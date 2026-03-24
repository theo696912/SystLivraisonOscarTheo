import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //VARIABLES GLOBALES
    private ServiceLivraison serviceLivraison = new ServiceLivraison();
    private Scanner scan = new Scanner(System.in);
    DateTimeFormatter formatFrance = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    //METHODES GENERALES
    public int lireEntier(){
        String entree = scan.nextLine();
        try {
            return Integer.parseInt(entree);
        } catch (NumberFormatException e){
            System.out.println("Erreur: veuillez entrer un nombre valide");
        }
        return 10000;
    }

    public String lireString(String message){
        boolean champVide = false;
        String champ;
        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
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
        String nom = lireString("Saisissez le nom du client: ");
        String prenom = lireString("Saisissez le prénom du client: ");
        String tel = lireString("Saisissez le numéro de téléphone du client: ");
        String adresse = lireString("Saisissez l'adresse du client: ");
        String email = lireString("Saisissez l'email du client: ");

        serviceLivraison.ajouterClient(new Client(nom, prenom, tel, adresse, email));
    }

    public void modifierInfosCommunes(Personne p, int choix){
        switch (choix){
            case 1:
                p.setNom(lireString("Nouveau nom (actuel: " + p.getNom() +"): "));
                break;
            case 2:
                p.setPrenom(lireString("Nouveau prénom (actuel: " + p.getPrenom() +"): "));
                break;
            case 3:
                p.setTelephone(lireString("Nouveau numéro de téléphone (actuel: " + p.getTel() +"): "));
                break;
        }
        System.out.println("Modification effectuée !");
    }

    public void modifierInfosClientAvecId(int id){
        Client clientAmodifier = serviceLivraison.rechercheClientParId(id);

        if (clientAmodifier == null){
            System.out.println("Client introuvable.\nAppuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }

        int choixMenu;
        boolean retourMenu = false;

        while (!retourMenu) {
            effacerEcran();
            System.out.println("Un client à été trouvé :\n" + clientAmodifier + "\n");
            System.out.println("Que voulez vous modifier ?\n[1] Nom\n[2] Prenom\n[3] Téléphone\n[4] Adresse\n[5] Email\n[0] Retour");
            choixMenu = lireEntier();
            switch (choixMenu){
                case 1:
                case 2:
                case 3:
                    modifierInfosCommunes(clientAmodifier, choixMenu);
                    break;
                case 4:
                    clientAmodifier.setAdresse(lireString("Saisissez la nouvelle adresse (actuelle: " + clientAmodifier.getAdresse() + "): "));
                    break;
                case 5:
                    clientAmodifier.setEmail(lireString("Saisissez la nouvelle adresse email (actuelle: " + clientAmodifier.getEmail() + "): "));
                    break;
                case 0:
                    retourMenu = true;
                    break;
                default:
            }
        }
    }

    public void modifierInfosLivreurAvecId(int id){
        Livreur livreurAmodifier = serviceLivraison.rechercheLivreurParId(id);

        if (livreurAmodifier == null){
            System.out.println("Livreur introuvable.\nAppuyez sur entrée pour continuer...");
            scan.nextLine();
            return;
        }

        int choixMenu;
        boolean retourMenu = false;

        while (!retourMenu) {
            effacerEcran();
            System.out.println("Un livreur à été trouvé :\n" + livreurAmodifier + "\n");
            System.out.println("Que voulez vous modifier ?\n[1] Nom\n[2] Prenom\n[3] Téléphone\n[4] Vehicule\n[0] Retour");
            choixMenu = lireEntier();
            switch (choixMenu){
                case 1:
                case 2:
                case 3:
                    modifierInfosCommunes(livreurAmodifier, choixMenu);
                    break;
                case 4:
                    livreurAmodifier.setVehicule(lireString("Saisissez le nom du vehicule (actuel: " + livreurAmodifier.getVehicule() + "): "));
                    break;
                case 0:
                    retourMenu = true;
                    break;
                default:
            }
        }
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

    public void menuSupressionModificationEntite(Personne personne){
        if (personne == null) return;
        boolean quitter = false;
        boolean estUnClient;
        String entite;
        if (personne instanceof  Client){
            entite = "client";
            estUnClient = true;
        } else {
            entite = "livreur";
            estUnClient = false;
        }
        while (!quitter) {
            effacerEcran();
            System.out.println("Un " + entite + " à été trouvé :");
            System.out.println(personne);
            System.out.println("\nQue souhaitez vous faire ?\n" +
                    "[1] Supprimer le " + entite + "\n" +
                    "[2] Modifier les informations du " + entite + "\n" +
                    "[0] Quitter");
            switch (lireEntier()) {
                case 1:
                    if (estUnClient){
                        supprimerClientAvecId(personne.getId());
                    } else {
                        supprimerLivreurAvecId(personne.getId());
                    }
                    return;
                case 2:
                    if (estUnClient){
                        modifierInfosClientAvecId(personne.getId());
                    } else {
                        modifierInfosLivreurAvecId(personne.getId());
                    }
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void afficherListeClients(){
        if (serviceLivraison.getClientsListSize() != 0){
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Liste des clients:");
                serviceLivraison.afficherClients();
                System.out.println("\n[1] Modifier ou supprimer un client | [0] Quitter ");
                switch (lireEntier()){
                    case 1:
                        System.out.print("Saisissez l'id du client à éditer: ");
                        menuSupressionModificationEntite(serviceLivraison.rechercheClientParId(lireEntier()));
                        break;
                    case 0:
                        quitter = true;
                        break;
                    default:
                }
                if (serviceLivraison.getClientsListSize() == 0){
                    break;
                }
            }

        } else {
            System.out.println("\nAucun client n'est enregistré. Appuyez sur entrée pour continuer...");
            scan.nextLine();
        }
    }

    public void afficherListeLivreurs(){
        if (serviceLivraison.getLivreursListSize() != 0){
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Liste des livreurs:");
                serviceLivraison.afficherLivreurs();
                System.out.println("\n[1] Modifier ou supprimer un livreur | [0] Quitter ");
                switch (lireEntier()){
                    case 1:
                        System.out.print("Saisissez l'id du livreur à éditer: ");
                        menuSupressionModificationEntite(serviceLivraison.rechercheLivreurParId(lireEntier()));
                        break;
                    case 0:
                        quitter = true;
                        break;
                    default:
                }
                if (serviceLivraison.getLivreursListSize() == 0){
                    break;
                }
            }

        } else {
            System.out.println("\nAucun livreur n'est enregistré. Appuyez sur entrée pour continuer...");
            scan.nextLine();
        }
    }

    public void rechercheClient(){
        menuSupressionModificationEntite(saisieRechercheClient());
    }

    public Client saisieRechercheClient(){
        effacerEcran();
        if (serviceLivraison.getClientsListSize() == 0){
            System.out.println("Aucun client n'est enregistré, appuyez sur entrée pour quitter...");
            scan.nextLine();
            return null;
        }
        String recherche = lireString("Saisissez un mot clé (nom, prenom, email ...): ");
        ArrayList<Client> clientsTrouves = serviceLivraison.rechercheClient(recherche);
        if (clientsTrouves.isEmpty()){
            System.out.println("Aucun client n'a été trouvé avec le mot clé \"" + recherche +  "\". Appuyez sur entrée pour revenir...");
            scan.nextLine();
            return null;
        } else if (clientsTrouves.size() == 1){
            effacerEcran();
            return clientsTrouves.getFirst();
        } else {
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Plusieurs clients ont été trouvés:");
                for (Client client : clientsTrouves){
                    System.out.println(client);
                }
                System.out.println("\n[1] Selectionner un client | [0] Quitter ");
                switch (lireEntier()){
                    case 1:
                        System.out.print("Saisissez l'id du client à selectionner: ");
                        return serviceLivraison.rechercheClientParId(lireEntier());
                    case 0:
                        quitter = true;
                        break;
                    default:
                }
            }
        }
        return null;
    }

    public void rechercheLivreur(){
        effacerEcran();
        String recherche = lireString("Saisissez un mot clé (nom, prenom, email ...): ");
        ArrayList<Livreur> livreursTrouves = serviceLivraison.rechercheLivreur(recherche);
        if (livreursTrouves.isEmpty()){
            System.out.println("Aucun livreur n'a été trouvé avec le mot clé \"" + recherche +  "\". Appuyez sur entrée pour revenir...");
            scan.nextLine();
        } else if (livreursTrouves.size() == 1){
            effacerEcran();
            menuSupressionModificationEntite(livreursTrouves.getFirst());
        } else {
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Plusieurs livreurs ont été trouvés:");
                for (Livreur livreur : livreursTrouves){
                    System.out.println(livreur);
                }
                System.out.println("\n[1] Modifier ou supprimer un livreur | [0] Quitter ");
                switch (lireEntier()){
                    case 1:
                        System.out.print("Saisissez l'id du livreur à éditer: ");
                        menuSupressionModificationEntite(serviceLivraison.rechercheLivreurParId(lireEntier()));
                        break;
                    case 0:
                        quitter = true;
                        break;
                    default:
                }
            }
        }
    }


    public void afficherMenuGestionClients(){
        System.out.println("Que souhaitez vous faire ?" +
                "\n" +
                "\n" +
                "[1] Ajouter un client\n" +
                "[2] Afficher la liste des clients\n" +
                "[3] Rechercher un client\n" +
                "[0] Retour");
    }

    public void menuGestionClients(){
        boolean quitter = false;
        int choixMenu;
        effacerEcran();
        afficherMenuGestionClients();
        while (!quitter){
            effacerEcran();
            afficherMenuGestionClients();
            choixMenu = lireEntier();

            switch (choixMenu){
                case 1:
                    saisieEtAjoutClient();
                    break;
                case 2:
                    afficherListeClients();
                    break;
                case 3:
                    rechercheClient();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void saisieEtAjoutLivreur(){
        String nom = lireString("Saisissez le nom du livreur: ");
        String prenom = lireString("Saisissez le prénom du livreur: ");
        String tel = lireString("Saisissez le numéro de téléphone du livreur: ");
        String vehicule = lireString("Saisissez le vehicule du livreur: ");

        serviceLivraison.ajouterLivreur(new Livreur(nom, prenom, tel, vehicule));
    }

    public void afficherMenuGestionLivreurs(){
        System.out.println("Que souhaitez vous faire ?" +
                "\n" +
                "\n" +
                "[1] Ajouter un livreur\n" +
                "[2] Afficher la liste des livreurs\n" +
                "[3] Rechercher un livreur\n" +
                "[0] Retour");
    }

    public void menuGestionLivreurs(){
        boolean quitter = false;
        int choix;
        effacerEcran();
       afficherMenuGestionLivreurs();

        while (!quitter){
            effacerEcran();
            afficherMenuGestionLivreurs();
            choix = lireEntier();
            switch (choix){
                case 1:
                    saisieEtAjoutLivreur();
                    break;
                case 2:
                    afficherListeLivreurs();
                    break;
                case 3:
                    rechercheLivreur();
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
                    description = lireString("Saisissez la description de la commande: ");
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
        ArrayList <Commande> commandesEnAttente = serviceLivraison.getCommandesEnAttente();
        if (commandesEnAttente.isEmpty()){
            effacerEcran();
            System.out.println("--- Commandes en attente ---");
            System.out.print("Aucune commande en attente, appuyez sur entrée pour poursuivre...");
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
                        System.out.print("Commande introuvable, appuyez sur entrée pour poursuivre...");
                    } else if (commandeTrouvee.getStatut() != StatutCommande.EN_ATTENTE) {
                        System.out.print("Commande déjà acceptée, appuyez sur entrée pour poursuivre...");
                    } else {
                        commandeTrouvee.setStatut(StatutCommande.EN_PREPARATION);
                        System.out.print("La commande a bien été acceptée, appuyez sur entrée pour poursuivre...");
                    }
                    scan.nextLine();
                    quitter = true;
                    break;
                case 2:
                    for (Commande commande : commandesEnAttente){
                        commande.setStatut(StatutCommande.EN_PREPARATION);
                    }
                    System.out.print("Toutes les commandes ont bien été acceptées, appuyez sur entrée pour poursuivre...");
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
        ArrayList <Commande> commandesEnpreparation= serviceLivraison.getCommandesEnpreparation();
        effacerEcran();
        System.out.println("--- Commandes en préparation ---");
        if (commandesEnpreparation.isEmpty()){
            System.out.print("Aucune commande en préparation, appuyez sur entrée pour poursuivre...");
            scan.nextLine();
            return;
        }
        Livreur livreur = serviceLivraison.getLivreurDisponible();
        if (livreur == null){
            for (Commande commande : commandesEnpreparation){
                System.out.println(commande);
            }
            System.out.println("\nAucun livreur n'est disponible pour le moment, appuyez sur entrée pour quitter...");
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
                    for (Commande commandeActuelle : commandesEnpreparation){
                        if (livreur != null){
                            serviceLivraison.ajouterLivraison(new Livraison(livreur, commandeActuelle));
                            livreur = serviceLivraison.getLivreurDisponible();
                        } else {
                            break;
                        }
                        System.out.print("Tous les livreurs ont été assignés, appuyez sur entrée pour poursuivre...");
                        scan.nextLine();
                        quitter = true;
                    }
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
            System.out.println("Total de commandes livrées: " + serviceLivraison.getNbCommandesLivrees());
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
                    commandes = serviceLivraison.getCommandesTrieesParDate(choixUtilisateur==2);
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
            switch (lireEntier()){
                case 0:
                    quitter = true;
                    break;
            }
        }
    }

    public void afficherMenuCommandesLivraisons(){
        System.out.println(
                "------- COMMANDES & LIVRAISONS -------\n" +
                "  [1] Créer une nouvelle commande\n" +
                "  [2] Voir les commandes en attente\n" +                   //Voir les commandes EN_ATTENTE
                "  [3] Voir les commandes en préparation\n" +                     //Voir les commandes EN_PREPARATION
                "  [4] Suivi des livraisons en cours\n" +
                "  [5] Voir toutes les commandes\n" +           //(EN_LIVRAISON)
                "  [0] Retour au menu principal"
        );
    }

    public void menuCommandesLivraisons(){
        boolean quitter = false;
        int choixMenu;
        while (!quitter){
            effacerEcran();
            afficherMenuCommandesLivraisons();
            choixMenu = lireEntier();
            switch (choixMenu){
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
                case 0:
                    quitter = true;
                    break;
            }

        }
    }

    public void afficherMenuClientsLivreurs(){
        System.out.println(
                "------- CLIENTS & LIVREURS -------\n" +
                        "  [1] Gérer les clients\n" +
                        "  [2] Gérer les livreurs\n" +
                        "  [3] Afficher l'état de la flotte\n" +
                        "  [0] Retour au menu principal"
        );
    }

    public void menuClientsLivreurs(){
        boolean quitter = false;
        int choixMenu;
        while (!quitter){
            effacerEcran();
            afficherMenuClientsLivreurs();
            choixMenu = lireEntier();
            switch (choixMenu){
                case 1:
                    menuGestionClients();
                    break;
                case 2:
                    menuGestionLivreurs();
                    break;
                case 3:
                    break;
                case 0:
                    quitter = true;
                    break;
            }

        }
    }

    public void simulerSysteme(){
        for (Commande commandeActuelle: serviceLivraison.getListeCommandes()){
            if (commandeActuelle.getStatut() == StatutCommande.EN_ATTENTE && ChronoUnit.DAYS.between(commandeActuelle.getDateCommande(), ServiceLivraison.jourActuel()) >= 1){
                commandeActuelle.setStatut(StatutCommande.EN_PREPARATION);
            } else if (commandeActuelle.getStatut() == StatutCommande.EN_PREPARATION && ChronoUnit.DAYS.between(commandeActuelle.getDateCommande(), ServiceLivraison.jourActuel()) >= 2) {
                Livreur livreur = serviceLivraison.getLivreurDisponible();
                if (livreur != null){
                    serviceLivraison.ajouterLivraison(new Livraison(livreur, commandeActuelle));
                }
            }
        }

        for (Livraison livraisonActuelle: serviceLivraison.getListeLivraisons()){
            if (livraisonActuelle.getDateLivraison().isEqual(ServiceLivraison.jourActuel())){
                livraisonActuelle.getLivreur().setEstDisponible(true);
                livraisonActuelle.getLivreur().incrementerLivraisons();

                livraisonActuelle.getCommande().setStatut(StatutCommande.LIVREE);
            }
        }
    }

    public void menuSimulation(){
        boolean quitter = false;
        while (!quitter){
            effacerEcran();
            simulerSysteme();
            System.out.println("------- SIMULATION ------- Date actuelle: " + ServiceLivraison.jourActuel().format(formatFrance));
            System.out.println("\nCommandes:");
            serviceLivraison.afficherCommandes();
            System.out.print("\n\n");
            System.out.println("Livraisons en cours:");
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
                    serviceLivraison.ajouterCommande(new Commande(serviceLivraison.getListeClients().getFirst(), "Commande standard", false));
                    break;
                case 3:
                    serviceLivraison.ajouterCommande(new Commande(serviceLivraison.getListeClients().getFirst(), "Commande express", true));
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



    //MENU PRINCIPAL
    public void afficherMenuPrincipal(){
        System.out.println(
                "===========================================\n" +
                "    SYSTÈME DE LIVRAISON - MENU PRINCIPAL  \n" +
                "===========================================\n" +
                "        [1] COMMANDES & LIVRAISONS\n" +
                "        [2] CLIENTS & LIVREURS\n" +
                "        [3] RECHERCHE GLOBALE\n" +
                "        [4] SIMULATION\n" +
                "  \n" +
                "        [5] Statistiques Rapides\n" +
                "        [0] Quitter le système\n" +
                "===========================================");
    }

    void main(){
        serviceLivraison.ajouterClient(new Client("Bosselut", "Oscar", "07 83 60 80 02", "27 rue","oscar.bosselut@edu.ece.fr"));
        boolean quitter = false;
        int choixMenu;
        while (!quitter){
            effacerEcran();
            afficherMenuPrincipal();
            choixMenu = lireEntier();
            switch (choixMenu){
                case 1:
                    menuCommandesLivraisons();
                    break;
                case 2:
                    menuClientsLivreurs();
                    break;
                case 3:
                    break;
                case 4:
                    menuSimulation();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:

            }
        }
    }
}
