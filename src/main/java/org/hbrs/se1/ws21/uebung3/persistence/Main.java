package org.hbrs.se1.ws21.uebung3.persistence;

import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung2.Member;
import org.hbrs.se1.ws21.uebung2.MemberContainer;

/**
 * This class is responsible for starting this application
 */
public class Main {
    /**
     * This function is the first entry-point of this application
     *
     * @param args The command line parameters.
     * @see Client
     */
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
