import java.time.LocalDate;

public class Livraison {
    private final int id;
    private final Livreur livreur;
    private final Commande commande;
    private final LocalDate dateAssignation;
    private final LocalDate dateLivraison;

    private static int compteurId = 1;

    public Livraison(Livreur livreur, Commande commande){
        id = compteurId;
        this.livreur = livreur;
        this.commande = commande;
        dateAssignation = ServiceLivraison.getJourActuel();
        if (commande.getEstExpress()){
            dateLivraison = ServiceLivraison.getJourActuel().plusDays(2);
        } else {
            dateLivraison = ServiceLivraison.getJourActuel().plusDays(4);
        }
        compteurId++;
    }

    public Livreur getLivreur() {return livreur;}

    public Commande getCommande(){
        return commande;
    }

    public LocalDate getDateLivraison(){
        return dateLivraison;
    }

    public void terminerLivraison(){
        livreur.setEstDisponible(true);
        livreur.incrementerLivraisons();
        commande.setStatut(StatutCommande.LIVREE);
    }

    @Override
    public String toString(){
        return "Livraison n°" + id + " | Commande n°" + commande.getId() + " | Livreur: " + livreur.getNom() + " " + livreur.getPrenom() + " | Date d'assignation: " + dateAssignation.format(ServiceLivraison.FORMAT_FRANCE) + " | Date de livraison prévue: " + dateLivraison.format(ServiceLivraison.FORMAT_FRANCE) + " | Livraison express: " + commande.getEstExpress();
    }
}
