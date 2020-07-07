package compiler.syntax.symbols;

import compiler.syntax.ParserSym;
import compiler.syntax.tables.Type;

import java.io.PrintWriter;

public class SymbolType extends SymbolBase {

    private Type type;

    public SymbolType(int type) {
        super("Type", 0);

        if (type == ParserSym.VAR)
            this.type = Type.VAR;
        else if (type == ParserSym.VAL)
            this.type = Type.CONST;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");
    }

}
