package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.extern.java.Log;
import org.hbrs.se1.ws21.uebung2.Member;

import java.util.List;

/**
 * This class will accept a list of {@link Member} implementations and print them
 */
@Log
public class MemberView {
    /**
     * This function will print all the members to the console
     *
     * @param members which should be printed to the console
     */
    public void dump(List<Member> members) {
        if (members.isEmpty()) {
            log.warning("There are no members which can be printed");
            return;
        }
        log.info("Start printing all members");
        members.forEach(System.out::println);
        log.info("Stop printing all members");
    }
}
