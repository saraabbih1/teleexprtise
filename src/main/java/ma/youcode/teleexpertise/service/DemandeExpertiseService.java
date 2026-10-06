package ma.youcode.teleexpertise.service;

import ma.youcode.teleexpertise.enums.Priorite;
import ma.youcode.teleexpertise.enums.StatutDemande;
import java.time.LocalDateTime;
import ma.youcode.teleexpertise.entity.DemandeExpertise;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;

public class DemandeExpertiseService {
    private final DemandeExpertiseRepository repository;

    public DemandeExpertiseService() {
        this.repository = new DemandeExpertiseRepository();
    }

    public DemandeExpertise create(DemandeExpertise demande){

        demande.setStatut(StatutDemande.EN_ATTENTE);

        if(demande.getPriorite() == null){
            demande.setPriorite(Priorite.NORMALE);
        }

        demande.setDateCreation(LocalDateTime.now());

        return repository.save(demande);

    }
}