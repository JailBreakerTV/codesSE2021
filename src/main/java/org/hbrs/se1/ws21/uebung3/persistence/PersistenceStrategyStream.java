package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType.*;

@NoArgsConstructor
public class PersistenceStrategyStream<Member> implements PersistenceStrategy<Member> {

    @Getter
    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    @Setter
    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    public PersistenceStrategyStream(String location) {
        this.location = location;
    }

    @Override
    public void openConnection() throws PersistenceException {
        try {
            final Path path = Paths.get(this.location);
            if (Files.notExists(path)) {
                try {
                    Files.createFile(path);
                } catch (FileAlreadyExistsException e) {
                    throw new PersistenceException(OutputFileAlreadyExist, e.getMessage());
                } catch (Throwable t) {
                    throw new PersistenceException(OutputFileCouldNotBeCreated, t.getMessage());
                }
            }
            if (Files.isDirectory(path)) {
                throw new PersistenceException(OutputFileCanNotBeDirectory, "Given File can not be a directory");
            }
        } catch (InvalidPathException e) {
            throw new PersistenceException(OutputFilePathIsInvalid, e.getMessage());
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {
        throw new PersistenceException(ImplementationNotAvailable, "There is no implementation available yet");
    }

    @Override
    public void save(List<Member> member) throws PersistenceException {
        if (!(member instanceof Serializable)) {
            throw new PersistenceException(
                    ValueMustImplementSerializable,
                    "The value to be saved must be an implementation of the Serializable interface"
            );
        }
        final Serializable serializable = (Serializable) member;
        try {
            this.write(serializable);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(OutputFileNotExisting, "There is no file which can be edited");
        } catch (IOException e) {
            e.printStackTrace();
            throw new PersistenceException(ValueCouldNotBeSaved, e.getMessage());
        }
    }

    @Override
    public List<Member> load() throws PersistenceException {
        try {
            return this.read();
        } catch (ClassCastException e) {
            throw new PersistenceException(ValueCouldNotBeCasted, e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new PersistenceException(ValueClassCouldNotBeFound, e.getMessage());
        } catch (IOException e) {
            throw new PersistenceException(ValueCouldNotBeFetched, e.getMessage());
        }
    }

    private void write(Serializable serializable) throws IOException {
        try (final FileOutputStream fileOut = new FileOutputStream(this.location);
             final ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(serializable);
        }
    }

    private <T> T read() throws IOException, ClassNotFoundException, ClassCastException, NullPointerException {
        try (final FileInputStream fileIn = new FileInputStream(this.location);
             final ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            final Object raw = objectIn.readObject();
            if (raw == null) {
                throw new NullPointerException("Raw value from ObjectInputStream is null");
            }
            return (T) raw;
        }
    }
}
