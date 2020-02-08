/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.ParserSym;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolRelation extends SymbolBase {

    private SymbolExpr expr1;
    private SymbolOprel oprel;
    private SymbolExpr expr2;

    private Subtype subtype;

    // [FORMA] Relation ::= Expr Oprel Expr
    public SymbolRelation(SymbolExpr expr1, SymbolOprel oprel, SymbolExpr expr2) throws KotliteException.IncompatibleSubtypeException {
        super("Relation", 0);

        this.expr1 = expr1;
        this.oprel = oprel;
        this.expr2 = expr2;

        if (expr1.getSubtype() != expr2.getSubtype())
            throw new KotliteException.IncompatibleSubtypeException("Oprel requires same Subtype");

        switch (oprel.getRelationType()) {
            case ParserSym.LESS:
            case ParserSym.LESSEQU:
            case ParserSym.GREATEREQU:
            case ParserSym.GREATER:
                if (expr1.getSubtype() != Subtype.INT || expr2.getSubtype() != Subtype.INT)
                    throw new KotliteException.IncompatibleSubtypeException("Oprel Less/Greater requires INT Subtype");
        }

        this.subtype = Subtype.BOOLEAN;
    }

    // [FORMA] Relation ::= Expr
    public SymbolRelation(SymbolExpr expr1) {
        super("Relation", 0);

        this.expr1 = expr1;
        subtype = expr1.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (oprel != null) {
            out.print(index + "->" + expr1.getIndex() + "\n");
            out.print(index + "->" + oprel.getIndex() + "\n");
            out.print(index + "->" + expr2.getIndex() + "\n");

            expr1.toDot(out);
            oprel.toDot(out);
            expr2.toDot(out);
        } else {
            out.print(index + "->" + expr1.getIndex() + "\n");

            expr1.toDot(out);
        }
    }

}
