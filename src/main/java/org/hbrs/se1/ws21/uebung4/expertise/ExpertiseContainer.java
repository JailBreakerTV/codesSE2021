package org.hbrs.se1.ws21.uebung4.expertise;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpertiseContainer extends Container<Expertise> {
    private static final ExpertiseContainer CONTAINER_INSTANCE = new ExpertiseContainer();

    public static ExpertiseContainer getInstance() {
        return CONTAINER_INSTANCE;
    }
}
