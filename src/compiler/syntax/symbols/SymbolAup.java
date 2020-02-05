/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolAup extends SymbolBase {

    // FORMA Aup ::= .
    public SymbolAup() {
        super("Aup", 0);
        symbolTable.startBlock();
    }

    @Override
    public void toDot(PrintWriter out) {

    }

}
