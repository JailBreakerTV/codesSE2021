package org.hbrs.se1.ws21.uebung4.controller.expertise;

import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung4.model.Expertise;

import java.util.Optional;

public final class ExpertiseServiceImpl implements ExpertiseService {
    private final ExpertiseContainer expertiseContainer;

    public ExpertiseServiceImpl(ExpertiseContainer expertiseContainer) {
        this.expertiseContainer = expertiseContainer;
    }

    @Override
    public Optional<Expertise> findExpertiseById(Integer id) {
        return this.expertiseContainer.get(id);
    }

    @Override
    public Optional<Expertise> findExpertiseByTitle(String title) {
        return this.expertiseContainer.getCurrentList().stream()
                .filter(expertise -> expertise.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public void addExpertise(Expertise expertise) throws ContainerException {
        this.expertiseContainer.addMember(expertise);
    }

    @Override
    public boolean removeExpertiseById(Integer id) {
        return this.expertiseContainer.deleteMember(id);
    }

    @Override
    public boolean removeExpertiseByString(String title) {
        return this.findExpertiseByTitle(title).map(expertise -> this.removeExpertiseById(expertise.getId())).orElse(false);
    }
}
