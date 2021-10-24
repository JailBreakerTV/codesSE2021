package org.hbrs.se1.ws21.uebung2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException.ExceptionType;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class provides the basic structure for storing and managing generic instances
 *
 * @param <E> represents the type which should be managed within the container
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class Container<E extends IdAware> {

    /**
     * This {@link List} contains all object instances this container manages
     */
    private final List<E> members = new ArrayList<>();

    /**
     * This lock makes the operations on the list thread-safe
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * This {@link PersistenceStrategy} takes care of
     * persisting the object instances stored in memory
     */
    @Getter
    @Setter
    private PersistenceStrategy<E> strategy;

    /**
     * This function is supposed to persist the object instances located in the memory
     *
     * @throws PersistenceException if the {@link Container#strategy} is null
     *                              or the {@link PersistenceStrategy#save(List)} function throws an error
     */
    public void store() throws PersistenceException {
        if (this.strategy == null) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "There is no strategy");
        }
        this.strategy.save(this.getCurrentList());
    }

    /**
     * This function will load all the stored object instances by using the strategy and store them in the memory
     *
     * @throws PersistenceException if the {@link Container#strategy} is null
     *                              or the {@link PersistenceStrategy#load()} function throws an error
     */
    public void load() throws PersistenceException {
        if (this.strategy == null) {
            throw new PersistenceException(ExceptionType.NoStrategyIsSet, "There is no strategy");
        }
        final List<E> loadedMembers = this.strategy.load();
        if (loadedMembers == null) {
            throw new NullPointerException("Can not load Members (loadedMembers = null)");
        }
        this.lock.writeLock().lock();
        try {
            this.members.removeIf(member -> loadedMembers.stream().anyMatch(member::equals));
            this.members.addAll(loadedMembers);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * This function adds a new entry to the local list
     *
     * @param member which should be added to the local list
     * @throws ContainerException if the entry with the given {@link IdAware#getId()} already exists
     */
    public void addMember(E member) throws ContainerException {
        if (this.hasMember(member.getId())) {
            throw new ContainerException(member);
        }
        this.lock.writeLock().lock();
        try {
            this.members.add(member);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * This function provides information about whether an entry with
     * the specified {@link IdAware#getId()} already exists
     *
     * @param id which should be searched for
     * @return boolean either true if an entry exists or false if this is not the case
     */
    public boolean hasMember(Integer id) {
        this.lock.readLock().lock();
        try {
            return this.members.stream().anyMatch(member -> member.getId().equals(id));
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * This function removes an entry from the local list
     *
     * @param id whose entry should be deleted
     * @return boolean either true if the entry was deleted or false if this not the case
     */
    public boolean deleteMember(Integer id) {
        this.lock.writeLock().lock();
        try {
            return this.members.removeIf(member -> member.getId().equals(id));
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * This function returns a current snapshot of the local list
     *
     * @return A {@link List} which is a snapshot of the actual list
     * @see Collections#unmodifiableList(List)
     */
    public List<E> getCurrentList() {
        this.lock.readLock().lock();
        try {
            return Collections.unmodifiableList(this.members);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * This function searches for an entry with the given id and returns that entry packed in an optional
     *
     * @param id which should be searched for
     * @return Optional if there is an entry with this Id or {@link Optional#empty()} if this is not the case
     */
    public Optional<E> get(Integer id) {
        this.lock.readLock().lock();
        try {
            return this.members.stream().filter(e -> e.getId().equals(id)).findFirst();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * This function returns the current length of the local list
     *
     * @return int representing the local lists current length
     */
    public int size() {
        this.lock.readLock().lock();
        try {
            return this.members.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * This function will clear the list of entries
     */
    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.members.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
