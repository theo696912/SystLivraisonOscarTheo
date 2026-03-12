public class Client extends Personne{
    private String adresse;
    private String email;

    public Client(String nom, String prenom, String telephone, String adresse, String email){
        super(nom,prenom,telephone);
        this.adresse = adresse;
        this.email = email;
    }

    public String getAdresse(){
        return adresse;
    }

    public String getEmail(){
        return email;
    }

    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String toString(){
        return super.toString() + " | Adresse: " + adresse + " | Email: " + email;
    }
}
