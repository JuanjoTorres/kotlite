/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.sintax.symbols;

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
                          SymbolStatments statments, SymbolRtnpart rtnpart) {

        super("Function", 0);
        this.id = id;
        this.argsdec = argsdec;
        this.basic = basic;
        this.decls = decls;
        this.statments = statments;
        this.rtnpart = rtnpart;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label=\"" + name + "\"];\n" + index + "->\"" + name + "\"\n");
    }

}
