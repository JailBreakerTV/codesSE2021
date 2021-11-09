package org.hbrs.se1.ws21.uebung4.model;

import lombok.Data;
import org.hbrs.se1.ws21.uebung2.IdAware;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * This class represents an expertise an {@link Employee} can have
 */
@Data
public final class Expertise implements IdAware, Serializable, Comparable<Expertise> {
    /**
     * The id of this expertise
     */
    private final Integer id;

    /**
     * The title of this expertise
     */
    private final String title;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public int compareTo(@NotNull Expertise expertise) {
        return this.id.compareTo(expertise.id);
    }
}
