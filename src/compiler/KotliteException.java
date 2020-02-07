package compiler;

public class KotliteException {

    public static class DuplicatedIdentifierException extends Exception {
        public DuplicatedIdentifierException(String msg) {
            super(msg);
        }
    }

    public static class IdentifierNotExistException extends Exception {
        public IdentifierNotExistException(String msg) {
            super(msg);
        }
    }

    public static class IncompatibleSubtypeException extends Exception {
        public IncompatibleSubtypeException(String msg) {
            super(msg);
        }
    }

    public static class MissingArgumentException extends Exception {
        public MissingArgumentException(String msg) {
            super(msg);
        }
    }

    public static class TooManyArgumentException extends Exception {
        public TooManyArgumentException(String msg) {
            super(msg);
        }
    }

    public static class ErrorTypeArgumentException extends Exception {
        public ErrorTypeArgumentException(String msg) {
            super(msg);
        }
    }
}