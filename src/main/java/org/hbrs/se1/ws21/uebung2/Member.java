package org.hbrs.se1.ws21.uebung2;

/**
 * This interface represents a member. In order to provide unique member
 * instances, this interface is inherited from the {@link IdAware} interface
 */
public interface Member extends IdAware {
    /**
     * This function should return the current member instance as a string
     *
     * @return String containing an expressive definition for this member instance
     */
    String toString();
}
