/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;

import java.io.PrintWriter;

public class SymbolBool extends SymbolBase {

    private SymbolBool bool;
    private SymbolJoin join;
    private SymbolRelation relation;

    private Subtype subtype;

    // FORMA Bool ::= Bool Join Relation
    public SymbolBool(SymbolBool bool, SymbolJoin join, SymbolRelation relation) throws KotliteException.IncompatibleSubtypeException {

        super("Bool", 0);
        this.bool = bool;
        this.join = join;
        this.relation = relation;

        if (bool.getSubtype() != Subtype.BOOLEAN || relation.getSubtype() != Subtype.BOOLEAN)
            throw new KotliteException.IncompatibleSubtypeException("Join requires BOOLEAN Subtype");

    }

    // FORMA Bool ::= Relation
    public SymbolBool(SymbolRelation relation) {
        super("Bool", 0);
        this.relation = relation;

        subtype = relation.getSubtype();
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
