package org.hbrs.se1.ws21.uebung2.test;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung2.Member;
import org.hbrs.se1.ws21.uebung2.MemberImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {
    private Container container;
    private Member first, second, third;

    @BeforeEach
    public void loadMembers() {
        this.container = Container.getContainer();
        this.first = new MemberImpl(1);
        this.second = new MemberImpl(2);
        this.third = new MemberImpl(3);
    }

    @Test
    public void containerTest() throws ContainerException {
        this.container.addMember(this.first);
        this.container.addMember(this.second);
        this.container.addMember(this.third);
        assertEquals(3, this.container.size());

        this.container.deleteMember(this.first.getID());
        assertEquals(2, this.container.size());

        this.container.deleteMember(this.second.getID());
        assertEquals(1, this.container.size());

        this.container.addMember(this.first);
        assertEquals(2, this.container.size());

        assertThrows(ContainerException.class, () -> this.container.addMember(this.first));

        assertFalse(this.container.deleteMember(this.second.getID()));

        assertTrue(this.container.deleteMember(this.first.getID()));
    }

}
