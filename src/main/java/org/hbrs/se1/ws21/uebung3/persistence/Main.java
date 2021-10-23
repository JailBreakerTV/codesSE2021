package org.hbrs.se1.ws21.uebung3.persistence;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung2.Member;
import org.hbrs.se1.ws21.uebung2.MemberContainer;

public class Main {
    public static void main(String[] args) {
        final PersistenceStrategy<Member> strategy = new PersistenceStrategyStream<>();
        MemberContainer.getInstance().setStrategy(strategy);
        final Client client = new Client(22);
        try {
            client.fill();
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}
