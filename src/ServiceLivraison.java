import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ServiceLivraison {
    private final ArrayList <Client> listeClients;
    private final ArrayList <Livreur> listeLivreurs;
    private final ArrayList <Commande> listeCommandes;
    private final ArrayList <Livraison> listeLivraisons;

    private static int jourDecalage = 0; //utile pour la simulation (=date d'aujoudhui + le nb de jours avancés dans la simu)
    public static final DateTimeFormatter FORMAT_FRANCE = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public ServiceLivraison(){
        this.listeClients = new ArrayList<>();
        this.listeLivreurs = new ArrayList<>();
        this.listeCommandes = new ArrayList<>();
        this.listeLivraisons = new ArrayList<>();
    }

    //TEMPS
    public static LocalDate getJourActuel(){
        return LocalDate.now().plusDays(jourDecalage);
    }

    public static void ajouterUnJour(){
        jourDecalage++;
    }

    //SIMULATION
    public void rafraichirSysteme(){   //met à jour le systeme de livraison avec le jour actuel
        ArrayList <Commande> commandesAExpedier = new ArrayList<>();

        for (Commande commandeActuelle: listeCommandes){
            if (commandeActuelle.getStatut() == StatutCommande.EN_ATTENTE && ChronoUnit.DAYS.between(commandeActuelle.getDateCommande(), getJourActuel()) >= 1){
                commandeActuelle.setStatut(StatutCommande.EN_PREPARATION);
            } else if (commandeActuelle.getStatut() == StatutCommande.EN_PREPARATION && ChronoUnit.DAYS.between(commandeActuelle.getDateCommande(), getJourActuel()) >= 2) {
                commandesAExpedier.add(commandeActuelle);
            }
        }

        commandesAExpedier.sort(Comparator.comparing(Commande::getEstExpress).reversed().thenComparing(Commande::getDateCommande));

        for (Commande commandeActuelle : commandesAExpedier){
            Livreur livreur = getLivreurDisponible();
            if (livreur != null){
                ajouterLivraison(new Livraison(livreur, commandeActuelle));
            } else {
                break;
            }
        }

        for (Livraison livraisonActuelle: listeLivraisons){
            if (livraisonActuelle.getDateLivraison().isEqual(getJourActuel()) && livraisonActuelle.getCommande().getStatut() != StatutCommande.LIVREE){
                livraisonActuelle.terminerLivraison();
            }
        }
    }

    //CHARGEMENT / SAUVEGARDE DE DONNEES

    public boolean chargerClients(){
        return chargerClients(Paths.get("../../../data/clients.txt"));
    }
    public boolean chargerClients(Path cheminClients){       //charger des clients depuis un fichier txt
        if (!Files.exists(cheminClients)) return false;

        try {
            List<String> clients = Files.readAllLines(cheminClients);
            for (String client : clients){
                if (client.isBlank()) continue;
                String[] donneesClient = client.split(";");
                listeClients.add(new Client(Integer.parseInt(donneesClient[0]), donneesClient[1], donneesClient[2], donneesClient[3], donneesClient[4], donneesClient[5]));
            }
            initCompteurIdPersonne();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public boolean chargerLivreurs(){
        return chargerLivreurs(Paths.get("../../../data/livreurs.txt"));
    }

    public boolean chargerLivreurs(Path cheminLivreurs){          //charger livreurs depuis un .txt
        if (!Files.exists(cheminLivreurs)) return false;

        try {
            List<String> livreurs = Files.readAllLines(cheminLivreurs);
            for (String livreur : livreurs){
                if (livreur.isBlank()) continue;
                String[] donneesLivreur = livreur.split(";");
                listeLivreurs.add(new Livreur(Integer.parseInt(donneesLivreur[0]), donneesLivreur[1], donneesLivreur[2], donneesLivreur[3], donneesLivreur[4]));
            }
            initCompteurIdPersonne();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void genererCommandeAleatoire(boolean estExpress){       //permet de generer des commandes sans devoir tout taper à la main
        Random rand = new Random();
        if (listeClients.isEmpty()){
            System.out.println("Aucun client n'est enregistré. Appuyez sur entrée pour continuer...");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            return;
        }
        String[] objets = {"Livre de poche", "Smartphone", "Panier Bio", "Nespresso", "Fleurs", "Sneakers"};
        Client client = listeClients.get(rand.nextInt(listeClients.size()));
        String description = objets[rand.nextInt(objets.length)];
        listeCommandes.add(new Commande(client, description, estExpress));
    }

    void initCompteurIdPersonne(){      //utile si des clients/livreurs ont été chargés depuis un fichier txt, fait en sorte que personne n'ait le mm id
        int dernierId = 0;
        for (Client clientActuel :listeClients){
            if (clientActuel.getId() > dernierId){
                dernierId = clientActuel.getId();
            }
        }

        for (Livreur livreurActuel :listeLivreurs){
            if (livreurActuel.getId() > dernierId){
                dernierId = livreurActuel.getId();
            }
        }

        Personne.setCompteurId(dernierId + 1);
    }

    //PERSONNE
    public void afficherListePersonneDonee(List<? extends Personne> personnes){
        if (personnes == null) return;
        for (Personne personne : personnes) {
            System.out.println(personne);
        }
    }

    //CLIENTS
    public void ajouterClient(Client client){
        listeClients.add(client);
    }

    public boolean supprimerClient(int idASupprimer){
        return listeClients.removeIf(client -> client.getId() == idASupprimer);
    }

    public int getClientsListSize(){
        return listeClients.size();
    }

    public ArrayList<Client> getListeClients(){
        return new ArrayList<>(listeClients);
    }

    public Client rechercheClientParId(int idRecherche){
        for (Client client : listeClients){
            if (client.getId() == idRecherche){
                return client;
            }
        }
        return null;
    }

    public ArrayList<Client> rechercheClient(String recherche){
        ArrayList<Client> resultat = new ArrayList<>();
        recherche = recherche.toLowerCase();
        for (Client client : listeClients){
            if (client.toString().toLowerCase().contains(recherche)){
                resultat.add(client);
            }
        }
        return resultat;
    }

    public ArrayList<Commande> getCommandesClient(int idClient){  //retourne les commandes d'un client
        ArrayList <Commande> commandes = new ArrayList<>();
        for (Commande commande : listeCommandes){
            if (commande.getClient().getId() == idClient){
                commandes.add(commande);
            }
        }
        return commandes;
    }

    public ArrayList <Client> getTriClientsParNom(boolean alphabetique){
        ArrayList <Client> triClients = new ArrayList<>(listeClients);
        if (alphabetique){
            triClients.sort(Comparator.comparing(Client::getNom));
        } else {
            triClients.sort(Comparator.comparing(Client::getNom).reversed());
        }
        return triClients;
    }


    //LIVREURS
    public void ajouterLivreur(Livreur livreur){ listeLivreurs.add(livreur); }

    public boolean supprimerLivreur(int idASupprimer){
        return listeLivreurs.removeIf(livreur -> livreur.getId() == idASupprimer);
    }

    public void afficherLivreurs(){
        afficherListePersonneDonee(listeLivreurs);
    }

    public ArrayList <Livreur> getListeLivreurs() {return new ArrayList<>(listeLivreurs);}

    public int getLivreursListSize(){ return listeLivreurs.size(); }

    public Livreur rechercheLivreurParId(int idRecherche){
        for (Livreur livreur : listeLivreurs){
            if (livreur.getId() == idRecherche){
                return livreur;
            }
        }
        return null;
    }

    public ArrayList<Livreur> rechercheLivreur(String recherche){
        ArrayList<Livreur> resultat = new ArrayList<>();
        recherche = recherche.toLowerCase();
        for (Livreur livreur: listeLivreurs){
            if (livreur.toString().toLowerCase().contains(recherche)){
                resultat.add(livreur);
            }
        }
        return resultat;
    }

    public Livreur getLivreurDisponible() {
        Livreur meilleurLivreur = null;
        for (Livreur l : listeLivreurs) {
            if (l.getEstDisponible()) {
                if (meilleurLivreur == null || l.getNbLivraisons() < meilleurLivreur.getNbLivraisons()) {
                    meilleurLivreur = l;
                }
            }
        }
        return meilleurLivreur;
    }

    public ArrayList <Livreur> getTriLivreursParNom(boolean alphabetique){
        ArrayList <Livreur> triLivreurs = new ArrayList<>(listeLivreurs);
        if (alphabetique){
            triLivreurs.sort(Comparator.comparing(Livreur::getNom));
        } else {
            triLivreurs.sort(Comparator.comparing(Livreur::getNom).reversed());
        }
        return triLivreurs;
    }


    public ArrayList<Livreur> getLivreursLesPlusActifs(){
        ArrayList <Livreur> triLivreurs = new ArrayList<>(listeLivreurs);
        triLivreurs.sort(Comparator.comparing(Livreur::getNbLivraisons).reversed());
        return triLivreurs;
    }

    //COMMANDES
    public void ajouterCommande(Commande commande){listeCommandes.add(commande);}

    public boolean supprimerCommande(int idASupprimer){ return listeCommandes.removeIf(commande -> commande.getId() == idASupprimer);}

    public int getCommandesListSize(){
        return listeCommandes.size();
    }

    public ArrayList<Commande> getListeCommandes(){
        return listeCommandes;
    }

    public ArrayList<Commande> getCommandesTrieesParDate(boolean ordreCroissant){
        ArrayList<Commande> copie = new ArrayList<>(listeCommandes);
        if (ordreCroissant){
            copie.sort(Comparator.comparing(Commande::getDateCommande));
        } else {
            copie.sort(Comparator.comparing(Commande::getDateCommande).reversed());
        }
        return copie;
    }

    public ArrayList<Commande> getCommandesParStatut(StatutCommande statut){  //retourne les commandes qui possèdent le statut specifié
        ArrayList <Commande> commandesRecherchees = new ArrayList<>();
        for (Commande commande: listeCommandes){
            if (commande.getStatut() == statut){
                commandesRecherchees.add(commande);
            }
        }
        return commandesRecherchees;
    }

    public int getNbCommandesPossedantUnStatut(StatutCommande statut){  //retourne le nb de commandes qui possèdent ce statut
        int i = 0;
        for (Commande commande: listeCommandes){
            if (commande.getStatut() == statut){
                i++;
            }
        }
        return i;
    }

    public Commande rechercheCommandeParId(int idRecherche){
        for (Commande commande : listeCommandes){
            if (commande.getId() == idRecherche){
                return commande;
            }
        }
        return null;
    }

    public void afficherCommandesEnCours(){
        for (Commande commandeActuelle: listeCommandes){
            if (commandeActuelle.getStatut() != StatutCommande.LIVREE){
                System.out.println(commandeActuelle);
            }
        }
    }

    public void afficherListeCommandeDonnee(ArrayList <Commande> commandes){
        if (commandes == null) return;
        for (Commande commandeActuelle: commandes){
            System.out.println(commandeActuelle);
        }
    }

    public void accepterToutesLesCommandesEnAttente(){
        for (Commande c : getCommandesParStatut(StatutCommande.EN_ATTENTE)) {
            c.setStatut(StatutCommande.EN_PREPARATION);
        }
    }



    //LIVRAISONS
    public int getLivraisonListSize(){ return listeLivraisons.size();}

    public void ajouterLivraison(Livraison livraison){
        listeLivraisons.add(livraison);
        livraison.getCommande().setStatut(StatutCommande.EN_LIVRAISON);
        livraison.getLivreur().setEstDisponible(false);
    }

    public void afficherHistoriqueLivraisons(){
        for (Livraison livraisonActuelle : listeLivraisons){
            if (livraisonActuelle.getCommande().getStatut() == StatutCommande.LIVREE){
                System.out.println(livraisonActuelle);
            }
        }
    }

    public void afficherLivraisonsEnCours(){
        for (Livraison livraisonActuelle : listeLivraisons){
            if (livraisonActuelle.getCommande().getStatut() == StatutCommande.EN_LIVRAISON){
                System.out.println(livraisonActuelle);
            }
        }
    }
}