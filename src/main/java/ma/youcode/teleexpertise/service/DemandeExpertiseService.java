package ma.youcode.teleexpertise.service;

import java.time.LocalDateTime;
import ma.youcode.teleexpertise.exception.NotFoundException;
import ma.youcode.teleexpertise.entity.DemandeExpertise;
import ma.youcode.teleexpertise.enums.Priorite;
import ma.youcode.teleexpertise.enums.StatutDemande;
import ma.youcode.teleexpertise.exception.DemandeExpertiseException;
import ma.youcode.teleexpertise.repository.DemandeExpertiseRepository;

public class DemandeExpertiseService {

    private final DemandeExpertiseRepository repository;

    public DemandeExpertiseService() {
        this.repository = new DemandeExpertiseRepository();
    }

    public DemandeExpertise create(DemandeExpertise demande) {

        validate(demande);

        if (!repository.specialisteExiste(demande.getSpecialisteId())) {
        throw new NotFoundException(
            "Le spécialiste n'existe pas."
            );
        }

        if (!repository.consultationExiste(demande.getConsultationId())) {
        throw new NotFoundException(
            "La consultation n'existe pas."
            );
        }

        demande.setStatut(StatutDemande.EN_ATTENTE);

        if (demande.getPriorite() == null) {
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

        if (demande.getQuestion() == null
                || demande.getQuestion().isBlank()) {
            throw new DemandeExpertiseException(
                    "La question est obligatoire."
            );
        }

        if (demande.getPriorite() == null) {
            throw new DemandeExpertiseException(
                    "La priorité est obligatoire."
            );
        }

        if (demande.getConsultationId() == null) {
            throw new DemandeExpertiseException(
                    "La consultation est obligatoire."
            );
        }

        if (demande.getSpecialisteId() == null) {
            throw new DemandeExpertiseException(
                    "Le spécialiste est obligatoire."
            );
        }
    }
}