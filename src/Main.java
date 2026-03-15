import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //VARIABLES GLOBALES
    private ServiceLivraison serviceLivraison = new ServiceLivraison();
    private Scanner scan = new Scanner(System.in);

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


    //METHODES CLIENTS
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
                for (Client client : serviceLivraison.getListeClients()){
                    System.out.println(client);
                }
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
                for (Livreur livreur : serviceLivraison.getListeLivreurs()){
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
        effacerEcran();
        String recherche = lireString("Saisissez un mot clé (nom, prenom, email ...): ");
        ArrayList<Client> clientsTrouves = serviceLivraison.rechercheClient(recherche);
        if (clientsTrouves.isEmpty()){
            System.out.println("Aucun client n'a été trouvé avec le mot clé \"" + recherche +  "\". Appuyez sur entrée pour revenir...");
            scan.nextLine();
        } else if (clientsTrouves.size() == 1){
            effacerEcran();
            menuSupressionModificationEntite(clientsTrouves.getFirst());
        } else {
            boolean quitter = false;
            while (!quitter){
                effacerEcran();
                System.out.println("Plusieurs clients ont été trouvés:");
                for (Client client : clientsTrouves){
                    System.out.println(client);
                }
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
            }
        }
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

    //METHODES LIVREURS

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

    public void afficherMenuCommandesLivraisons(){
        System.out.println(
                "------- COMMANDES & LIVRAISONS -------\n" +
                "  [1] Prendre une nouvelle commande\n" +
                "  [2] Assigner un livreur\n" +                     //(Voir les commandes EN_ATTENTE
                "  [3] Valider une livraison terminée\n" +
                "  [4] Suivi des livraisons en cours\n" +           //(EN_LIVRAISON)
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
                    break;
                case 2:
                    break;
                case 3:
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



    //MENU PRINCIPAL
    public void afficherMenuPrincipal(){
        System.out.println(
                "===========================================\n" +
                "    SYSTÈME DE LIVRAISON - MENU PRINCIPAL  \n" +
                "===========================================\n" +
                "        [1] COMMANDES & LIVRAISONS\n" +
                "        [2] CLIENTS & LIVREURS\n" +
                "        [3] RECHERCHE GLOBALE\n" +
                "  \n" +
                "        [4] Statistiques Rapides\n" +
                "        [0] Quitter le système\n" +
                "===========================================");
    }

    void main(){
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
                    break;
                case 0:
                    quitter = true;
                    break;
                default:

            }
        }
    }
}
