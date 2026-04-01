import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //VARIABLES GLOBALES
    private final ServiceLivraison serviceLivraison = new ServiceLivraison();
    private final Scanner scan = new Scanner(System.in);

    //METHODES GENERALES
    public int lireEntier() {
        while (true) {
            String entree = scan.nextLine();
            try {
                return Integer.parseInt(entree);
            } catch (NumberFormatException e) {
                System.out.print("Erreur : veuillez entrer un nombre valide : ");
            }
        }
    }

    public String lireString(String message, boolean effacerEcran){
        boolean champVide = false;
        String champ;
        do {
            if (effacerEcran) effacerEcran();
            if (champVide){
                System.out.println("Champ vide !");
            }
            System.out.print(message);
            champ = scan.nextLine();
            champVide = champ.isBlank();
        } while(champVide);
        return champ;
    }



    public void effacerEcran(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 30; i++){
                System.out.println();
            }
        }
    }


    //CLIENTS ET LIVREURS
    public void saisieEtAjoutClient(){
        String nom = lireString("Saisissez le nom du client: ", true);
        String prenom = lireString("Saisissez le prénom du client: ", true);
        String tel = lireString("Saisissez le numéro de téléphone du client: ", true);
        String adresse = lireString("Saisissez l'adresse du client: ", true);
        String email = lireString("Saisissez l'email du client: ", true);

        serviceLivraison.ajouterClient(new Client(nom, prenom, tel, adresse, email));
    }

    public void saisieEtAjoutLivreur(){
        String nom = lireString("Saisissez le nom du livreur: ", true);
        String prenom = lireString("Saisissez le prénom du livreur: ", true);
        String tel = lireString("Saisissez le numéro de téléphone du livreur: ", true);
        String vehicule = lireString("Saisissez le vehicule du livreur: ", true);

        serviceLivraison.ajouterLivreur(new Livreur(nom, prenom, tel, vehicule));
    }

    public void supprimerClientAvecId(int id){
        if (serviceLivraison.supprimerClient(id)){
            System.out.println("\nClient supprimé avec succès ! Appuyez sur entrée pour poursuivre...");
        } else {
            System.out.println("\nLa suppression a échoué. Appuyez sur entrée pour poursuivre...");
        }
        scan.nextLine();
    }

    public void supprimerLivreurAvecId(int id){
        if (serviceLivraison.supprimerLivreur(id)){
            System.out.println("\nLivreur supprimé avec succès ! Appuyez sur entrée pour poursuivre...");
        } else {
            System.out.println("\nLa suppression a échoué. Appuyez sur entrée pour poursuivre...");
        }
        scan.nextLine();
    }

    public void modifierInfosPersonne(Personne personneAmodifier, boolean estClient){

        String entite = estClient ? "client" : "livreur";
        String Entite = estClient ? "Client" : "Livreur";

        if (personneAmodifier == null){
            System.out.println(Entite + " introuvable.\\nAppuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }

        boolean retourMenu = false;
        while (!retourMenu){
            effacerEcran();
            System.out.println("Un "+ entite +" à été trouvé :\n" + personneAmodifier + "\n");
            System.out.print("Que voulez vous modifier ?\n[1] Nom\n[2] Prenom\n[3] Téléphone");
            if (estClient){
                System.out.println("\n[4] Adresse\n[5] Email");
            } else {
                System.out.println("\n[4] Véhicule");
            }
            System.out.println("\n[0] Retour");

            switch (lireEntier()){
                case 1:
                    personneAmodifier.setNom(lireString("Nouveau nom (actuel: " + personneAmodifier.getNom() +"): ", true));
                    break;
                case 2:
                    personneAmodifier.setPrenom(lireString("Nouveau prénom (actuel: " + personneAmodifier.getPrenom() +"): ", true));
                    break;
                case 3:
                    personneAmodifier.setTelephone(lireString("Nouveau numéro de téléphone (actuel: " + personneAmodifier.getTel() +"): ", true));
                    break;
                case 4:
                    if (estClient){
                        Client client = (Client) personneAmodifier;
                        client.setAdresse(lireString("Saisissez la nouvelle adresse (actuelle: " + client.getAdresse() + "): ", true));
                    } else {
                        Livreur livreur = (Livreur) personneAmodifier;
                        livreur.setVehicule(lireString("Saisissez le nom du vehicule (actuel: " + livreur.getVehicule() + "): ", true));
                    }
                    break;
                case 5:
                    if (!estClient) break;
                    Client client = (Client) personneAmodifier;
                    client.setEmail(lireString("Saisissez la nouvelle adresse email (actuelle: " + client.getEmail() + "): ", true));
                    break;
                case 0:
                    retourMenu = true;
                    break;
            }
        }
    }

    public void menuAffichagePersonne(Personne personne){
        if (personne == null) return;
        boolean estClient = personne instanceof Client;

        String entite = estClient ? "client" : "livreur";

        boolean quitter = false;
        while (!quitter) {
            effacerEcran();
            System.out.println("Un " + entite + " à été trouvé :\n" + personne);
            System.out.println("\nQue souhaitez vous faire ?\n[1] Supprimer le " + entite + "\n[2] Modifier les informations du " + entite);
            if (estClient){
                System.out.println("[3] Afficher les commandes du client");
            }
            System.out.println("[0] Quitter");
            switch (lireEntier()) {
                case 1:
                    if (estClient){
                        supprimerClientAvecId(personne.getId());
                    } else {
                        supprimerLivreurAvecId(personne.getId());
                    }
                    return;
                case 2:
                    modifierInfosPersonne(personne, estClient);
                    break;
                case 3:
                    if (!estClient) break;
                    effacerEcran();
                    System.out.println("--- Commandes de " + personne.getPrenom() + " " + personne.getNom() + " ---");
                    ArrayList <Commande> commandes = serviceLivraison.getCommandesClient(personne.getId());
                    if (commandes.isEmpty()){
                        System.out.println("Le client n'a passé aucune commande.\n\nAppuyez sur entrée pour continuer...");
                    } else {
                        for (Commande commandeActuelle : commandes){
                            System.out.println(commandeActuelle);
                        }
                        System.out.println("\nAppuyez sur entrée pour continuer...");
                    }
                    scan.nextLine();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void afficherListePersonne(boolean estClient){
        String entite = estClient ? "estClient" : "livreur";
        String entitePluriel = estClient ? "clients" : "livreurs";

        ArrayList<? extends Personne> listePersonne = estClient ?  serviceLivraison.getListeClients() : serviceLivraison.getListeLivreurs();
        if (listePersonne.isEmpty()){
            effacerEcran();
            System.out.println("\nAucun " + entite + " n'est enregistré. Appuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }

        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("Liste des " + entitePluriel + ":");
            serviceLivraison.afficherListePersonneDonee(listePersonne);
            System.out.println("\n[1] Modifier, afficher ou supprimer un " + entite + " | [2] Ordre alphabétique | [3] Ordre alphabétique inversé | [0] Quitter ");
            int choixUtilisateur = lireEntier();
            switch (choixUtilisateur){
                case 1:
                    System.out.print("Saisissez l'id du " + entite + " à éditer: ");
                    if (estClient){
                        menuAffichagePersonne(serviceLivraison.rechercheClientParId(lireEntier()));
                    } else {
                        menuAffichagePersonne(serviceLivraison.rechercheLivreurParId(lireEntier()));
                    }
                    break;
                case 2:
                case 3:
                    if (estClient){
                        listePersonne = serviceLivraison.getTriClientsParNom(choixUtilisateur == 2);
                    } else {
                        listePersonne = serviceLivraison.getTriLivreursParNom(choixUtilisateur == 2);
                    }
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
            if ((estClient ? serviceLivraison.getClientsListSize() : serviceLivraison.getLivreursListSize()) == 0) break;
        }

    }


    public Client saisieRechercheClient(){
        return (Client) saisieRecherchePersonne(true);
    }

    public Livreur saisieRechercheLivreur(){
        return (Livreur) saisieRecherchePersonne(false);
    }

    public Personne saisieRecherchePersonne(boolean client){
        effacerEcran();
        int tailleListe = client ? serviceLivraison.getClientsListSize() : serviceLivraison.getLivreursListSize();
        String personne = client ? "client" : "livreur";
        String pluriel = client ? "clients" : "livreurs";


        if (tailleListe == 0){
            System.out.println("Aucun " + personne + "n'est enregistré, appuyez sur entrée pour quitter...");
            scan.nextLine();
            return null;
        }

        String recherche = lireString("Saisissez un mot clé (nom, prenom, email ...): ", true);
        ArrayList<? extends Personne> personnesTrouvees = client ? serviceLivraison.rechercheClient(recherche) : serviceLivraison.rechercheLivreur(recherche);

        if (personnesTrouvees.isEmpty()){
            System.out.println("Aucun " + personne + " n'a été trouvé avec le mot clé \"" + recherche +  "\". Appuyez sur entrée pour revenir...");
            scan.nextLine();
            return null;
        } else if (personnesTrouvees.size() == 1){
            return personnesTrouvees.getFirst();
        } else {
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Plusieurs " + pluriel + " ont été trouvés:");
                serviceLivraison.afficherListePersonneDonee(personnesTrouvees);
                System.out.println("\n[1] Selectionner un "+ personne +" | [0] Quitter ");
                switch (lireEntier()){
                    case 1:
                        System.out.println("Saisissez l'id du "+ personne +" à selectionner: ");
                        if (client){
                            return serviceLivraison.rechercheClientParId(lireEntier());
                        } else {
                            return serviceLivraison.rechercheLivreurParId(lireEntier());
                        }
                    case 0:
                        quitter = true;
                        break;
                    default:
                }
            }
        }
        return null;
    }


    public void menuGestionClients(){

        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("Que souhaitez vous faire ?\n\n[1] Ajouter un client\n[2] Afficher la liste des clients\n[3] Rechercher un client\n[0] Retour");
            switch (lireEntier()){
                case 1:
                    saisieEtAjoutClient();
                    break;
                case 2:
                    afficherListePersonne(true);
                    break;
                case 3:
                    menuAffichagePersonne(saisieRechercheClient());
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void menuGestionLivreurs(){

        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("Que souhaitez vous faire ?\n\n[1] Ajouter un livreur\n[2] Afficher la liste des livreurs\n[3] Rechercher un livreur\n[0] Retour");
            switch (lireEntier()){
                case 1:
                    saisieEtAjoutLivreur();
                    break;
                case 2:
                    afficherListePersonne(false);
                    break;
                case 3:
                    menuAffichagePersonne(saisieRechercheLivreur());
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    //COMMANDES ET LIVRAISONS
    public void saisieNouvelleCommande(){
        String description;
        boolean quitter = false;

        while (!quitter){
            effacerEcran();
            System.out.println("--- Saisie d'une nouvelle commande ---");
            System.out.println("Saisie du client: [1] Rechercher un client | [0] Quitter");
            switch (lireEntier()){
                case 1:
                    Client client = saisieRechercheClient();
                    if (client == null){
                        return;
                    }
                    description = lireString("Saisissez la description de la commande: ", true);
                    int choixLivraison;
                    do {
                        effacerEcran();
                        System.out.println("Souhaitez vous une livraison express ? [1] Oui | [2] Non");
                        choixLivraison = lireEntier();
                    } while (choixLivraison != 1 && choixLivraison != 2);
                    boolean estExpress = choixLivraison == 1;
                    serviceLivraison.ajouterCommande(new Commande(client, description, estExpress));
                    System.out.println("La commande a bien été prise en compte, appuyez sur espace pour continuer...");
                    scan.nextLine();
                    quitter = true;
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void accepterCommande(){
        ArrayList <Commande> commandesEnAttente = serviceLivraison.getCommandesParStatut(StatutCommande.EN_ATTENTE);
        if (commandesEnAttente.isEmpty()){
            effacerEcran();
            System.out.println("--- Commandes en attente ---");
            System.out.print("Aucune commande en attente, appuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }
        boolean quitter = false;

        while (!quitter){
            effacerEcran();
            System.out.println("--- Commandes en attente ---");
            for (Commande commande : commandesEnAttente){
                System.out.println(commande);
            }
            System.out.println("\n[1] Accepter une commande | [2] Tout accepter | [0] Quitter");
            switch (lireEntier()){
                case 1:
                    System.out.print("Saisissez l'id de la commande à valider: ");
                    Commande commandeTrouvee = serviceLivraison.rechercheCommandeParId(lireEntier());
                    if (commandeTrouvee == null){
                        System.out.print("Commande introuvable, appuyez sur entrée pour continuer...");
                    } else if (commandeTrouvee.getStatut() != StatutCommande.EN_ATTENTE) {
                        System.out.print("Commande déjà acceptée, appuyez sur entrée pour continuer...");
                    } else {
                        commandeTrouvee.setStatut(StatutCommande.EN_PREPARATION);
                        System.out.print("La commande a bien été acceptée, appuyez sur entrée pour continuer...");
                    }
                    scan.nextLine();
                    quitter = true;
                    break;
                case 2:
                    serviceLivraison.accepterToutesLesCommandesEnAttente();
                    System.out.print("Toutes les commandes ont bien été acceptées, appuyez sur entrée pour continuer...");
                    scan.nextLine();
                    quitter = true;
                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }
    }

    public void assignerLivreur(){
        ArrayList <Commande> commandesEnpreparation = serviceLivraison.getCommandesParStatut(StatutCommande.EN_PREPARATION);
        effacerEcran();
        System.out.println("--- Commandes en préparation ---");
        if (commandesEnpreparation.isEmpty()){
            System.out.print("Aucune commande en préparation, appuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }
        Livreur livreur = serviceLivraison.getLivreurDisponible();
        if (livreur == null){
            for (Commande commande : commandesEnpreparation){
                System.out.println(commande);
            }
            System.out.println("\nAucun livreur n'est disponible pour le moment, appuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }

        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("--- Commandes en préparation ---");
            for (Commande commande : commandesEnpreparation){
                System.out.println(commande);
            }
            System.out.println("\n[1] Assigner un livreur à une commande | [2] Assigner automatiquement les commandes avec tous les livreurs disponibles | [0] Quitter");

            switch (lireEntier()){
                case 1:
                    System.out.print("Saisissez l'id de la commande à assigner: ");
                    Commande commandeTrouvee = serviceLivraison.rechercheCommandeParId(lireEntier());
                    if (commandeTrouvee == null){
                        System.out.print("Commande introuvable, appuyez sur entrée pour poursuivre...");
                    } else if (commandeTrouvee.getStatut() != StatutCommande.EN_PREPARATION) {
                        System.out.print("Commande déjà assignée, appuyez sur entrée pour poursuivre...");
                    } else {
                        serviceLivraison.ajouterLivraison(new Livraison(livreur, commandeTrouvee));
                        System.out.print("Un livreur a été assigné à la commande, appuyez sur entrée pour poursuivre...");
                    }
                    scan.nextLine();
                    quitter = true;
                    break;
                case 2:
                    effacerEcran();
                    int compteurAssignations = 0;
                    for (Commande commandeActuelle : commandesEnpreparation) {
                        if (livreur == null) {
                            break;
                        }
                        serviceLivraison.ajouterLivraison(new Livraison(livreur, commandeActuelle));
                        compteurAssignations++;
                        livreur = serviceLivraison.getLivreurDisponible();
                    }

                    if (livreur == null && compteurAssignations < commandesEnpreparation.size()) {
                        System.out.println("Plus aucun livreur disponible, " + compteurAssignations + " commandes ont été assignées, il en reste " + (commandesEnpreparation.size() - compteurAssignations) + " en attente...");
                    } else {
                        System.out.println("Toutes les commandes en préparation ont été assignées.");
                    }

                    System.out.print("\nAppuyez sur entrée pour continuer...");
                    scan.nextLine();
                    quitter = true;
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void afficherCommandes(){
        if (serviceLivraison.getCommandesListSize() == 0){
            effacerEcran();
            System.out.println("--- Commandes ---");
            System.out.println("Aucune commande enregistrée, appuyez sur entrée pour quitter...");
            scan.nextLine();
            return;
        }

        ArrayList <Commande> commandes = serviceLivraison.getListeCommandes();

        boolean quitter = false;
        int choixUtilisateur;
        while (!quitter){
            effacerEcran();
            System.out.println("--- Commandes ---");
            serviceLivraison.afficherListeCommandeDonnee(commandes);
            System.out.println("Total de commandes livrées: " + serviceLivraison.getNbCommandesPossedantUnStatut(StatutCommande.LIVREE));
            System.out.println("\n[1] Supprimer une commande | [2] Trier les commandes par date croissante | [3] Trier les commandes par date décroissante");
            choixUtilisateur = lireEntier();
            switch (choixUtilisateur){
                case 1:
                    System.out.print("Saisissez l'id de la commande à supprimer: ");
                    if(serviceLivraison.supprimerCommande(lireEntier())){
                        System.out.print("Commande supprimée avec succès, appuyez sur entrée pour continuer...");
                        scan.nextLine();
                    }
                    break;
                case 2:
                case 3:
                    commandes = serviceLivraison.getCommandesTrieesParDate(choixUtilisateur==2); //bool donc vrai si l'utilisateur a choisi ordre croissant (2)
                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }
    }

    public void afficherLivraisonsEnCours(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("--- Livraisons en cours ---");
            serviceLivraison.afficherLivraisonsEnCours();
            System.out.println("\n[0] Quitter");
            if (lireEntier() == 0) quitter = true;
        }
    }

    public void afficherHistoriqueLivraisons(){
        effacerEcran();
        System.out.println("--- Historique des livraisons ---");
        serviceLivraison.afficherHistoriqueLivraisons();
        System.out.println("\n\nAppuyez sur entrée pour quitter...");
        scan.nextLine();
    }

    public void afficherMenuCommandesLivraisons(){
        System.out.println(
                "------- COMMANDES & LIVRAISONS -------\n" +
                "  [1] Créer une nouvelle commande\n" +
                "  [2] Voir les commandes en attente\n" +
                "  [3] Voir les commandes en préparation\n" +
                "  [4] Suivi des livraisons en cours\n" +
                "  [5] Voir toutes les commandes\n" +
                "  [6] Afficher l'historique des livraisons\n" +
                "  [0] Retour au menu principal"
        );
    }

    public void menuCommandesLivraisons(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            afficherMenuCommandesLivraisons();
            switch (lireEntier()){
                case 1:
                    saisieNouvelleCommande();
                    break;
                case 2:
                    accepterCommande();
                    break;
                case 3:
                    assignerLivreur();
                    break;
                case 4:
                    afficherLivraisonsEnCours();
                    break;
                case 5:
                    afficherCommandes();
                    break;
                case 6:
                    afficherHistoriqueLivraisons();
                    break;
                case 0:
                    quitter = true;
                    break;
            }

        }
    }


    public void menuClientsLivreurs(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("------- CLIENTS & LIVREURS -------\n  [1] Gérer les clients\n  [2] Gérer les livreurs\n  [0] Retour au menu principal");
            switch (lireEntier()){
                case 1:
                    menuGestionClients();
                    break;
                case 2:
                    menuGestionLivreurs();
                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }
    }


    public void menuSimulation(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            serviceLivraison.rafraichirSysteme();
            System.out.println("------- SIMULATION ------- Date actuelle: " + ServiceLivraison.getJourActuel().format(ServiceLivraison.FORMAT_FRANCE));

            if (serviceLivraison.getCommandesListSize() == 0){
                System.out.println("\nAucune commande en cours.");
            } else {
                System.out.println("\nCommandes en cours:");
            }
            serviceLivraison.afficherCommandesEnCours();
            System.out.print("\n\n");

            if (serviceLivraison.getLivraisonListSize() == 0){
                System.out.println("Aucune livraison en cours.");
            } else {
                System.out.println("Livraisons en cours:");
            }

            serviceLivraison.afficherLivraisonsEnCours();
            System.out.print("\n\n");
            System.out.println("Livreurs:");
            serviceLivraison.afficherLivreurs();
            System.out.println("\n[1] Passer au jour suivant | [2] Ajouter une commande standard | [3] Ajouter une commande express | [4] Ajouter un livreur | [0] Quitter");

            switch (lireEntier()){
                case 1:
                    ServiceLivraison.ajouterUnJour();
                    break;
                case 2:
                    serviceLivraison.genererCommandeAleatoire(false);
                    break;
                case 3:
                    serviceLivraison.genererCommandeAleatoire(true);
                    break;
                case 4:
                    serviceLivraison.ajouterLivreur(new Livreur("Odievre", "Théo", "06 78 90 56 04", "Tmax-530"));
                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }
    }

    void menuStatistiques(){
        effacerEcran();
        System.out.println("------- STATISTIQUES -------");
        System.out.println("Commandes livrées: " + serviceLivraison.getNbCommandesPossedantUnStatut(StatutCommande.LIVREE));
        System.out.println("Livreurs les plus actifs:");
        ArrayList <Livreur> livreurActifs = serviceLivraison.getLivreursLesPlusActifs();
        if (livreurActifs.isEmpty()){
            System.out.println("Pas de livreurs...");
        } else {
            int compteur = 0;
            for (Livreur livreurActuel: livreurActifs){
                if (compteur > 5) break;
                System.out.println(livreurActuel);
                compteur++;
            }
        }

        System.out.println("\nAppuyez sur entrée pour quitter...");
        scan.nextLine();
    }

    public void saisieCheminDonneesManuelles(boolean estClient){
        String entite = estClient ? "clients" : "livreurs";
        effacerEcran();
        System.out.println("------- Charger des données -------");
        System.out.println("Dossier de travail actuel : " + System.getProperty("user.dir"));
        Path cheminFichierEntite = Path.of(lireString("Saisissez le chemin relatif vers le fichier de données "+ entite +": ", false));
        boolean success;
        if (estClient){
            success = serviceLivraison.chargerClients(cheminFichierEntite);
        } else {
            success = serviceLivraison.chargerLivreurs(cheminFichierEntite);
        }

        if (success){
            System.out.println("Fichier chargé avec succès ! Appuyez sur entrée pour poursuivre...");
        } else {
            System.out.println("Erreur, verifiez le chemin du fichier. Appuyez sur entrée pour poursuivre...");
        }

        scan.nextLine();
    }

    public void menuChargerDonneesPerso(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("------- Charger des données -------");
            System.out.println("Quel type de données voulez vous charger ?\n[1] Données clients\n[2] Données livreurs\n[0] Quitter");
            switch (lireEntier()){
                case 1:
                    saisieCheminDonneesManuelles(true);
                    break;
                case 2:
                    saisieCheminDonneesManuelles(false);
                    break;
                case 0:
                    quitter = true;
                    break;
            }
        }

    }

    public void menuChargerDonnees(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            System.out.println("------- Charger des données -------");
            System.out.println("Que voulez vous faire ?\n[1] Charger les données par défaut\n[2] Charger mes propres données\n[0] Quitter");
            switch (lireEntier()){
                case 1:
                    effacerEcran();
                    System.out.println("------- Charger des données -------");
                    if (serviceLivraison.chargerClients() && serviceLivraison.chargerLivreurs()){
                        System.out.print("Données chargées avec succès ! ");
                    } else {
                        System.out.print("Un erreur est survenue lors de la lecture des données. ");
                    }
                    System.out.println("Appuyez sur entrée pour quitter... ");
                    scan.nextLine();
                    break;
                case 2:
                    menuChargerDonneesPerso();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    //MENU PRINCIPAL
    public void afficherMenuPrincipal(){
        System.out.println(
                "===========================================\n" +
                "    SYSTÈME DE LIVRAISON - MENU PRINCIPAL  \n" +
                "===========================================\n" +
                "        [1] COMMANDES & LIVRAISONS\n" +
                "        [2] CLIENTS & LIVREURS\n" +
                "        [3] SIMULATION\n" +
                "  \n" +
                "        [4] Statistiques Rapides\n" +
                "        [5] Charger des données\n" +
                "        [0] Quitter le système\n" +
                "===========================================");
    }

    void main(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            afficherMenuPrincipal();
            switch (lireEntier()){
                case 1:
                    menuCommandesLivraisons();
                    break;
                case 2:
                    menuClientsLivreurs();
                    break;
                case 3:
                    menuSimulation();
                    break;
                case 4:
                    menuStatistiques();
                    break;
                case 5:
                    menuChargerDonnees();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }
}
