package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.RequiredArgsConstructor;
import org.hbrs.se1.ws21.uebung2.*;

import java.util.List;

/**
 * This {@link Member} implementation is resonsible for filling the current
 * {@link MemberContainer} instance with some default values
 */
@RequiredArgsConstructor
public final class Client implements Member {
    /**
     * The id of this client
     */
    private final Integer id;

    /**
     * This function fills the current {@link MemberContainer} instance
     * with some default values
     *
     * @throws ContainerException when an error occurs while handling some {@link Container} related functions
     * @see MemberContainer#getInstance()
     * @see MemberImpl
     */
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
