import java.util.ArrayList;

public class ServiceLivraison {
    private final ArrayList <Client> listeClients;
    private final ArrayList <Livreur> listeLivreurs;

    public ServiceLivraison(){
        this.listeClients = new ArrayList<>();
        this.listeLivreurs = new ArrayList<>();
    }

    public void ajouterClient(Client client){
        listeClients.add(client);
    }
    public void ajouterLivreur(Livreur livreur){ listeLivreurs.add(livreur); }

    public boolean supprimerClient(int idASupprimer){
        return listeClients.removeIf(client -> client.getId() == idASupprimer);
    }
    public boolean supprimerLivreur(int idASupprimer){
        return listeLivreurs.removeIf(livreur -> livreur.getId() == idASupprimer);
    }

    public int getClientsListSize(){
        return listeClients.size();
    }
    public int getLivreursListSize(){ return listeLivreurs.size(); }

    public ArrayList<Client> getListeClients(){
        return new ArrayList<>(listeClients);
    }

    public ArrayList<Livreur> getListeLivreurs(){
        return new ArrayList<>(listeLivreurs);
    }


    public Client rechercheClientParId(int idRecherche){
        for (Client client : listeClients){
            if (client.getId() == idRecherche){
                return client;
            }
        }
        return null;
    }

    public Livreur rechercheLivreurParId(int idRecherche){
        for (Livreur livreur : listeLivreurs){
            if (livreur.getId() == idRecherche){
                return livreur;
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

    public void afficherClients(){
        for (Client client : listeClients) {
            System.out.println(client);
        }
    }

    public void afficherLivreurs(){
        for (Livreur livreur: listeLivreurs){
            System.out.println(livreur);
        }
    }
}
