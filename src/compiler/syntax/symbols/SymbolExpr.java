/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolExpr extends SymbolBase {

    private SymbolExpr expr;
    private SymbolAdd add;
    private SymbolTerm term;

    // [FORMA] Expr ::= Expr Add Term
    public SymbolExpr(SymbolExpr expr, SymbolAdd add, SymbolTerm term) {

        super("Expr", 0);
        this.expr = expr;
        this.add = add;
        this.term = term;
    }

    // [FORMA] Expr ::= Term
    public SymbolExpr(SymbolTerm term) {

        super("Expr",0);
        this.term = term;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
