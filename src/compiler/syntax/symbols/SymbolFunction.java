/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import compiler.KotliteException;
import compiler.syntax.table.*;

import java.io.PrintWriter;

public class SymbolFunction extends SymbolBase {

    private SymbolId id;
    private SymbolArgsdec argsdec;
    private SymbolBasic basic;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolRtnpart rtnpart;

    // [FORMA] Function ::= FUN Id:id LPAREN Argsdec:v1 RPAREN COLON Basic:v2 LBRACKET Decls:v3 Statments:v4
    //         Rtnpart:v5 RBRACKET
    public SymbolFunction(SymbolId id, SymbolArgsdec argsdec, SymbolBasic basic, SymbolDecls decls,
                          SymbolStatments statments, SymbolRtnpart rtnpart) throws KotliteException.DuplicatedIdentifierException, KotliteException.IncompatibleSubtypeException {
        super("Function", 0);

        this.id = id;
        this.argsdec = argsdec;
        this.basic = basic;
        this.decls = decls;
        this.statments = statments;
        this.rtnpart = rtnpart;

        if (basic.getSubtype() != rtnpart.getSubtype())
            throw new KotliteException.IncompatibleSubtypeException("Incompatible Subtype");

        //Crear símbolo de la funcion
        Symbol function = new Symbol(id.getName(), Type.PROC, basic.getSubtype());

        //Añadir argumentos
        function.getArgs().addAll(argsdec.getArgs());

        //Añadir función a la tabla de simbolos
        symbolTable.add(function);
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        out.print(index + "->" + id.getIndex() + "\n");
        out.print(index + "->" + argsdec.getIndex() + "\n");
        out.print(index + "->" + basic.getIndex() + "\n");
        out.print(index + "->" + decls.getIndex() + "\n");
        out.print(index + "->" + statments.getIndex() + "\n");
        out.print(index + "->" + rtnpart.getIndex() + "\n");

        id.toDot(out);
        argsdec.toDot(out);
        basic.toDot(out);
        decls.toDot(out);
        statments.toDot(out);
        rtnpart.toDot(out);
    }

}
