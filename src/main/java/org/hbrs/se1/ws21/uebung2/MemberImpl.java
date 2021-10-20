package org.hbrs.se1.ws21.uebung2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class MemberImpl implements Member, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id;

    public MemberImpl(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("Member (ID = %s)", this.getID());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final MemberImpl member = (MemberImpl) obj;
        return Objects.equals(this.id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
