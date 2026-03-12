public class Personne {
    private final int id;
    private String nom;
    private String prenom;
    private String telephone;

    private static int compteurId = 1;

    public Personne(String nom, String prenom, String telephone){
        this.id = compteurId;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        compteurId++;
    }

    public int getId(){
        return id;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public String getTel(){
        return telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public String toString(){
        return "Id: " + id + " | Nom: " + nom + " | Prenom: " + prenom + " | Tel:" + telephone;
    }
}
