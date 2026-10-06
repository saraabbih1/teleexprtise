package ma.youcode.teleexpertise.service;

import java.util.List;

import ma.youcode.teleexpertise.entity.Specialiste;
import ma.youcode.teleexpertise.entity.Specialite;
import ma.youcode.teleexpertise.repository.SpecialisteRepository;

public class SpecialisteService {

    private final SpecialisteRepository repository;

    public SpecialisteService() {
        this.repository = new SpecialisteRepository();
    }

    public List<Specialiste> getSpecialistes(Specialite specialite) {
        return repository.findBySpecialite(specialite)
                .stream()
                .sorted((s1, s2) -> Double.compare(s1.getTarif(), s2.getTarif()))
                .toList();
    }
}