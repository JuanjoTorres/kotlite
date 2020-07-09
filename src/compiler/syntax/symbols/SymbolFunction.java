package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.*;

import java.io.PrintWriter;

public class SymbolFunction extends SymbolBase {

    private SymbolFun fun;
    private SymbolId id;
    private SymbolArgsdec argsdec;
    private SymbolBasic basic;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolRtnpart rtnpart;

    // [FORMA] Function ::= FUN Id:id LPAREN Argsdec:v1 RPAREN COLON Basic:v2 LBRACKET Decls:v3 Statments:v4
    //         Rtnpart:v5 RBRACKET
    public SymbolFunction(SymbolId id, SymbolFun fun, SymbolArgsdec argsdec, SymbolBasic basic, SymbolDecls decls,
                          SymbolStatments statments, SymbolRtnpart rtnpart, int line, int column) {
        super("Function", 0);

        this.fun = fun;
        this.id = id;
        this.argsdec = argsdec;
        this.basic = basic;
        this.decls = decls;
        this.statments = statments;
        this.rtnpart = rtnpart;

        //Comprobar si la función tiene return
        if (rtnpart == null) {
            //Si no tiene return, la función tiene que ser de tipo subyacente None
            if (basic.getSubtype() != Subtype.NONE)
                Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() +
                        " es del tipo subyacente " + basic.getSubtype() + " y no se ha encontrado RETURN");
        } else {
            //Si tiene return, tiene que ser del mismo tipo subyacente que la funcion
            if (basic.getSubtype() != rtnpart.getSubtype())
                Output.writeError("Error semántico en posición " + line + ":" + column + " - La función " + id.getName() +
                        " es del tipo subyacente " + basic.getSubtype() + " y se ha encontrado RETURN del tipo " + rtnpart.getSubtype());
        }

        //Crear símbolo de la funcion
        Symbol function = new Symbol(id.getName(), Type.PROC, basic.getSubtype());

        if (argsdec != null) {
            //Añadir argumentos
            function.getArgs().addAll(argsdec.getArgs());
        }

        //Añadir id de la función a la tabla de simbolos
        if (!symbolTable.add(function))
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El ID " + id.getName() +
                    " ya se encuentra en la tabla de símbolos en el ámbito actual");

        //Obtener el procedimiento para calcular sus propiedades
        Procedure procedure = id.getProcedure();
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
