package org.hbrs.se1.ws21.uebung2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Container {
    private static final Container CONTAINER = new Container();

    private final List<Member> members = new ArrayList<>();

    @Setter
    private PersistenceStrategy<Member> strategy;

    public static Container getContainer() {
        return CONTAINER;
    }

    public void store() throws PersistenceException {
        if (this.strategy == null) {
            throw new NullPointerException("PersistenceStrategy is null");
        }
        this.strategy.save(this.members);
    }

    public void load() throws PersistenceException {
        if (this.strategy == null) {
            throw new NullPointerException("PersistenceStrategy is null");
        }
        final List<Member> loadedMembers = this.strategy.load();
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

    // Kurz erläutern welche Nachteile sich durch diesen Rückgabewert ergeben
    public boolean deleteMember(Integer id) {
        return this.members.removeIf(member -> member.getID().equals(id));
    }

    public void dump() {
        this.members.forEach(member -> System.out.println(member.toString()));
    }

    public int size() {
        return this.members.size();
    }
}
