package org.hbrs.se1.ws21.uebung2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This inherited class takes care of the management of member instances.
 * It should be mentioned that this class applies the Singleton pattern.
 *
 * @see Container
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberContainer extends Container<Member> {
    /**
     * The instance of this container
     */
    private static final MemberContainer MEMBER_CONTAINER = new MemberContainer();

    /**
     * This function provides the previously created instance
     *
     * @return MemberContainer as a singleton
     */
    public static MemberContainer getInstance() {
        return MEMBER_CONTAINER;
    }
}
