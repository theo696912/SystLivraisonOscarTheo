public enum StatutCommande {
    EN_ATTENTE("En attente"),
    EN_PREPARATION("En préparation"),
    EN_LIVRAISON("En livraison"),
    LIVREE("Livrée"),
    ANNULEE("Annulée");

    private final String libelle;

    StatutCommande(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle(){
        return libelle;
    }

}
