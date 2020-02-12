package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolAdd extends SymbolBase {

    private int symbol;

    public SymbolAdd(int symbol) {
        super("Add", 0);
        this.symbol = symbol;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");
    }

}
