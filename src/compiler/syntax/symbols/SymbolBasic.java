package compiler.syntax.symbols;

import compiler.syntax.ParserSym;
import compiler.syntax.tables.Subtype;

import java.io.PrintWriter;

public class SymbolBasic extends SymbolBase {

    private Subtype tipo;

    public SymbolBasic(int symbol) {
        super("Basic", 0);

        if (symbol == ParserSym.INT)
            tipo = Subtype.INT;
        else if (symbol == ParserSym.STRING)
            tipo = Subtype.STRING;
        else if (symbol == ParserSym.BOOLEAN)
            tipo = Subtype.BOOLEAN;
        else if (symbol == ParserSym.NONE)
            tipo = Subtype.NONE;
    }

    public Subtype getSubtype() {
        return tipo;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");
    }

}
