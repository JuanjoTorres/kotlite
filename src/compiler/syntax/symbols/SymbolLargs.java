package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.Symbol;
import compiler.syntax.table.Type;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolLargs extends SymbolBase {

    private SymbolLargs largs;
    private SymbolFactor factor;

    private ArrayList<Symbol> args = new ArrayList<>();

    // [FORMA] Largs ::= Largs COMMA Factor
    public SymbolLargs(SymbolLargs largs, SymbolFactor factor) {
        super("Largs", 0);

        this.largs = largs;
        this.factor = factor;

        //Añadir argumentos de lista de argumentos y el argumento actual
        args.addAll(largs.getArgs());
        args.add(new Symbol(factor.getName(), Type.ARG, factor.getSubtype()));
    }

    // [FORMA] Largs ::= Factor
    public SymbolLargs(SymbolFactor factor) {
        super("Largs", 0);

        this.factor = factor;

        //Añadir el argumento actual
        args.add(new Symbol(factor.getName(), Type.ARG, factor.getSubtype()));
    }

    public ArrayList<Symbol> getArgs() {
        return args;
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
