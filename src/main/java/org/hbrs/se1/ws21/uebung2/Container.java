package org.hbrs.se1.ws21.uebung2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Container {
    private static final Container CONTAINER = new Container();

    private final List<Member> members = new ArrayList<>();

    public static Container getContainer() {
        return CONTAINER;
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
