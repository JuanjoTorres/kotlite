package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolMult extends SymbolBase {

    private int symbol;

    public SymbolMult(int symbol) {
        super("Mult", 0);

        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");
    }

}
