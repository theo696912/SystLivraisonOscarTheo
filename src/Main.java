import java.util.Scanner;

public class Main {
    private ServiceLivraison serviceLivraison = new ServiceLivraison();
    private Scanner scan = new Scanner(System.in);

    public void effacerEcran(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 30; i++){
                System.out.println();
            }
        }
    }

    public void saisieEtAjoutClient(){
        String nom;
        String prenom;
        String tel;
        String adresse;
        String email;
        boolean champVide = false;

        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
            System.out.print("Saisissez le nom du client: ");
            nom = scan.nextLine();
            champVide = nom.isBlank();
        } while(champVide);

        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
            System.out.print("Saisissez le prénom du client: ");
            prenom = scan.nextLine();
            champVide = prenom.isBlank();
        } while (champVide);

        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
            System.out.print("Saisissez le numéro de téléphone du client: ");
            tel = scan.nextLine();
            champVide = tel.isBlank();
        } while (champVide);

        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
            System.out.print("Saisissez l'adresse du client: ");
            adresse = scan.nextLine();
            champVide = adresse.isBlank();
        } while (champVide);

        do {
            effacerEcran();
            if (champVide){System.out.println("Champ vide !");}
            System.out.print("Saisissez l'email du client: ");
            email = scan.nextLine();
            champVide = email.isBlank();
        } while (champVide);


        serviceLivraison.ajouterClient(new Client(nom, prenom, tel, adresse, email));
    }

    public void supprimerClient(){
        effacerEcran();
        System.out.print("Entrez l'id du client à supprimer: ");
        if (serviceLivraison.supprimerClient(scan.nextInt())){
            System.out.println("\nClient supprimé avec succès !\nAppuyez sur entrée pour continuer...");
        } else {
            System.out.println("\nAucun client trouvé avec cet ID.\nAppuyez sur entrée pour continuer...");
        }
        scan.nextLine();
        scan.nextLine();
    }

    public void afficherMenuModifClient(Client clientAmodifier){
        System.out.println("Un client à été trouvé :\n" + clientAmodifier + "\n");
        System.out.println("Que voulez vous modifier ?\n1 - Nom\n2 - Prenom\n3 - Téléphone\n4 - Adresse\n5 - Email\n0 - Retour");
    }

    public void modifierInfosClient(){
        effacerEcran();
        int idAmodifier;
        System.out.print("Entrez l'id du client dont vous voulez modifier les informations (0 pour revenir): ");
        idAmodifier = scan.nextInt();
        if (idAmodifier == 0){
            return;
        }
        Client clientAmodifier = serviceLivraison.rechercheClientParId(idAmodifier);
        scan.nextLine();

        if (clientAmodifier != null){
            effacerEcran();
            int choixMenu;
            boolean retourMenu = false;

            afficherMenuModifClient(clientAmodifier);

            while (!retourMenu) {
                choixMenu = scan.nextInt();
                scan.nextLine();
                switch (choixMenu){
                    case 1:
                        effacerEcran();
                        System.out.print("Saisissez le nouveau nom du client (actuel: " + clientAmodifier.getNom() + "): ");
                        String nouveauNom = scan.nextLine();
                        if (nouveauNom.isBlank()){
                            System.out.println("Champ vide, appuyez sur entrée pour revenir...");
                        } else {
                            clientAmodifier.setNom(nouveauNom);
                            System.out.println("Le nom a bien été modifié ! Appuyez sur entrée pour quitter...");
                        }
                        scan.nextLine();
                        effacerEcran();
                        afficherMenuModifClient(clientAmodifier);
                        break;
                    case 2:
                        effacerEcran();
                        System.out.print("Saisissez le nouveau prenom du client (actuel: " + clientAmodifier.getPrenom() + "): ");
                        String nouveauPrenom = scan.nextLine();
                        if (nouveauPrenom.isBlank()){
                            System.out.println("Champ vide, appuyez sur entrée pour revenir...");
                        } else {
                            clientAmodifier.setPrenom(nouveauPrenom);
                            System.out.println("Le prenom a bien été modifié ! Appuyez sur entrée pour quitter...");
                        }
                        scan.nextLine();
                        effacerEcran();
                        afficherMenuModifClient(clientAmodifier);
                        break;
                    case 3:
                        effacerEcran();
                        System.out.print("Saisissez le nouveau numéro de téléphone du client (actuel: " + clientAmodifier.getTel() + "): ");
                        String nouveauTel = scan.nextLine();
                        if (nouveauTel.isBlank()){
                            System.out.println("Champ vide, appuyez sur entrée pour revenir...");
                        } else {
                            clientAmodifier.setTelephone(nouveauTel);
                            System.out.println("Le numéro de téléphone a bien été modifié ! Appuyez sur entrée pour quitter...");
                        }
                        scan.nextLine();
                        effacerEcran();
                        afficherMenuModifClient(clientAmodifier);
                        break;
                    case 4:
                        effacerEcran();
                        System.out.print("Saisissez la nouvelle adresse du client (actuelle: " + clientAmodifier.getAdresse() + "): ");
                        String nouvelleAdresse = scan.nextLine();
                        if (nouvelleAdresse.isBlank()){
                            System.out.println("Champ vide, appuyez sur entrée pour revenir...");
                        } else {
                            clientAmodifier.setAdresse(nouvelleAdresse);
                            System.out.println("L'adresse a bien été modifiée ! Appuyez sur entrée pour quitter...");
                        }
                        scan.nextLine();
                        effacerEcran();
                        afficherMenuModifClient(clientAmodifier);
                        break;
                    case 5:
                        effacerEcran();
                        System.out.print("Saisissez la nouvelle adresse email du client (actuelle: " + clientAmodifier.getEmail() + "): ");
                        String nouvelleAdresseEmail = scan.nextLine();
                        if (nouvelleAdresseEmail.isBlank()){
                            System.out.println("Champ vide, appuyez sur entrée pour revenir...");
                        } else {
                            clientAmodifier.setEmail(nouvelleAdresseEmail);
                            System.out.println("L'adresse email a bien été modifiée ! Appuyez sur entrée pour quitter...");
                        }
                        scan.nextLine();
                        effacerEcran();
                        afficherMenuModifClient(clientAmodifier);
                        break;
                    case 0:
                        retourMenu = true;
                        break;
                    default:
                }
            }

        } else {
            System.out.println("Client introuvable.\nAppuyez sur entrée pour continuer...");
            scan.nextLine();
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
                    supprimerClient();
                    effacerEcran();
                    afficherMenuGestionClients();
                    break;
                case 3:
                    modifierInfosClient();
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
