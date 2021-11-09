package org.hbrs.se1.ws21.uebung4.controller.expertise;

import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung4.model.Expertise;

import java.util.Optional;

/**
 * This service interface should handle some operations in relation to the {@link Expertise} entity
 */
public interface ExpertiseService {
    /**
     * This function will search for an {@link Expertise} with an given id
     *
     * @param id of the {@link Expertise}
     * @return Optional either empty or containing the {@link Expertise} value
     */
    Optional<Expertise> findExpertiseById(Integer id);

    /**
     * This function will search for an {@link Expertise} with an given title
     *
     * @param title of the {@link Expertise}
     * @return Optional either empty or containing the {@link Expertise} value
     */
    Optional<Expertise> findExpertiseByTitle(String title);

    /**
     * This function will add an new {@link Expertise}
     *
     * @param expertise which should be added
     * @throws ContainerException when an error occurs in the {@link ExpertiseContainer}
     */
    void addExpertise(Expertise expertise) throws ContainerException;

    /**
     * This function will remove an {@link Expertise} by its id
     *
     * @param id of the {@link Expertise} which should be removed
     * @return either true if the {@link Expertise} was removed successfully or
     * false if there is no {@link Expertise} with that id
     */
    boolean removeExpertiseById(Integer id);

    /**
     * This function will remove an {@link Expertise} by its title
     *
     * @param title of the {@link Expertise} which should be removed
     * @return either true if the {@link Expertise} was removed successfully or
     * false if there is no {@link Expertise} with that title
     */
    boolean removeExpertiseByString(String title);
}