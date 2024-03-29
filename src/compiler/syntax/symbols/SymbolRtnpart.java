package compiler.syntax.symbols;

import compiler.syntax.tables.Subtype;

import java.io.PrintWriter;

public class SymbolRtnpart extends SymbolBase {

    private SymbolFactor factor;

    private Subtype subtype;

    // [FORMA] Rtnpart ::= .
    public SymbolRtnpart() {
        super("Rtnpart", 0);

        String functionLabel = generator.popFunctionLabel();

        this.subtype = Subtype.NONE;

        //Escibir C3D con operación RTN
        if (!functionLabel.equals("main"))
            generator.addThreeAddressCode("RTN", functionLabel, "", "");
    }

    // [FORMA] Rtnpart ::= RETURN Factor
    public SymbolRtnpart(SymbolFactor factor) {
        super("Rtnpart", 0);

        this.factor = factor;
        this.subtype = factor.getSubtype();

        String functionLabel = generator.popFunctionLabel();

        //Escibir C3D con operación RTN
        generator.addThreeAddressCode("RTN", functionLabel, "", factor.getVariable().getId());
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
