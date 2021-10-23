package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.RequiredArgsConstructor;
import org.hbrs.se1.ws21.uebung2.*;

import java.util.List;

@RequiredArgsConstructor
public final class Client implements Member {
    private final Integer id;

    public void fill() throws ContainerException {
        final Member first = new MemberImpl(1);
        final Member second = new MemberImpl(2);
        final Member third = new MemberImpl(3);
        final Container<Member> container = MemberContainer.getInstance();
        container.addMember(first);
        container.addMember(second);
        container.addMember(third);
        final List<Member> currentMembers = container.getCurrentList();
        final MemberView view = new MemberView();
        view.dump(currentMembers);
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
