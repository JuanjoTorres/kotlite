/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.Subtype;
import compiler.syntax.table.Type;

import java.io.PrintWriter;

public class SymbolStatment extends SymbolBase {

    private SymbolId id;
    private SymbolBool bool;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolElsepart elsepart;
    private SymbolFunctioncall functioncall;


    // [FORMA] Statment ::= Id ASSIGN Bool SEMICOLON
    public SymbolStatment(SymbolId id, SymbolBool bool) throws KotliteException.IdentifierNotExistException, KotliteException.IncompatibleSubtypeException {

        super("Statment", 0);
        this.id = id;
        this.bool = bool;

        if (id.getSubtype() != bool.getSubtype())
            throw new KotliteException.IncompatibleSubtypeException("Incompatible Subtype");
    }

    // [FORMA] Statment ::= IF LPAREN Bool RPAREN LBRACKET Decls Statments
    //         RBRACKET Elsepart
    public SymbolStatment(SymbolBool bool, SymbolDecls decls, SymbolStatments statments, SymbolElsepart elsepart) throws KotliteException.IncompatibleSubtypeException {

        super("Statment", 0);
        this.bool = bool;
        this.decls = decls;
        this.statments = statments;
        this.elsepart = elsepart;

        if (bool.getSubtype() != Subtype.BOOLEAN)
            throw new KotliteException.IncompatibleSubtypeException("Condition Subtype must be BOOLEAN");
    }

    // [FORMA] Statment ::= WHILE LPAREN Bool RPAREN LBRACKET Decls
    //         Statments RBRACKET
    public SymbolStatment(SymbolBool bool, SymbolDecls decls, SymbolStatments statments) throws KotliteException.IncompatibleSubtypeException {

        super("Statment", 0);
        this.bool = bool;
        this.decls = decls;
        this.statments = statments;

        if (bool.getSubtype() != Subtype.BOOLEAN)
            throw new KotliteException.IncompatibleSubtypeException("Condition Subtype must be BOOLEAN");
    }

    // [FORMA] Statment ::= Id ASSIGN Functioncall SEMICOLON
    public SymbolStatment(SymbolId id, SymbolFunctioncall functioncall) throws KotliteException.IdentifierNotExistException, KotliteException.IncompatibleSubtypeException {
        super("Statment", 0);
        this.id = id;
        this.functioncall = functioncall;

        //TODO Comprobar si es una constante y ya tiene valor asignado
        if (symbolTable.getId(id.getName()).getType() == Type.CONST) {

        }

        if (id.getSubtype() != functioncall.getSubtype())
            throw new KotliteException.IncompatibleSubtypeException("Incompatible Subtype");
    }

    // [FORMA] Statment ::= Functioncall SEMICOLON
    public SymbolStatment(SymbolFunctioncall functioncall) {

        super("Statment", 0);
        this.functioncall = functioncall;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
