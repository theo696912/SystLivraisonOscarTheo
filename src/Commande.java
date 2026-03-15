import java.time.LocalDate;

public class Commande {
    private int id;
    private Client client;
    private String description;
    private LocalDate date;
    private StatutCommande statut;


    private static int compteurId = 1;

    public Commande(Client client, String description, LocalDate date){
        id = compteurId;
        this.client = client;
        this.description = description;
        this.date = date;
        statut = StatutCommande.EN_ATTENTE;
        compteurId++;
    }

    public void setStatut(StatutCommande nouveauStatut){
        statut = nouveauStatut;
    }

    public StatutCommande getStatut(){
        return statut;
    }

}
