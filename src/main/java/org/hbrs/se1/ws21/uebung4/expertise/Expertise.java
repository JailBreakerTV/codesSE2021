package org.hbrs.se1.ws21.uebung4.expertise;

import lombok.Data;
import org.hbrs.se1.ws21.uebung2.IdAware;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
public final class Expertise implements IdAware, Serializable, Comparable<Expertise> {
    private final Integer id;
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
