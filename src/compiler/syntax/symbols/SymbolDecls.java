package compiler.syntax.symbols;

import java.io.PrintWriter;

public class SymbolDecls extends SymbolBase {

    private SymbolDecls decls;
    private SymbolDecl decl;

    // FORMA Decls ::= .
    public SymbolDecls() {
        super("Decls", 0);
    }


    // FORMA Decls ::= Decls Decl
    public SymbolDecls(SymbolDecls decls, SymbolDecl decl) {
        super("Decls", 0);

        this.decls = decls;
        this.decl = decl;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (decls != null)
            out.print(index + "->" + decls.getIndex() + "\n");
        if (decl != null)
            out.print(index + "->" + decl.getIndex() + "\n");

        if (decls != null)
            decls.toDot(out);
        if (decl != null)
            decl.toDot(out);
    }

}
