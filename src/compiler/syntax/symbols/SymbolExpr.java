/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolExpr extends SymbolBase {

    private SymbolExpr expr;
    private SymbolAdd add;
    private SymbolTerm term;

    private Subtype subtype;

    // [FORMA] Expr ::= Expr Add Term
    public SymbolExpr(SymbolExpr expr, SymbolAdd add, SymbolTerm term) throws KotliteException.IncompatibleSubtypeException {
        super("Expr", 0);
        this.expr = expr;
        this.add = add;
        this.term = term;

        if (expr.getSubtype() != Subtype.INT || term.getSubtype() != Subtype.INT)
            throw new KotliteException.IncompatibleSubtypeException("Add/Sub requires INT Subtype");
    }

    // [FORMA] Expr ::= Term
    public SymbolExpr(SymbolTerm term) {

        super("Expr",0);
        this.term = term;

        this.subtype = term.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
