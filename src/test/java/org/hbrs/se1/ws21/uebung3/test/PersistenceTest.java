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
        assertNotNull(this.container);
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
        assertNotNull(this.container);

        this.container.setStrategy(null);
        assertNull(this.container.getStrategy());

        final PersistenceException loadException = assertThrows(PersistenceException.class, this.container::load);
        assertEquals(ExceptionType.NoStrategyIsSet, loadException.getExceptionType());

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
        assertNotNull(this.container);
        assertNotNull(this.mongoStrategy);
        assertInstanceOf(PersistenceStrategyMongoDB.class, this.mongoStrategy);
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::openConnection);
        this.container.setStrategy(this.mongoStrategy);
        assertThrows(UnsupportedOperationException.class, this.container::store);
        assertThrows(UnsupportedOperationException.class, this.container::load);
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::closeConnection);
    }

    /**
     * This function uses the default file path which is
     * used when instantiating the {@link PersistenceStrategyStream} class
     */
    @Test
    @Order(4)
    public void useDefaultLocation() {
        assertNotNull(this.container);
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);
        this.container.setStrategy(this.streamStrategy);

        this.container.clear();
        assertEquals(0, this.container.size());

        final Member first = new MemberImpl(1);
        final Member second = new MemberImpl(2);
        final Member third = new MemberImpl(3);

        assertDoesNotThrow(() -> this.container.addMember(first));
        assertEquals(1, this.container.size());

        assertDoesNotThrow(() -> this.container.addMember(second));
        assertEquals(2, this.container.size());

        assertDoesNotThrow(() -> this.container.addMember(third));
        assertEquals(3, this.container.size());

        assertDoesNotThrow(() -> this.container.store());

        this.container.clear();
        assertEquals(0, this.container.size());

        assertDoesNotThrow(() -> this.container.load());

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
        assertNotNull(this.container);
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation(System.getProperty("user.dir"));

        final PersistenceException directoryException = assertThrows(PersistenceException.class, this.streamStrategy::openConnection);
        assertEquals(ExceptionType.OutputFileCanNotBeDirectory, directoryException.getExceptionType());

        this.container.setStrategy(this.streamStrategy);

        final PersistenceException fileNotExistingException = assertThrows(PersistenceException.class, this.container::store);
        assertEquals(ExceptionType.OutputFileNotExisting, fileNotExistingException.getExceptionType());

        final PersistenceException valueCouldNotBeFetchedException = assertThrows(PersistenceException.class, this.container::load);
        assertEquals(ExceptionType.ValueCouldNotBeFetched, valueCouldNotBeFetchedException.getExceptionType());

        final PersistenceException notImplementedException = assertThrows(PersistenceException.class, this.streamStrategy::closeConnection);
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
        assertNotNull(this.container);
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        this.container.clear();
        assertEquals(0, this.container.size());

        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation(null);

        final PersistenceException invalidPathException = assertThrows(PersistenceException.class, this.streamStrategy::openConnection);
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
        assertNotNull(this.container);
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        this.container.clear();
        assertEquals(0, this.container.size());

        final String userDir = System.getProperty("user.dir");
        assertNotNull(userDir);

        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        final Path path = assertDoesNotThrow(() -> Paths.get(userDir, "abcdefg.txt"));
        streamStrategy.setLocation(path.toString());

        this.container.setStrategy(this.streamStrategy);

        final Member first = new MemberImpl(1);
        final Member second = new MemberImpl(2);
        final Member third = new MemberImpl(3);

        assertDoesNotThrow(() -> this.container.addMember(first));
        assertEquals(1, this.container.size());

        assertDoesNotThrow(() -> this.container.addMember(second));
        assertEquals(2, this.container.size());

        assertDoesNotThrow(() -> this.container.addMember(third));
        assertEquals(3, this.container.size());

        assertDoesNotThrow(() -> this.container.store());

        this.container.clear();
        assertEquals(0, this.container.size());

        assertDoesNotThrow(this.container::load);

        assertEquals(3, this.container.size());
    }
}
