package integration;

public class SchoolDBException extends Exception {
    public SchoolDBException(String reason){
        super(reason);
    }

    public SchoolDBException(String reason, Throwable rootCause){
        super(reason, rootCause);
    }
}
