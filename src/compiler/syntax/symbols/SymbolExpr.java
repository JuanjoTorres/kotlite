package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolExpr extends SymbolBase {

    private SymbolExpr expr;
    private SymbolAdd add;
    private SymbolTerm term;

    private Subtype subtype;

    // [FORMA] Expr ::= Expr Add Term
    public SymbolExpr(SymbolExpr expr, SymbolAdd add, SymbolTerm term, int line, int column) {
        super("Expr", 0);

        this.expr = expr;
        this.add = add;
        this.term = term;

        if (expr.getSubtype() != Subtype.INT || term.getSubtype() != Subtype.INT)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - Add/Sub requires INT Subtype");

        //Resultado de + o - es INT
        subtype = Subtype.INT;
    }

    // [FORMA] Expr ::= Term
    public SymbolExpr(SymbolTerm term) {
        super("Expr", 0);

        this.term = term;
        this.subtype = term.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (expr != null)
            out.print(index + "->" + expr.getIndex() + "\n");
        if (add != null)
            out.print(index + "->" + add.getIndex() + "\n");
        out.print(index + "->" + term.getIndex() + "\n");

        if (expr != null)
            expr.toDot(out);
        if (add != null)
            add.toDot(out);
        term.toDot(out);
    }

}
