/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

import java.io.PrintWriter;

public class SymbolStatment extends SymbolBase {

    private SymbolId id;
    private SymbolBool bool;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolElsepart elsepart;
    private SymbolFunctioncall functioncall;


    // [FORMA] Statment ::= Id ASSIGN Bool SEMICOLON
    public SymbolStatment(SymbolId id, SymbolBool bool) {

        super("Statment", 0);
        this.id = id;
        this.bool = bool;
    }

    // [FORMA] Statment ::= IF LPAREN Bool RPAREN LBRACKET Decls Statments
    //         RBRACKET Elsepart
    public SymbolStatment(SymbolBool bool, SymbolDecls decls,
                          SymbolStatments statments, SymbolElsepart elsepart) {

        super("Statment", 0);
        this.bool = bool;
        this.decls = decls;
        this.statments = statments;
        this.elsepart = elsepart;
    }

    // [FORMA] Statment ::= WHILE LPAREN Bool RPAREN LBRACKET Decls
    //         Statments RBRACKET
    public SymbolStatment(SymbolBool bool, SymbolDecls decls,
                          SymbolStatments statments) {

        super("Statment", 0);
        this.bool = bool;
        this.decls = decls;
        this.statments = statments;
    }

    // [FORMA] Statment ::= Id ASSIGN Functioncall SEMICOLON
    public SymbolStatment(SymbolId id, SymbolFunctioncall functioncall)
    {
        super("Statment", 0);
        this.id = id;
        this.functioncall = functioncall;
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
