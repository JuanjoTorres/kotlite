package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolCond extends SymbolBase {

    // FORMA Cond ::= .
    public SymbolCond() {
        super("Cond", 0);
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
