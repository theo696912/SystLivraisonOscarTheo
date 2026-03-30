public class Livreur extends Personne{
    private String vehicule;
    private boolean estDisponible;
    private int nbLivraisons;

    public Livreur(String nom, String prenom, String telephone, String vehicule){
        super(nom, prenom, telephone);
        this.vehicule = vehicule;
        estDisponible = true;
        nbLivraisons = 0;
    }

    public Livreur(int id, String nom, String prenom, String telephone, String vehicule){
        super(id, nom, prenom, telephone);
        this.vehicule = vehicule;
        estDisponible = true;
        nbLivraisons = 0;
    }

    public String getVehicule(){
        return vehicule;
    }

    public boolean getEstDisponible(){return estDisponible;}

    public int getNbLivraisons(){return nbLivraisons;}

    public void setEstDisponible(boolean estDisponible){this.estDisponible = estDisponible;}

    public void setVehicule(String vehicule){
        this.vehicule = vehicule;
    }

    public void incrementerLivraisons(){nbLivraisons++;}

    @Override
    public String toString(){
        if (estDisponible){
            return super.toString() + " | Vehicule: " + vehicule + " | Disponble | Nombre de livraisons: " + nbLivraisons;
        }
        return super.toString() + " | Vehicule: " + vehicule + " | Indisponble | Nombre de livraisons: " + nbLivraisons;
    }
}
