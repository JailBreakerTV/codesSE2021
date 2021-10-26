package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType.*;

/**
 * This strategy class takes care of storing the content within a stream
 *
 * @param <Member> the type of data to be stored
 */
@NoArgsConstructor
public final class PersistenceStrategyStream<Member> implements PersistenceStrategy<Member> {

    @Getter
    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    @Setter
    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    /**
     * Instantiates this strategy with a given output location
     *
     * @param location which should be used as an output file
     */
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
                    throw new PersistenceException(OUTPUT_FILE_ALREADY_EXIST, e);
                } catch (Throwable t) {
                    throw new PersistenceException(OUTPUT_FILE_COULD_NOT_BE_CREATED, t);
                }
            }
            if (Files.isDirectory(path)) {
                throw new PersistenceException(OUTPUT_FILE_CAN_NOT_BE_DIRECTORY, "Given File can not be a directory");
            }
        } catch (InvalidPathException | NullPointerException e) {
            throw new PersistenceException(OUTPUT_FILE_PATH_IS_INVALID, e);
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {
        throw new PersistenceException(IMPLEMENTATION_NOT_AVAILABLE, "There is no implementation available yet");
    }

    @Override
    public void save(List<Member> member) throws PersistenceException {
        if (!(member instanceof final Serializable serializable)) {
            throw new PersistenceException(
                    VALUE_MUST_IMPLEMENT_SERIALIZABLE,
                    "The value to be saved must be an implementation of the Serializable interface"
            );
        }
        try {
            this.write(serializable);
        } catch (FileNotFoundException e) {
            throw new PersistenceException(OUTPUT_FILE_NOT_EXISTING, "There is no file which can be edited");
        } catch (IOException e) {
            e.printStackTrace();
            throw new PersistenceException(VALUE_COULD_NOT_BE_SAVED, e);
        }
    }

    @Override
    public List<Member> load() throws PersistenceException {
        try {
            return this.read();
        } catch (ClassCastException e) {
            throw new PersistenceException(VALUE_COULD_NOT_BE_CASTED, e);
        } catch (ClassNotFoundException e) {
            throw new PersistenceException(VALUE_CLASS_COULD_NOT_BE_FOUND, e);
        } catch (IOException e) {
            throw new PersistenceException(VALUE_COULD_NOT_BE_FETCHED, e);
        }
    }

    /**
     * This function writes a {@link Serializable} into a specific {@link OutputStream} implementation
     * which is responsible for saving the content into a file.
     *
     * @param serializable which should be saved into a file
     * @throws IOException if an I/O error occurs while writing stream header
     * @see FileOutputStream
     * @see ObjectOutputStream
     * @see ObjectOutputStream#writeObject(Object)
     */
    private void write(Serializable serializable) throws IOException {
        try (final FileOutputStream fileOut = new FileOutputStream(this.location);
             final ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(serializable);
        }
    }

    /**
     * This function reads the contents of the given file and
     * tries to cast this extracted value to the type of the given generic
     *
     * @param <T> generic, which specifies the type of the read data
     * @return The value which was extracted and casted
     * @throws IOException            if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     * @throws ClassCastException     if the extracted value is not an instance of the given generic type
     * @throws NullPointerException   if the extracted value is null
     * @see ObjectInputStream#readObject()
     */
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
