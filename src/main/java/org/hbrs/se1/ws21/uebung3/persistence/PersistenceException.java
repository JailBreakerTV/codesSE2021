package org.hbrs.se1.ws21.uebung3.persistence;

/**
 * This class represents an exception which can be thrown in the context of persistence.
 */
public class PersistenceException extends Exception {

    /**
     * The type of the exception
     *
     * @see ExceptionType#values()
     */
    private final ExceptionType exceptionType;

    /**
     * Instantiates this exception with a given message and exceptionType
     *
     * @param exceptionType of this exception
     * @param message       which should be printed
     */
    public PersistenceException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    /**
     * Instantiates this exception with a given {@link Throwable} and exceptionType
     *
     * @param exceptionType of this exception
     * @param throwable     which was thrown and should be transformed
     */
    public PersistenceException(ExceptionType exceptionType, Throwable throwable) {
        super(throwable.getMessage());
        this.exceptionType = exceptionType;
    }

    /**
     * This function will return the current exceptionType value
     *
     * @return ExceptionType of this exception
     */
    public ExceptionType getExceptionType() {
        return this.exceptionType;
    }

    /**
     * ExceptionTypes for declaring the type of an exception.
     * Please feel free to extend this list!
     * <p>
     * Example: If an internal Exception of type java.lang.UnsupportedOperationException is thrown,
     * then this exception must be caught and transformed to an object of this exception-type, consisting
     * of Type 'ImplementationNotAvailable'. Re-throw the new exception e.g. to a client
     */
    public enum ExceptionType {
        ImplementationNotAvailable,
        ConnectionNotAvailable,
        NoStrategyIsSet,

        ValueMustImplementSerializable,
        ValueCouldNotBeSaved,
        ValueClassCouldNotBeFound,
        ValueCouldNotBeCasted,
        ValueCouldNotBeFetched,

        OutputFileAlreadyExist,
        OutputFileNotExisting,
        OutputFileCanNotBeDirectory,
        OutputFileCouldNotBeCreated,
        OutputFilePathIsInvalid
    }
}
