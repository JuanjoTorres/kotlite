/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;
import compiler.syntax.table.Symbol;
import compiler.syntax.table.Type;

import java.io.PrintWriter;

public class SymbolDecl extends SymbolBase {

    private SymbolType type;
    private SymbolId id;
    private SymbolBasic basic;
    private SymbolBool bool;

    // FORMA Decl ::= Type Id COLON Basic SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic) throws KotliteException.DuplicatedIdentifierException {

        super("Decl", 0);
        this.type = type;
        this.id = id;
        this.basic = basic;

        //Añadir id de la funcion a la table de simbolos
        symbolTable.add(new Symbol(id.getName(), type.getType(), basic.getSubtype()));
    }

    // FORMA Decl ::= Type Id COLON Basic ASSIGN Bool SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic, SymbolBool bool) throws KotliteException.DuplicatedIdentifierException, KotliteException.IncompatibleSubtypeException {

        super("Decl", 0);
        this.type = type;
        this.id = id;
        this.basic = basic;
        this.bool = bool;

        if (basic.getSubtype() != bool.getSubtype())
            throw new KotliteException.IncompatibleSubtypeException("Incompatible Subtype");

        //Añadir id de la funcion a la table de simbolos
        symbolTable.add(new Symbol(id.getName(), type.getType(), basic.getSubtype()));
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
