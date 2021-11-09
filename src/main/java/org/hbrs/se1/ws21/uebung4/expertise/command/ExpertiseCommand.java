package org.hbrs.se1.ws21.uebung4.expertise.command;

import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.command.CommandParameters;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseContainer;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseView;
import org.hbrs.se1.ws21.uebung4.table.TablePrinterException;

import java.util.Collections;

/**
 * This {@link ConsoleCommand} is responsible for creating/removing/listing {@link Expertise}s
 */
public final class ExpertiseCommand extends ConsoleCommand {
    /**
     * The {@link ExpertiseService} which handle some useful operations in relation to {@link Expertise}s
     */
    private final ExpertiseService expertiseService;

    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final ExpertiseContainer expertiseContainer;

    public ExpertiseCommand(ExpertiseService expertiseService, ExpertiseContainer expertiseContainer) {
        super("expertise", "Verwaltet alle Expertisen", Collections.emptySet());
        this.expertiseService = expertiseService;
        this.expertiseContainer = expertiseContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "dump" -> {
                    try {
                        ExpertiseView.dump(this.expertiseContainer.getCurrentList());
                    } catch (TablePrinterException e) {
                        e.printStackTrace();
                    }
                }
                case "load" -> {
                    System.out.println("[BEGINN] Laden der Expertisen");
                    try {
                        this.expertiseContainer.load();
                        System.out.println("[ENDE] Expertisen erfolgreich geladen");
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                }
                case "store" -> {
                    System.out.println("[BEGINN] Speichern der Expertisen");
                    try {
                        this.expertiseContainer.store();
                        System.out.println("[ENDE] Expertisen erfolgreich gespeichert");
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                }
                default -> {
                    System.out.println("Verwendung: expertise load");
                    System.out.println("Verwendung: expertise store");
                }
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            final Integer id = parameters.getInteger("-id");
            if (id != null) {
                if (this.expertiseService.removeExpertiseById(id)) {
                    System.out.printf("Die Expertise mit der Id '%s' wurde entfernt%n", id);
                } else {
                    System.out.printf("Unter der Id '%s' existiert keine Expertise%n", id);
                }
                return;
            }
            final String title = parameters.getString("-title");
            if (title != null) {
                if (this.expertiseService.removeExpertiseByString(title)) {
                    System.out.printf("Die Expertise mit dem Titel '%s' wurde entfernt%n", title);
                } else {
                    System.out.printf("Unter dem Titel '%s' existiert keine Expertise%n", title);
                }
                return;
            }
            System.out.println("Es kann keine Expertise entfernt werden, wenn keine Schlüssel angegeben werden");
            System.out.println("Verwendung: expertise remove -id <Id>");
            System.out.println("Verwendung: expertise remove -title <Title>");
        } else if (args.length == 5 && args[0].equalsIgnoreCase("add")) {
            final Integer id = parameters.getInteger("-id");
            if (id == null) {
                System.err.println("Es muss eine Id mit '-id <Id>' übergeben werden");
                return;
            }
            final String title = parameters.getString("-title");
            if (title == null) {
                System.err.println("Es muss ein Titel mit '-title <Titel>' übergeben werden");
                return;
            }
            final Expertise expertise = new Expertise(id, title);
            try {
                this.expertiseService.addExpertise(expertise);
                System.out.printf("Expertise %s hinzugefügt%n", expertise);
            } catch (ContainerException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Verwendung expertise add -id <Id> -title <Title>");
            System.out.println("Verwendung expertise remove -id <Id>");
            System.out.println("Verwendung expertise remove -title <Title>");
            System.out.println("Verwendung expertise load");
            System.out.println("Verwendung expertise store");
        }
    }
}
