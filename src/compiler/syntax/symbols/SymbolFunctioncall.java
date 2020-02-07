/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;
import compiler.syntax.table.Symbol;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolFunctioncall extends SymbolBase {

    private SymbolId id;
    private SymbolArgs args;

    private Subtype subtype;

    // [FORMA] Functioncall ::= Id LPAREN Args RPAREN
    public SymbolFunctioncall(SymbolId id, SymbolArgs args) throws KotliteException.IdentifierNotExistException {

        super("Functioncall", 0);
        this.id = id;
        this.args = args;

        subtype = id.getSubtype();


        ArrayList<Symbol> functionArgs = symbolTable.getId(id.getName()).getArgs();

        //if (args.size() < functionArgs.size())
        //MissingArgumentException

        for (int i = 0; i < functionArgs.size(); i++) {

        }
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
