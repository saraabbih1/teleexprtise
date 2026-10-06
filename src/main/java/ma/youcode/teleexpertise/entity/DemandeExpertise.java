package ma.youcode.teleexpertise.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ma.youcode.teleexpertise.enums.*;

@Entity 
public class DemandeExpertise {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motif;

    private String description;

    @Enumerated (EnumType.STRING)
    private StatutDemande statut;

    @Enumerated (EnumType.STRING)
    private Priorite priorite;

    private LocalDateTime dateCreation;

    public DemandeExpertise(){

    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getMotif(){
        return motif;
    }

    public void setMotif(String motif){
        this.motif = motif;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public StatutDemande getStatut(){
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }


}
