package org.hbrs.se1.ws21.uebung3.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType.ConnectionNotAvailable;

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
                Files.createFile(path);
            }
            if (Files.isDirectory(path)) {
                throw new PersistenceException(ConnectionNotAvailable, "Given File can not be a directory");
            }
        } catch (IOException e) {
            throw new PersistenceException(ConnectionNotAvailable, e.getMessage());
        }
    }

    @Override
    public void closeConnection() throws PersistenceException {

    }

    @Override
    public void save(List<Member> member) throws PersistenceException {
        if (!(member instanceof Serializable)) {
            throw new PersistenceException(
                    ConnectionNotAvailable,
                    "Die Listen-Implementation muss das Interface Serializable implementieren"
            );
        }
        try (
                final FileOutputStream fout = new FileOutputStream(this.location);
                final ObjectOutputStream oout = new ObjectOutputStream(fout)
        ) {
            oout.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(ConnectionNotAvailable, e.getMessage());
        }
    }

    @Override
    public List<Member> load() throws PersistenceException {
        try (
                final FileInputStream fin = new FileInputStream(this.location);
                final ObjectInputStream oin = new ObjectInputStream(fin)
        ) {
            final Object rawMembers = oin.readObject();
            if (!(rawMembers instanceof List)) {
                return null;
            }
            return (List<Member>) rawMembers;
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(ConnectionNotAvailable, e.getMessage());
        }
    }
}
