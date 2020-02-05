package compiler;

public class KotliteException {

    public static class SymbolTableException extends Exception {
        public SymbolTableException(String msg) {
            super(msg);
        }
    }

}
