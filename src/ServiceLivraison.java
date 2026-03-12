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

    public void afficherClients(){
        for (int i = 0; i < listeClients.size(); i++){
            System.out.println(listeClients.get(i).toString());
        }
    }
}
