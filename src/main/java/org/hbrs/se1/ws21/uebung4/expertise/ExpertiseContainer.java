package org.hbrs.se1.ws21.uebung4.expertise;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;

/**
 * This {@link Container} implementation is responsible for handling {@link Expertise} instances
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpertiseContainer extends Container<Expertise> {
    /**
     * Applying the singleton pattern to the {@link Container} implementation
     */
    private static final ExpertiseContainer CONTAINER_INSTANCE = new ExpertiseContainer();

    /**
     * This function returns the singleton instance of this {@link Container} implemenation
     *
     * @return ExpertiseContainer singleton instance
     */
    public static ExpertiseContainer getInstance() {
        return CONTAINER_INSTANCE;
    }
}
