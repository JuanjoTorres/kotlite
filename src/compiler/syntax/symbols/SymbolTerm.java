package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolTerm extends SymbolBase {

    private SymbolTerm term;
    private SymbolMult mult;
    private SymbolUnary unary;

    private Subtype subtype;

    // Term ::= Term Mult Unary
    public SymbolTerm(SymbolTerm term, SymbolMult mult, SymbolUnary unary, int line, int column) {
        super("Term", 0);

        this.term = term;
        this.mult = mult;
        this.unary = unary;

        if (term.getSubtype() != Subtype.INT || unary.getSubtype() != Subtype.INT)
            Output.writeError("Error in position: " + line + ":" + column + " - Mult/Div requires INT Subtype");

        //Resultado de multiplicacion es INT
        this.subtype = Subtype.INT;
    }

    // Term ::= Unary
    public SymbolTerm(SymbolUnary unary) {
        super("Term", 0);

        this.unary = unary;
        this.subtype = unary.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (term != null)
            out.print(index + "->" + term.getIndex() + "\n");
        if (mult != null)
            out.print(index + "->" + mult.getIndex() + "\n");
        out.print(index + "->" + unary.getIndex() + "\n");

        if (term != null)
            term.toDot(out);
        if (mult != null)
            mult.toDot(out);
        unary.toDot(out);
    }

}
