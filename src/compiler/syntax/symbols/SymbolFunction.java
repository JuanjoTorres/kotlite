package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.*;

import java.io.PrintWriter;

public class SymbolFunction extends SymbolBase {

    private SymbolId id;
    private SymbolArgsdec argsdec;
    private SymbolBasic basic;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolRtnpart rtnpart;

    // [FORMA] Function ::= FUN Id:id LPAREN Argsdec:v1 RPAREN COLON Basic:v2 LBRACKET Decls:v3 Statments:v4
    //         Rtnpart:v5 RBRACKET
    public SymbolFunction(SymbolId id, SymbolArgsdec argsdec, SymbolBasic basic, SymbolDecls decls,
                          SymbolStatments statments, SymbolRtnpart rtnpart, int line, int column) {
        super("Function", 0);

        this.id = id;
        this.argsdec = argsdec;
        this.basic = basic;
        this.decls = decls;
        this.statments = statments;
        this.rtnpart = rtnpart;

        //Comprobar si la función tiene return
        if (rtnpart == null) {
            //Si no tiene return, la función tiene que ser de tipo subyacente None
            if (basic.getSubtype() != Subtype.NULL)
                Output.writeError("Error semántico en posición " + line + ":" + column + " - No return, " + basic.getSubtype() + " subtype return expected");
        } else {
            //Si tiene return, tiene que ser del mismo tipo subyacente que la funcion
            if (basic.getSubtype() != rtnpart.getSubtype())
                Output.writeError("Error semántico en posición " + line + ":" + column + " - Incompatible Subtype");
        }

        //Crear símbolo de la funcion
        Symbol function = new Symbol(id.getName(), Type.PROC, basic.getSubtype());

        if (argsdec != null) {
            //Añadir argumentos
            function.getArgs().addAll(argsdec.getArgs());
        }

        //Añadir función a la tabla de simbolos
        symbolTable.add(function);
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        out.print(index + "->" + id.getIndex() + "\n");
        if (argsdec != null)
            out.print(index + "->" + argsdec.getIndex() + "\n");
        out.print(index + "->" + basic.getIndex() + "\n");
        if (decls != null)
            out.print(index + "->" + decls.getIndex() + "\n");
        if (statments != null)
            out.print(index + "->" + statments.getIndex() + "\n");
        if (rtnpart != null)
            out.print(index + "->" + rtnpart.getIndex() + "\n");

        id.toDot(out);
        if (argsdec != null)
            argsdec.toDot(out);
        basic.toDot(out);
        if (decls != null)
            decls.toDot(out);
        if (statments != null)
            statments.toDot(out);
        if (rtnpart != null)
            rtnpart.toDot(out);
    }

}
