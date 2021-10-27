package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsoleReader {
    public static void read(Consumer<String> consumer) {
        new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);
            System.out.print(SprintPlanningMessages.PREFIX);
            while (scanner.hasNext()) {
                consumer.accept(scanner.nextLine());
                System.out.println();
                System.out.print(SprintPlanningMessages.PREFIX);
            }
        }).start();
    }
}
