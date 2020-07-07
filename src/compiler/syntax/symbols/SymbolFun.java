package compiler.syntax.symbols;

import compiler.codegeneration.TAC;

import java.io.PrintWriter;

public class SymbolFun extends SymbolBase {

    // FORMA Fun ::= .
    public SymbolFun() {
        super("Fun", 0);

        //Crear etiqueta
        TAC.genera("GOTO", "", "", TAC.popFunctionLabel());
    }

    @Override
    public void toDot(PrintWriter out) {
    }

}
