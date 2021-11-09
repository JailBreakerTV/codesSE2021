package org.hbrs.se1.ws21.uebung4.test;

import org.hbrs.se1.ws21.uebung4.controller.CommandParameters;
import org.hbrs.se1.ws21.uebung4.controller.CommandRegistry;
import org.hbrs.se1.ws21.uebung4.controller.ConsoleCommand;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandRegistryTest {
    private ConsoleCommand testCommand;

    @BeforeEach
    public void init() {
        this.testCommand = new ConsoleCommand("test", "This command is for test purpose") {
            @Override
            public void execute(String[] args, CommandParameters parameters) {
                System.out.println("Command 'test' executed");
            }
        };
    }

    @Test
    @Order(0)
    public void testCommandRegistry() {
        assertDoesNotThrow(() -> CommandRegistry.register(this.testCommand));
    }

    @Test
    @Order(1)
    public void testCommandDiscovery() {
        assertDoesNotThrow(() -> CommandRegistry.discover(this.testCommand.getName()));
        assertEquals(this.testCommand, CommandRegistry.discover(this.testCommand.getName()));
    }

    @Test
    @Order(2)
    public void testCommandSnapshot() {
        assertFalse(CommandRegistry.snapshot().isEmpty());
        assertEquals(1, CommandRegistry.snapshot().size());
    }
}
