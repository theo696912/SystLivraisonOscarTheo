import java.util.ArrayList;

public class ServiceLivraison {
    private final ArrayList <Client> listeClients;

    public ServiceLivraison(){
        this.listeClients = new ArrayList<>();
    }

    public void ajouterClient(Client client){
        listeClients.add(client);
    }

    public boolean supprimerClient(int idASupprimer){
        return listeClients.removeIf(client -> client.getId() == idASupprimer);
    }

    public int getClientListSize(){
        return listeClients.size();
    }

    public Client rechercheClientParId(int idRecherche){
        for (Client client : listeClients){
            if (client.getId() == idRecherche){
                return client;
            }
        }
        return null;
    }

    public void afficherClients(){
        for (Client client : listeClients) {
            System.out.println(client.toString());
        }
    }
}
