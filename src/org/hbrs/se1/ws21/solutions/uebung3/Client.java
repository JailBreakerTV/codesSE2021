package org.hbrs.se1.ws21.solutions.uebung3;

import org.hbrs.se1.ws21.solutions.uebung3.persistence.*;

import java.util.List;

public class Client {

    public void startClient() {
        Container container = Container.getInstance();

        try {
            container.addMember(new MemberKonkret(2));
        } catch (ContainerException e) {
            e.printStackTrace();
        }

        try {
            container.store();
        } catch (PersistenceException e) {
           e.printStackTrace();
        }

        List<Member> liste = container.getCurrentList();

        MemberView view = new MemberView();
        view.dump( liste );

        try {
            container.load();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        liste = container.getCurrentList();

        view = new MemberView();
        view.dump( liste );

    }


}
