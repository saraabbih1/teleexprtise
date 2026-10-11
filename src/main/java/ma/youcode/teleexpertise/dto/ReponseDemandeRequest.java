
package ma.youcode.teleexpertise.dto;

public class ReponseDemandeRequest {

    private String avis;
    private String recommandations;

    public ReponseDemandeRequest() {
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }
}
