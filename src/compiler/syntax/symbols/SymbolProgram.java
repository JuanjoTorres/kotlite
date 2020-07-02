package compiler.syntax.symbols;

import compiler.output.Output;

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

        //Cerrar fichero de tabla de símbolos
        Output.closeSymbolTable();

        //Cerrar fichero de tabla de variables
        Output.closeVariableTable();

        //Crear árbol sintáctico
        PrintWriter out = new PrintWriter("ArbolSintactico.dot");
        out.println("strict digraph {");
        toDot(out);
        out.println("}");
        out.close();

        //Comprobar que existe la función main()
        if (symbolTable.getId("main") == null)
            Output.writeError("Error semántico - El programa no tiene función main()");
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (decls != null)
            out.print(index + "->" + decls.getIndex() + "\n");
        if (functions != null)
            out.print(index + "->" + functions.getIndex() + "\n");

        if (decls != null)
            decls.toDot(out);
        if (functions != null)
            functions.toDot(out);

    }

}
