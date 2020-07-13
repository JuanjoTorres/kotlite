package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.Subtype;

import java.io.PrintWriter;

public class SymbolStatment extends SymbolBase {

    private SymbolId id;
    private SymbolBool bool;
    private SymbolCond cond;
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
    public SymbolStatment(SymbolCond cond, SymbolDecls decls, SymbolStatments statments, SymbolElsepart elsepart, int line, int column) {
        super("Statment", 0);

        this.cond = cond;
        this.decls = decls;
        this.statments = statments;
        this.elsepart = elsepart;

        if (cond.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El condicional IF requiere una condicion del tipo BOOLEAN y se ha encontrado un tipo " + cond.getSubtype());
    }

    // [FORMA] Statment ::= WHILE LPAREN Bool RPAREN LBRACKET Decls
    //         Statments RBRACKET
    public SymbolStatment(SymbolCond cond, SymbolDecls decls, SymbolStatments statments, int line, int column) {
        super("Statment", 0);

        this.cond = cond;
        this.decls = decls;
        this.statments = statments;

        if (cond.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El bucle WHILE requiere una condicion del tipo BOOLEAN y se ha encontrado un tipo " + cond.getSubtype());
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

        // Copiar valor de retorno en la variable del ID
        generator.addThreeAddressCode("COPY", functioncall.getVariable().getId(), "", id.getName());
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
        if (cond != null)
            out.print(index + "->" + cond.getIndex() + "\n");
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
        if (cond != null)
            cond.toDot(out);
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
