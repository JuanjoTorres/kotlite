/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolRelation extends SymbolBase {

    private SymbolExpr expr1;
    private SymbolOprel oprel;
    private SymbolExpr expr2;

    // [FORMA] Relation ::= Expr Oprel Expr
    public SymbolRelation(SymbolExpr expr1, SymbolOprel oprel, SymbolExpr expr2)
    {
        super("Relation", 0);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    // [FORMA] Relation ::= Expr
    public SymbolRelation(SymbolExpr expr1) {
        super("Relation", 0);
        this.expr1 = expr1;
    }


    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
