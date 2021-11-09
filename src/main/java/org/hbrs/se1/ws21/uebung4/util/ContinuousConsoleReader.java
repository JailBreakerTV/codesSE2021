package org.hbrs.se1.ws21.uebung4.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.SprintPlanningMessages;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * This class is responsible for continously reading the system inputstream and providing the input-value
 *
 * @see System#in
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContinuousConsoleReader {

    /**
     * This function starts a new thread which is responsible for delivering the console input
     *
     * @param consumer which accepts the input-value
     */
    public static void start(Consumer<String> consumer) {
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            System.out.print(SprintPlanningMessages.PROMPT_PREFIX);
            while (scanner.hasNext()) {
                consumer.accept(scanner.nextLine());
                System.out.print(SprintPlanningMessages.PROMPT_PREFIX);
            }
        }).start();
    }
}
