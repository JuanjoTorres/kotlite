package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolElsepart extends SymbolBase {

    private SymbolDecls decls;
    private SymbolStatments statments;

    // [FORMA] Elsepart ::= .
    public SymbolElsepart() {
        super("Elsepart", 0);
    }

    // [FORMA] Elsepart ::= ELSE LBRACKET Decls Statments RBRACKET
    public SymbolElsepart(SymbolDecls decls, SymbolStatments statments) {
        super("Elsepart", 0);

        this.decls = decls;
        this.statments = statments;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (decls != null)
            out.print(index + "->" + decls.getIndex() + "\n");
        if (statments != null)
            out.print(index + "->" + statments.getIndex() + "\n");

        if (decls != null)
            decls.toDot(out);
        if (statments != null)
            statments.toDot(out);
    }

}
