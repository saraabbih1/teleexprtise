package ma.youcode.teleexpertise.service;

import ma.youcode.teleexpertise.enums.Priorite;
import ma.youcode.teleexpertise.enums.StatutDemande;
import java.time.LocalDateTime;
import ma.youcode.teleexpertise.entity.DemandeExpertise;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;
import ma.youcode.teleexpertise.exception.DemandeExpertiseException;


public class DemandeExpertiseService {
    private final DemandeExpertiseRepository repository;

    public DemandeExpertiseService() {
        this.repository = new DemandeExpertiseRepository();
    }

    public DemandeExpertise create(DemandeExpertise demande){

        validate(demande);

        demande.setStatut(StatutDemande.EN_ATTENTE);

        if(demande.getPriorite() == null){
            demande.setPriorite(Priorite.NORMALE);
        }

        demande.setDateCreation(LocalDateTime.now());

        return repository.save(demande);

    }

    private void validate(DemandeExpertise demande) {

        if (demande == null) {
            throw new DemandeExpertiseException(
                    "La demande d'expertise est obligatoire."
            );
        }

        if (demande.getMotif() == null || demande.getMotif().isBlank()) {
            throw new DemandeExpertiseException(
                    "Le motif est obligatoire."
            );
        }

        if (demande.getDescription() == null || demande.getDescription().isBlank()) {
            throw new DemandeExpertiseException(
                    "La description est obligatoire."
            );
        }
    }
}