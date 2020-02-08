/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler.syntax.symbols;

import java.io.IOException;
import java.io.PrintWriter;

public class SymbolProgram extends SymbolBase {

    private SymbolDecls decls;
    private SymbolFunctions functions;

    // [FORMA] Program ::= Decls Functions
    public SymbolProgram(SymbolDecls decls, SymbolFunctions functions) throws IOException {
        super("Program", 0);

        this.decls = decls;
        this.functions = functions;

        //Cerrar tabla y fichero HTML
        symbolTable.buffer.write("</table></body></html>");
        symbolTable.buffer.close();

        //Crear árbol sintáctico
        PrintWriter out = new PrintWriter("ArbolSintactico.dot");
        out.println("strict digraph {");
        toDot(out);
        out.println("}");
        out.close();
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + "\t[label='" + name + "'];\n");

        out.print(index + "->" + decls.getIndex() + "\n");
        out.print(index + "->" + functions.getIndex() + "\n");

        decls.toDot(out);
        functions.toDot(out);

    }

}
