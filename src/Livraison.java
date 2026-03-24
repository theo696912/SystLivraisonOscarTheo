import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Livraison {
    private int id;
    private Livreur livreur;
    private Commande commande;
    private LocalDate dateAssignation;
    private LocalDate dateLivraison;

    private static int compteurId = 1;
    DateTimeFormatter formatFrance = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public Livraison(Livreur livreur, Commande commande){
        id = compteurId;
        this.livreur = livreur;
        this.commande = commande;
        dateAssignation = ServiceLivraison.jourActuel();
        dateLivraison = null;
        if (commande.getEstExpress()){
            dateLivraison = ServiceLivraison.jourActuel().plusDays(2);
        } else {
            dateLivraison = ServiceLivraison.jourActuel().plusDays(6);
        }
        compteurId++;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public Commande getCommande(){
        return commande;
    }

    public LocalDate getDateLivraison(){
        return dateLivraison;
    }

    public void terminerLivraison(){
        dateLivraison = ServiceLivraison.jourActuel();
        commande.setStatut(StatutCommande.LIVREE);
    }

    public String toString(){
        return "Livraison n°" + id + " | Commande n°" + commande.getId() + " | Livreur: " + livreur.getNom() + " " + livreur.getPrenom() + " | Date d'assignation: " + dateAssignation.format(formatFrance) + " | Date de livraison prévue: " + dateLivraison.format(formatFrance) + " | Livraison express: " + commande.getEstExpress();
    }
}
