package org.hbrs.se1.ws21.uebung4.expertise;

import lombok.Data;
import org.hbrs.se1.ws21.uebung2.IdAware;

@Data
public final class Expertise implements IdAware {
    private final Integer id;
    private final String title;

    @Override
    public Integer getId() {
        return this.id;
    }
}
