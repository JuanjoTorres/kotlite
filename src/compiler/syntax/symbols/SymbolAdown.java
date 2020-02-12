package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolAdown extends SymbolBase {

    // FORMA Adown ::= .
    public SymbolAdown() {
        super("Adown", 0);
        symbolTable.endBlock();
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
