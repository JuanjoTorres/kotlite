package compiler.syntax.symbols;

import compiler.syntax.table.Symbol;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolArgs extends SymbolBase {

    private SymbolLargs largs;

    // FORMA Args ::= .
    public SymbolArgs() {
        super("Args", 0);
    }

    // FORMA Args ::= Largs
    public SymbolArgs(SymbolLargs largs) {
        super("Args", 0);
        this.largs = largs;
    }

    public ArrayList<Symbol> getArgs() {
        return largs.getArgs();
    }


    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (largs != null) {
            out.print(index + "->" + largs.getIndex() + "\n");
            largs.toDot(out);
        }
    }

}
