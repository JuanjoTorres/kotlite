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

public class SymbolFactor extends SymbolBase {

    private SymbolBool bool;
    private SymbolId id;

    private Subtype subtype;

    // [FORMA] Factor ::= LPAREN Bool RPAREN
    public SymbolFactor(SymbolBool bool) {

        super("Factor", 0);
        this.bool = bool;
    }

    // [FORMA] Factor ::= Id
    public SymbolFactor(int symbol, SymbolId id) throws KotliteException.IdentifierNotExistException {

        super("Factor", 0);
        this.id = id;
        this.subtype = id.getSubtype();
    }

    public SymbolFactor(int symbol) {
        super("Factor", 0);
        switch (symbol) {
            case ParserSym.LITERAL:
                subtype = Subtype.STRING;
                break;
            case ParserSym.NUM:
                subtype = Subtype.INT;
                break;
            case ParserSym.TRUE:
            case ParserSym.FALSE:
                subtype = Subtype.BOOLEAN;
                break;
            case ParserSym.NONE:
                subtype = Subtype.NULL;
                break;
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
