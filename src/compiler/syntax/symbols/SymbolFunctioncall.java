package compiler.syntax.symbols;

import compiler.syntax.table.Subtype;
import compiler.syntax.table.Symbol;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SymbolFunctioncall extends SymbolBase {

    private SymbolId id;
    private SymbolArgs args;

    private Subtype subtype;

    // [FORMA] Functioncall ::= Id LPAREN Args RPAREN
    public SymbolFunctioncall(SymbolId id, SymbolArgs args) {
        super("Functioncall", 0);

        this.id = id;
        this.args = args;

        subtype = id.getSubtype();

        ArrayList<Symbol> functionArgs = symbolTable.getId(id.getName()).getArgs();
        //TODO Hacer comprobacion del numero de argumentos
        //if (args.size() < functionArgs.size())
        //MissingArgumentException

        for (int i = 0; i < functionArgs.size(); i++) {

        }
    }

    public Subtype getSubtype() {
        return subtype;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        out.print(index + "->" + id.getIndex() + "\n");
        if (args != null)
            out.print(index + "->" + args.getIndex() + "\n");

        id.toDot(out);
        if (args != null)
            args.toDot(out);
    }

}
