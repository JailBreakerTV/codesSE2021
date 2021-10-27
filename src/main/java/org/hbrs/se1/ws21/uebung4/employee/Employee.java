package org.hbrs.se1.ws21.uebung4.employee;

import lombok.Data;
import org.hbrs.se1.ws21.uebung2.Member;

@Data
public final class Employee implements Member {
    private final Integer id;

    @Override
    public Integer getId() {
        return this.id;
    }
}
