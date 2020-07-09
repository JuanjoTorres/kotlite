package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolEndif extends SymbolBase {

    // FORMA Endif ::= .
    public SymbolEndif() {
        super("Endif", 0);
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
