import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class ServiceLivraison {
    private final ArrayList <Client> listeClients;
    private final ArrayList <Livreur> listeLivreurs;
    private final ArrayList <Commande> listeCommandes;
    private final ArrayList <Livraison> listeLivraisons;

    private static int jourDecalage = 0;


    public ServiceLivraison(){
        this.listeClients = new ArrayList<>();
        this.listeLivreurs = new ArrayList<>();
        this.listeCommandes = new ArrayList<>();
        this.listeLivraisons = new ArrayList<>();
    }

    //TEMPS
    public static LocalDate jourActuel(){
        return LocalDate.now().plusDays(jourDecalage);
    }

    public static void ajouterUnJour(){
        jourDecalage++;
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

    public void afficherClients(){
        for (Client client : listeClients) {
            System.out.println(client);
        }
    }


    //LIVREURS
    public void ajouterLivreur(Livreur livreur){ listeLivreurs.add(livreur); }

    public boolean supprimerLivreur(int idASupprimer){
        return listeLivreurs.removeIf(livreur -> livreur.getId() == idASupprimer);
    }

    public int getLivreursListSize(){ return listeLivreurs.size(); }

    public ArrayList<Livreur> getListeLivreurs(){
        return new ArrayList<>(listeLivreurs);
    }

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


    public void afficherLivreurs(){
        for (Livreur livreur: listeLivreurs){
            System.out.println(livreur);
        }
    }

    public Livreur getLivreurDisponible(){
        for (Livreur livreur: listeLivreurs){
            if (livreur.getEstDisponible()){
                return livreur;
            }
        }
        return null;
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

    public ArrayList<Commande> getCommandesEnAttente(){
        ArrayList <Commande> commandesEnAttente = new ArrayList<>();
        for (Commande commande: listeCommandes){
            if (commande.getStatut() == StatutCommande.EN_ATTENTE){
                commandesEnAttente.add(commande);
            }
        }
        return commandesEnAttente;
    }

    public ArrayList<Commande> getCommandesEnpreparation(){
        ArrayList <Commande> commandesEnPreparation = new ArrayList<>();
        for (Commande commande: listeCommandes){
            if (commande.getStatut() == StatutCommande.EN_PREPARATION){
                commandesEnPreparation.add(commande);
            }
        }
        return commandesEnPreparation;
    }

    public int getNbCommandesLivrees(){
        int i = 0;
        for (Commande commande: listeCommandes){
            if (commande.getStatut() == StatutCommande.LIVREE){
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

    public void afficherCommandes(){
        for (Commande commandeActuelle: listeCommandes){
            System.out.println(commandeActuelle);
        }
    }

    public void afficherListeCommandeDonnee(ArrayList <Commande> commandes){
        for (Commande commandeActuelle: commandes){
            System.out.println(commandeActuelle);
        }
    }



    //LIVRAISONS

    public ArrayList<Livraison> getListeLivraisons(){return listeLivraisons;}

    public void ajouterLivraison(Livraison livraison){
        listeLivraisons.add(livraison);
        livraison.getCommande().setStatut(StatutCommande.EN_LIVRAISON);
        livraison.getLivreur().setEstDisponible(false);
    }

    public void afficherLivraisonsEnCours(){
        for (Livraison livraisonActuelle : listeLivraisons){
            if (livraisonActuelle.getCommande().getStatut() == StatutCommande.EN_LIVRAISON){
                System.out.println(livraisonActuelle);
            }
        }
    }
}









