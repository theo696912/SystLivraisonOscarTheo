import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Commande {
    private int id;
    private Client client;
    private String description;
    private LocalDate dateCommande;
    private StatutCommande statut;
    private boolean estExpress;

    DateTimeFormatter formatFrance = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private static int compteurId = 1;

    public Commande(Client client, String description, boolean estExpress){
        id = compteurId;
        this.client = client;
        this.description = description;
        this.dateCommande = ServiceLivraison.jourActuel();
        statut = StatutCommande.EN_ATTENTE;
        this.estExpress = estExpress;
        compteurId++;
    }

    public void setStatut(StatutCommande nouveauStatut){
        statut = nouveauStatut;
    }

    public LocalDate getDateCommande(){return dateCommande;}
    public StatutCommande getStatut(){
        return statut;
    }
    public int getId(){return id;}
    public boolean getEstExpress(){return estExpress;}
    public String toString(){
        return "Id: " + id + " | Client: " + client.getNom() + " " + client.getPrenom() + " | Description: " + description + " | Date: " + dateCommande.format(formatFrance) + " | Statut: " + statut.getLibelle();
    }

}
