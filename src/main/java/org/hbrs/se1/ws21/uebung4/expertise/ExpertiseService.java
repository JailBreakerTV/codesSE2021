package org.hbrs.se1.ws21.uebung4.expertise;

import org.hbrs.se1.ws21.uebung2.ContainerException;

import java.util.Optional;

public interface ExpertiseService {


    Optional<Expertise> findExpertiseById(Integer id);

    Optional<Expertise> findExpertiseByTitle(String title);

    void addExpertise(Expertise expertise) throws ContainerException;

    boolean removeExpertise(Integer id);

    boolean removeExpertise(String title);
}