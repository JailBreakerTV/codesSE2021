package org.hbrs.se1.ws21.uebung2;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class is an implementation of the member interface
 */
public class MemberImpl implements Member, Serializable {

    /**
     * The version of this member implementation
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id of this member
     */
    private final Integer id;

    /**
     * This constructor instantiates the member with a given id
     *
     * @param id of this member
     */
    public MemberImpl(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("Member (ID = %s)", this.getId());
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
