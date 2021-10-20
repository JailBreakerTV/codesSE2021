package org.hbrs.se1.ws21.uebung3.test;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.Member;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersistenceTest {

    private Container container;
    private PersistenceStrategy<Member> mongoStrategy;
    private PersistenceStrategy<Member> streamStrategy;

    @BeforeEach
    public void init() {
        this.container = Container.getContainer();
        this.mongoStrategy = new PersistenceStrategyMongoDB<>();
        this.streamStrategy = new PersistenceStrategyStream<>();
    }

    /**
     * Diese Funktion testet die aktuelle {@link Container} Instanz direkt nach der Instanziierung
     */
    @Test
    @Order(1)
    public void initialContainerTest() {
        assertNotNull(this.container);
        assertEquals(0, this.container.size());
    }

    /**
     * Diese Funktion testet die Methoden {@link Container#load()} sowie {@link Container#store()}
     * auf Ihre Funktionalität, sofern keine {@link PersistenceStrategy} angegeben wurde
     */
    @Test
    @Order(2)
    public void noStrategyTest() {
        final PersistenceException loadException = assertThrows(PersistenceException.class, this.container::load);
        assertEquals(ExceptionType.NoStrategyIsSet, loadException.getExceptionType());

        final PersistenceException storeException = assertThrows(PersistenceException.class, this.container::store);
        assertEquals(ExceptionType.NoStrategyIsSet, storeException.getExceptionType());
    }

    /**
     * Diese Funktion testet die Funktionalitäten der Klasse {@link Container} unter der Bedingung, dass innerhalb
     * eines Containers für die {@link PersistenceStrategy} die Implementation {@link PersistenceStrategyMongoDB}
     * verwendet wird
     */
    @Test
    @Order(3)
    public void useMongoStrategyTest() {
        assertNotNull(this.mongoStrategy);
        assertInstanceOf(PersistenceStrategyMongoDB.class, this.mongoStrategy);
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::openConnection);
        this.container.setStrategy(this.mongoStrategy);
        assertThrows(UnsupportedOperationException.class, this.container::store);
        assertThrows(UnsupportedOperationException.class, this.container::load);
        assertThrows(UnsupportedOperationException.class, this.mongoStrategy::closeConnection);
    }

    @Test
    @Order(4)
    public void useDirectoryAsOutputLocationTest() {
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation("C:\\Users\\Artillero\\Documents\\IntelliJ\\Studium\\codesSE2021\\src\\main\\resources");

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

    @Test
    @Order(5)
    public void useWrongOutputLocationTest() {
        assertNotNull(this.streamStrategy);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

        final PersistenceStrategyStream<Member> streamStrategy = (PersistenceStrategyStream<Member>) this.streamStrategy;
        streamStrategy.setLocation("C:://Users//Artillero//Documents//IntelliJ//Studium//codesSE2021//src//main//resources");

        final PersistenceException invalidPathException = assertThrows(PersistenceException.class, this.streamStrategy::openConnection);
        assertEquals(ExceptionType.OutputFilePathIsInvalid, invalidPathException.getExceptionType());
    }

    @Test
    @Order(6)
    public void storeMembersAndDeleteFromContainerTest() {
        assertNotNull(this.container);
        assertInstanceOf(PersistenceStrategyStream.class, this.streamStrategy);

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

        this.container.getCurrentList().clear();
        assertEquals(0, this.container.size());

        assertDoesNotThrow(this.container::load);

        assertEquals(3, this.container.size());
    }
}
