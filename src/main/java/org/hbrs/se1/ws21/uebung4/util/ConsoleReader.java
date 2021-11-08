package org.hbrs.se1.ws21.uebung4.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.SprintPlanningMessages;

import java.util.Scanner;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleReader {
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
