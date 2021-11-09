package org.hbrs.se1.ws21.uebung4.expertise;

import org.hbrs.se1.ws21.uebung2.ContainerException;

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
    public boolean removeExpertise(Integer id) {
        return this.expertiseContainer.deleteMember(id);
    }

    @Override
    public boolean removeExpertise(String title) {
        return this.findExpertiseByTitle(title).map(expertise -> this.removeExpertise(expertise.getId())).orElse(false);
    }
}
