package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolFunctions extends SymbolBase {

    private SymbolFunctions functions;
    private SymbolFunction function;

    // [FORMA] Functions ::= .
    public SymbolFunctions() {
        super("Functions", 0);
    }

    // [FORMA] Functions ::= Functions Function
    public SymbolFunctions(SymbolFunctions functions, SymbolFunction function) {
        super("Functions", 0);

        this.functions = functions;
        this.function = function;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (functions != null)
            out.print(index + "->" + functions.getIndex() + "\n");
        if (function != null)
            out.print(index + "->" + function.getIndex() + "\n");

        if (functions != null)
            functions.toDot(out);
        if (function != null)
            function.toDot(out);
    }

}
