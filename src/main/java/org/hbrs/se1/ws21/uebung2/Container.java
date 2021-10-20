package org.hbrs.se1.ws21.uebung2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Container {
    private static final Container CONTAINER = new Container();

    private final List<Member> members = new ArrayList<>();

    @Getter
    @Setter
    private PersistenceStrategy<Member> strategy;

    public static Container getContainer() {
        return CONTAINER;
    }

    public void store() throws PersistenceException {
        if (this.strategy == null) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "There is no strategy");
        }
        this.strategy.save(this.members);
    }

    public void load() throws PersistenceException {
        if (this.strategy == null) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "There is no strategy");
        }
        final List<Member> loadedMembers = this.strategy.load();
        if (loadedMembers == null) {
            throw new NullPointerException("Can not load Members (loadedMembers = null)");
        }
        this.members.removeIf(member -> loadedMembers.stream().anyMatch(member::equals));
        this.members.addAll(loadedMembers);
    }

    public void addMember(Member member) throws ContainerException {
        if (this.hasMember(member.getID())) {
            throw new ContainerException(member);
        }
        this.members.add(member);
    }

    public boolean hasMember(Integer id) {
        return this.members.stream().anyMatch(member -> member.getID().equals(id));
    }

    public boolean deleteMember(Integer id) {
        return this.members.removeIf(member -> member.getID().equals(id));
    }

    public List<Member> getCurrentList() {
        return this.members;
    }

    public int size() {
        return this.members.size();
    }
}
