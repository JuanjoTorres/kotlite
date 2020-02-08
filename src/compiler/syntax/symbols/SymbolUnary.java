/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolUnary extends SymbolBase {

    private SymbolUnary unary;
    private SymbolFactor factor;

    private Subtype subtype;

    private boolean negado;

    // [FORMA] Unary ::= NOT Unary
    public SymbolUnary(SymbolUnary unary) throws KotliteException.IncompatibleSubtypeException {
        super("Unary", 0);

        this.subtype = unary.getSubtype();

        if (subtype != Subtype.BOOLEAN)
            throw new KotliteException.IncompatibleSubtypeException("Subtype != BOOLEAN");

        this.negado = !this.negado;
        this.unary = unary;
    }

    // [FORMA] Unary ::= Factor
    public SymbolUnary(SymbolFactor factor) {
        super("Unary", 0);

        this.factor = factor;
        this.subtype = factor.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        if (unary != null) {
            out.print(index + "->" + unary.getIndex() + "\n");

            unary.toDot(out);
        } else {
            out.print(index + "->" + factor.getIndex() + "\n");

            factor.toDot(out);
        }
    }

}
