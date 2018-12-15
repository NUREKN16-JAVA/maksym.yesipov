package main.java.ua.nure.kn.yesipov.database;

public class DatabaseException extends Exception {

    public DatabaseException() {
    }

    public DatabaseException(String arg0) {
        super(arg0);
    }

    public DatabaseException(Throwable arg0) {
        super(arg0);
    }

    public DatabaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DatabaseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
}
