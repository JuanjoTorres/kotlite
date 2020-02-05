/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolAdown extends SymbolBase {

    // FORMA Adown ::= .
    public SymbolAdown() {
        super("Adown", 0);
        symbolTable.endBlock();
    }

    @Override
    public void toDot(PrintWriter out) {

    }

}
