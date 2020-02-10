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

        //Subtype de AND o OR es BOOL
        subtype = Subtype.BOOLEAN;
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
        out.print(index + " [label=\"" + name + "\"];\n");

        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");
        if (join != null)
            out.print(index + "->" + join.getIndex() + "\n");
        out.print(index + "->" + relation.getIndex() + "\n");

        if (bool != null)
            bool.toDot(out);
        if (join != null)
            join.toDot(out);
        relation.toDot(out);
    }

}
