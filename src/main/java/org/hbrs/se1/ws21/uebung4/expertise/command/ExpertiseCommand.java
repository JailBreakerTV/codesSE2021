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
        super("expertise", "This command can save new expertises, delete existing ones or load them from or into the persistence store");
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
                    System.out.println("The expertises are loaded from the persistence store");
                    try {
                        this.expertiseContainer.load();
                        System.out.println("The expertises were successfully loaded from the persistence store");
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                }
                case "store" -> {
                    System.out.println("The expertises will now be persisted");
                    try {
                        this.expertiseContainer.store();
                        System.out.println("The expertises were successfully persisted");
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                }
                default -> {
                    System.out.println("Please use 'expertise load' to load the expertise from the persistence store");
                    System.out.println("Please use 'expertise store' to persist the expertises");
                }
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            final Integer id = parameters.getInteger("-id");
            if (id != null) {
                if (this.expertiseService.removeExpertiseById(id)) {
                    System.out.printf("The expertise with the id '%s' was successfully removed%n", id);
                } else {
                    System.out.printf("No expertise exists for the specified Id '%s'.%n", id);
                }
                return;
            }
            final String title = parameters.getString("-title");
            if (title != null) {
                if (this.expertiseService.removeExpertiseByString(title)) {
                    System.out.printf("The expertise with the title '%s' was successfully removed%n", id);
                } else {
                    System.out.printf("No expertise exists for the specified title '%s'.%n", id);
                }
                return;
            }
            System.out.println("No expertise can be removed if no keys are specified");
            System.out.println("Please use 'expertise remove -id <Id>' to delete an expertise by its id");
            System.out.println("Please use 'expertise remove -title <Title>' to delete an expertise by its title");
        } else if (args.length == 5 && args[0].equalsIgnoreCase("add")) {
            final Integer id = parameters.getInteger("-id");
            if (id == null) {
                System.err.println("An Id for new expertises must be specified");
                System.out.println("Please add a unique id for this expertise with the argument '-id <Id>'.");
                return;
            }
            final String title = parameters.getString("-title");
            if (title == null) {
                System.err.println("A Title for new expertises must be specified");
                System.out.println("Please add a title for this expertise with the argument '-title <Title>'.");
                return;
            }
            final Expertise expertise = new Expertise(id, title);
            try {
                this.expertiseService.addExpertise(expertise);
                System.out.printf("The expertise '%s' was successfully added%n", expertise);
            } catch (ContainerException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Use 'expertise add -id <Id> -title <title>' to add an expertise");
            System.out.println("Use 'expertise remove -id <Id>' to remove an expertise by its id");
            System.out.println("Use 'expertise remove -title <Title>' to remove an expertise by its title");
            System.out.println("Use 'expertise load' to load the expertise from the persistence store");
            System.out.println("Use 'expertise store' to persist the expertises");
        }
    }
}
