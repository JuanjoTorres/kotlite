package compiler.syntax.symbols;

import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolRtnpart extends SymbolBase {

    private SymbolFactor factor;

    private Subtype subtype;

    // [FORMA] Rtnpart ::= .
    public SymbolRtnpart() {
        super("Rtnpart", 0);
    }

    // [FORMA] Rtnpart ::= RETURN Factor
    public SymbolRtnpart(SymbolFactor factor) {
        super("Rtnpart", 0);

        this.factor = factor;
        this.subtype = factor.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (factor != null) {
            out.print(index + "->" + factor.getIndex() + "\n");
            factor.toDot(out);
        }
    }

}
