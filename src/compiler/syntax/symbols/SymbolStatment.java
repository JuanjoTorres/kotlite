package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.Subtype;

import java.io.PrintWriter;

public class SymbolStatment extends SymbolBase {

    private SymbolId id;
    private SymbolBool bool;
    private SymbolDecls decls;
    private SymbolStatments statments;
    private SymbolElsepart elsepart;
    private SymbolFunctioncall functioncall;


    // [FORMA] Statment ::= Id ASSIGN Bool SEMICOLON
    public SymbolStatment(SymbolId id, SymbolBool bool, int line, int column) {
        super("Statment", 0);

        this.id = id;
        this.bool = bool;

        if (id.getSubtype() != bool.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El id " + id.getName() +
                    " es del tipo subyacente " + id.getSubtype() + " y se le ha asignado un valor del tipo " + bool.getSubtype());
    }

    // [FORMA] Statment ::= IF LPAREN Bool RPAREN LBRACKET Decls Statments
    //         RBRACKET Elsepart
    public SymbolStatment(SymbolBool bool, SymbolDecls decls, SymbolStatments statments, SymbolElsepart elsepart, int line, int column) {
        super("Statment", 0);

        this.bool = bool;
        this.decls = decls;
        this.statments = statments;
        this.elsepart = elsepart;

        if (bool.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El condicional IF requiere una condicion del tipo BOOLEAN y se ha encontrado un tipo " + bool.getSubtype());
    }

    // [FORMA] Statment ::= WHILE LPAREN Bool RPAREN LBRACKET Decls
    //         Statments RBRACKET
    public SymbolStatment(SymbolCond cond, SymbolBool bool, SymbolDecls decls, SymbolStatments statments, SymbolEndloop endloop, int line, int column) {
        super("Statment", 0);

        this.bool = bool;
        this.decls = decls;
        this.statments = statments;

        if (bool.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El bucle WHILE requiere una condicion del tipo BOOLEAN y se ha encontrado un tipo " + bool.getSubtype());
    }

    // [FORMA] Statment ::= Id ASSIGN Functioncall SEMICOLON
    public SymbolStatment(SymbolId id, SymbolFunctioncall functioncall, int line, int column) {
        super("Statment", 0);

        this.id = id;
        this.functioncall = functioncall;

        // ¿Comprobar si es una constante y ya tiene valor asignado?

        if (id.getSubtype() != functioncall.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El id " + id.getName() +
                    " es del tipo subyacente " + id.getSubtype() + " y se la ha asignado un valor de retorno de función del tipo " + functioncall.getSubtype());
    }

    // [FORMA] Statment ::= Functioncall SEMICOLON
    public SymbolStatment(SymbolFunctioncall functioncall) {

        super("Statment", 0);
        this.functioncall = functioncall;
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (id != null)
            out.print(index + "->" + id.getIndex() + "\n");
        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");
        if (decls != null)
            out.print(index + "->" + decls.getIndex() + "\n");
        if (statments != null)
            out.print(index + "->" + statments.getIndex() + "\n");
        if (elsepart != null)
            out.print(index + "->" + elsepart.getIndex() + "\n");
        if (functioncall != null)
            out.print(index + "->" + functioncall.getIndex() + "\n");

        if (id != null)
            id.toDot(out);
        if (bool != null)
            bool.toDot(out);
        if (decls != null)
            decls.toDot(out);
        if (statments != null)
            statments.toDot(out);
        if (elsepart != null)
            elsepart.toDot(out);
        if (functioncall != null)
            functioncall.toDot(out);
    }

}
