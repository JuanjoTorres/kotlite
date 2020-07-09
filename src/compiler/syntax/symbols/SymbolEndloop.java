package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolEndloop extends SymbolBase {

    // FORMA Endloop ::= .
    public SymbolEndloop() {
        super("Endloop", 0);
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
