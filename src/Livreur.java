public class Livreur extends Personne{
    private String vehicule;

    public Livreur(String nom, String prenom, String telephone, String vehicule){
        super(nom, prenom, telephone);
        this.vehicule = vehicule;
    }

    public String getVehicule(){
        return vehicule;
    }

    public void setVehicule(String vehicule){
        this.vehicule = vehicule;
    }

    public String toString(){
        return super.toString() + " | Vehicule: " + vehicule;
    }
}
