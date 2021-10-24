package org.hbrs.se1.ws21.uebung3.test;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.Member;
import org.hbrs.se1.ws21.uebung2.MemberContainer;
import org.hbrs.se1.ws21.uebung2.MemberImpl;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class tests all functions of the container and persistence classes
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersistenceTest {

    /**
     * The container used for handling {@link Member} instances
     */
    private Container<Member> container;

    /**
     * The {@link PersistenceStrategy} for mongodb
     */
    private PersistenceStrategy<Member> mongoStrategy;

    /**
     * The {@link PersistenceStrategy} for basic streams
     */
    private PersistenceStrategy<Member> streamStrategy;

    /**
     * This function provides the most necessary tools for testing the new classes
     */
    @BeforeEach
    public void init() {
        // Specify the container type
        this.container = MemberContainer.getInstance();
        this.mongoStrategy = new PersistenceStrategyMongoDB<>();
        this.streamStrategy = new PersistenceStrategyStream<>();
    }

    /**
     * This function tests the container class immediately after its instantiation
     */
    @Test
    @Order(1)
    public void initialContainerTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if the container is empty after its instantiation
        assertEquals(0, this.container.size());
    }

    /**
     * This function tests the methods {@link Container#store()}
     * and {@link Container#load()} of the class {@link Container} with the condition that
     * no {@link PersistenceStrategy} has been set yet
     */
    @Test
    @Order(2)
    public void noStrategyTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Set the strategy to null
        this.container.setStrategy(null);
        assertNull(this.container.getStrategy());

        // Call the containers load function
        final PersistenceException loadException = assertThrows(PersistenceException.class, this.container::load);
        assertEquals(ExceptionType.NoStrategyIsSet, loadException.getExceptionType());

        // Call the containers store function
        final PersistenceException storeException = assertThrows(PersistenceException.class, this.container::store);
        assertEquals(ExceptionType.NoStrategyIsSet, storeException.getExceptionType());
    }

    /**
     * This function tests the functionality of the {@link Container} class with
     * the precondition that the {@link PersistenceStrategyMongoDB} implementation
     * is used for the {@link PersistenceStrategy}
     */
    @Test
    @Order(3)
    public void useMongoStrategyTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if there exists a mongoStrategy instance
        assertNotNull(this.mongoStrategy);

        // Check if the mongoStrategy instance instanceof the correct type
        assertInstanceOf(PersistenceStrategyMongoDB.class, this.mongoStrategy);

        // Open the mongoStrategy connection
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::openConnection);
        this.container.setStrategy(this.mongoStrategy);

        // Call the containers store function
        assertThrows(UnsupportedOperationException.class, this.container::store);

        // Call the containers store load
        assertThrows(UnsupportedOperationException.class, this.container::load);

        // Close the mongoStrategy connection
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::closeConnection);
    }

    /**
     * This function uses the default file path which is
     * used when instantiating the {@link PersistenceStrategyStream} class
     */
    @Test
    @Order(4)
    public void useDefaultLocation() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if there exists a streamStrategy instance
        assertNotNull(this.streamStrategy);

        // Check if the streamStrategy instance instanceof the correct type
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        // Use the stream strategy as the containers strategy
        this.container.setStrategy(this.streamStrategy);

        // Clear the container to prevent duplicate entries from the test functions before
        this.container.clear();
        assertEquals(0, this.container.size());

        // Add the first member
        final Member first = new MemberImpl(1);
        assertDoesNotThrow(() -> this.container.addMember(first));
        assertEquals(1, this.container.size());

        // Add the second member
        final Member second = new MemberImpl(2);
        assertDoesNotThrow(() -> this.container.addMember(second));
        assertEquals(2, this.container.size());

        // Add the third member
        final Member third = new MemberImpl(3);
        assertDoesNotThrow(() -> this.container.addMember(third));
        assertEquals(3, this.container.size());

        // Call the containers store function
        assertDoesNotThrow(() -> this.container.store());

        // Clear the container to be able to load the members from the harddrive
        this.container.clear();
        assertEquals(0, this.container.size());

        // Call the containers load function to fill the container with the values astored before
        assertDoesNotThrow(() -> this.container.load());

        // Check the containers size after loading all members from the harddrive
        assertEquals(3, this.container.size());
    }

    /**
     * This function tests the functionality of the {@link Container}
     * class with the condition that a directory is specified
     * as the file path for the {@link PersistenceStrategyStream}
     */
    @Test
    @Order(5)
    public void useDirectoryAsOutputLocationTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if there exists a streamStrategy instance
        assertNotNull(this.streamStrategy);

        // Check if the streamStrategy instance instanceof the correct type
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        // Set the location to the current working directory
        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation(System.getProperty("user.dir"));

        // Open the connection after setting the location to the current working directory
        final PersistenceException directoryException = assertThrows(
                PersistenceException.class,
                this.streamStrategy::openConnection
        );
        assertEquals(ExceptionType.OutputFileCanNotBeDirectory, directoryException.getExceptionType());

        // Update the containers strategy
        this.container.setStrategy(this.streamStrategy);

        // Store the containers members
        final PersistenceException fileNotExistingException = assertThrows(
                PersistenceException.class,
                this.container::store
        );
        assertEquals(ExceptionType.OutputFileNotExisting, fileNotExistingException.getExceptionType());

        // Load all members into the container
        final PersistenceException valueCouldNotBeFetchedException = assertThrows(
                PersistenceException.class,
                this.container::load
        );
        assertEquals(ExceptionType.ValueCouldNotBeFetched, valueCouldNotBeFetchedException.getExceptionType());

        // Close the streamStrategy
        final PersistenceException notImplementedException = assertThrows(
                PersistenceException.class,
                this.streamStrategy::closeConnection
        );
        assertEquals(ExceptionType.ImplementationNotAvailable, notImplementedException.getExceptionType());
    }

    /**
     * This function tests the functionality of the
     * {@link PersistenceStrategyStream} class when
     * the file path is set to null
     *
     * @see Container
     */
    @Test
    @Order(6)
    public void useWrongOutputLocationTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if there exists a streamStrategy instance
        assertNotNull(this.streamStrategy);

        // Check if the streamStrategy instance instanceof the correct type
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        // Clear the container to prevent duplicate entries
        this.container.clear();
        assertEquals(0, this.container.size());

        // Use null as output file location
        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation(null);

        // Open the connection after setting the output file location to null
        final PersistenceException invalidPathException = assertThrows(
                PersistenceException.class,
                this.streamStrategy::openConnection
        );
        assertEquals(ExceptionType.OutputFilePathIsInvalid, invalidPathException.getExceptionType());
    }

    /**
     * This function fills the {@link Container} class, stores
     * the values within the {@link Container} persistently and
     * loads them again
     *
     * @see PersistenceStrategyStream
     */
    @Test
    @Order(7)
    public void storeMembersAndDeleteFromContainerTest() {
        // Check if there exists a container instance
        assertNotNull(this.container);

        // Check if there exists a streamStrategy instance
        assertNotNull(this.streamStrategy);

        // Check if the streamStrategy instance instanceof the correct type
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        // Clear the container to prevent duplicate entries
        this.container.clear();
        assertEquals(0, this.container.size());

        // Get the current working directory
        final String userDir = System.getProperty("user.dir");
        assertNotNull(userDir);

        // Prepare the streamStrategy and set its output file location to a file which lies in the working directory
        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        final Path path = assertDoesNotThrow(() -> Paths.get(userDir, "abcdefg.txt"));
        streamStrategy.setLocation(path.toString());

        // Update the container persistence strategy
        this.container.setStrategy(this.streamStrategy);

        // Add the first member
        final Member first = new MemberImpl(1);
        assertDoesNotThrow(() -> this.container.addMember(first));
        assertEquals(1, this.container.size());

        // Add the second member
        final Member second = new MemberImpl(2);
        assertDoesNotThrow(() -> this.container.addMember(second));
        assertEquals(2, this.container.size());

        // Add the third member
        final Member third = new MemberImpl(3);
        assertDoesNotThrow(() -> this.container.addMember(third));
        assertEquals(3, this.container.size());

        // Store the previously added members to the harddrive
        assertDoesNotThrow(() -> this.container.store());

        // Clear the container
        this.container.clear();
        assertEquals(0, this.container.size());

        // Load the previously stored members from the harddrive
        assertDoesNotThrow(this.container::load);

        // Check if the container has loaded all three members
        assertEquals(3, this.container.size());
    }
}
