package compiler.syntax.symbols;

import compiler.syntax.ParserSym;

import java.io.PrintWriter;

public class SymbolJoin extends SymbolBase {

    private int symbol;

    public SymbolJoin(int symbol) {
        super("Join", 0);

        this.symbol = symbol;
    }

    @Override
    public void toDot(PrintWriter out) {
        if (symbol == ParserSym.AND)
            out.print(index + " [label=\"" + name + " AND\"];\n");
        else
            out.print(index + " [label=\"" + name + " OR\"];\n");
    }

    public int getSymbol() {
        return symbol;
    }
}
