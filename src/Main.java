import java.util.Scanner;

public class Main {
    private ServiceLivraison serviceLivraison = new ServiceLivraison();
    private Scanner scan = new Scanner(System.in);

    public void effacerEcran(){
        for (int i = 0; i < 30; i++){
            System.out.println();
        }
    }

    public void afficherMenuGestionClients(){
        System.out.println("Que souhaitez vous faire ?" +
                "\n" +
                "\n" +
                "1 - Ajouter un client\n" +
                "2 - Supprimer un client\n" +
                "3 - Modifier les informations d'un client\n" +
                "4 - Afficher la liste des clients\n" +
                "5 - Rechercher un client\n" +
                "0 - Revenir au menu principal");
    }

    public void menuGestionClients(){
        boolean quitter = false;
        int choixMenu;
        effacerEcran();
        afficherMenuGestionClients();
        while (!quitter){
            choixMenu = scan.nextInt();
            scan.nextLine();

            switch (choixMenu){
                case 1:
                    saisieEtAjoutClient();
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 2:
                    effacerEcran();
                    System.out.print("Entrez l'id du client à supprimer: ");
                    if (serviceLivraison.supprimerClient(scan.nextInt())){
                        System.out.println("\nClient supprimé avec succès !\nAppuyez sur entrée pour continuer...");
                    } else {
                        System.out.println("\nAucun client trouvé avec cet ID.\nAppuyez sur entrée pour continuer...");
                    }
                    scan.nextLine();
                    scan.nextLine();
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 3:
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 4:
                    effacerEcran();
                    System.out.println("Liste des clients:");
                    serviceLivraison.afficherClients();
                    System.out.println("Appuyez sur entrée pour continuer...");
                    scan.nextLine();
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 5:
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:
            }
        }
    }

    public void saisieEtAjoutClient(){
        System.out.print("Saisissez le nom du client: ");
        String nom = scan.nextLine();
        System.out.print("\nSaisissez le prénom du client: ");
        String prenom = scan.nextLine();
        System.out.print("\nSaisissez le numéro de téléphone du client: ");
        String tel = scan.nextLine();
        System.out.print("\nSaisissez l'adresse du client: ");
        String adresse = scan.nextLine();
        System.out.print("\nSaisissez l'email du client: ");
        String email = scan.nextLine();

        serviceLivraison.ajouterClient(new Client(nom, prenom, tel, adresse, email));
    }

    public void afficherMenuPrincipal(){
        System.out.println("Système de Livraison" +
                "\n" +
                "\n" +
                "1 - Gestion des clients\n" +
                "2 - Gestion des livreurs\n" +
                "3 - Gestion des commandes\n" +
                "4 - Gestion des livraisons\n" +
                "5 - Affichage et consultation\n" +
                "0 - Quitter");
    }

    void main(){
        boolean quitter = false;
        int choixMenu;
        effacerEcran();
        afficherMenuPrincipal();
        while (!quitter){
            choixMenu = scan.nextInt();
            scan.nextLine();
            switch (choixMenu){
                case 1:
                    menuGestionClients();
                    effacerEcran();
                    afficherMenuPrincipal();
                    break;
                case 2:
                    effacerEcran();
                    afficherMenuPrincipal();
                    break;
                case 3:
                    effacerEcran();
                    afficherMenuPrincipal();
                    break;
                case 4:
                    effacerEcran();
                    afficherMenuPrincipal();
                    break;
                case 5:
                    effacerEcran();
                    afficherMenuPrincipal();
                    break;
                case 0:
                    quitter = true;
                    break;
                default:

            }
        }
    }
}
