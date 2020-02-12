package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.Subtype;
import compiler.syntax.table.Type;

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
            Output.writeError("Error semántico en posición " + line + ":" + column + " - Incompatible Subtype");
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
            Output.writeError("Error semántico en posición " + line + ":" + column + " - Condition Subtype must be BOOLEAN");
    }

    // [FORMA] Statment ::= WHILE LPAREN Bool RPAREN LBRACKET Decls
    //         Statments RBRACKET
    public SymbolStatment(SymbolBool bool, SymbolDecls decls, SymbolStatments statments, int line, int column) {
        super("Statment", 0);

        this.bool = bool;
        this.decls = decls;
        this.statments = statments;

        if (bool.getSubtype() != Subtype.BOOLEAN)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - Condition Subtype must be BOOLEAN");
    }

    // [FORMA] Statment ::= Id ASSIGN Functioncall SEMICOLON
    public SymbolStatment(SymbolId id, SymbolFunctioncall functioncall, int line, int column) {
        super("Statment", 0);

        this.id = id;
        this.functioncall = functioncall;

        //TODO Comprobar si es una constante y ya tiene valor asignado
        if (symbolTable.getId(id.getName()).getType() == Type.CONST) {

        }

        if (id.getSubtype() != functioncall.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - Incompatible Subtype");
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
