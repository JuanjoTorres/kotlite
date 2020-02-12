package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolLargs extends SymbolBase {

    private SymbolLargs largs;
    private SymbolFactor factor;

    // [FORMA] Largs ::= Largs COMMA Factor
    public SymbolLargs(SymbolLargs largs, SymbolFactor factor) {
        super("Largs", 0);

        this.largs = largs;
        this.factor = factor;
    }

    // [FORMA] Largs ::= Factor
    public SymbolLargs(SymbolFactor factor) {
        super("Largs", 0);

        this.factor = factor;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (largs != null)
            out.print(index + "->" + largs.getIndex() + "\n");
        out.print(index + "->" + factor.getIndex() + "\n");

        if (largs != null)
            largs.toDot(out);
        factor.toDot(out);
    }

}
