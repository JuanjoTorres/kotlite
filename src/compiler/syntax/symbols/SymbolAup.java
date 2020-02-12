package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolAup extends SymbolBase {

    // FORMA Aup ::= .
    public SymbolAup() {
        super("Aup", 0);
        symbolTable.startBlock();
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
