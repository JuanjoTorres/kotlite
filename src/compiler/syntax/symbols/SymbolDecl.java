package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.table.Symbol;
import compiler.syntax.table.Variable;

import java.io.PrintWriter;

public class SymbolDecl extends SymbolBase {

    private SymbolType type;
    private SymbolId id;
    private SymbolBasic basic;
    private SymbolBool bool;

    // FORMA Decl ::= Type Id COLON Basic SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic, int line, int column) {
        super("Decl", 0);

        this.type = type;
        this.id = id;
        this.basic = basic;

        //Añadir id de la variable a la tabla de simbolos
        if (!symbolTable.add(new Symbol(id.getName(), type.getType(), basic.getSubtype())))
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El ID " + id.getName() +
                    " ya se encuentra en la tabla de símbolos en el ámbito actual");

        //TODO Hace falta meter variables delcaradas por el usuario?
        variableTable.put(id.getName(), new Variable(4, basic.getSubtype()));
    }

    // FORMA Decl ::= Type Id COLON Basic ASSIGN Bool SEMICOLON
    public SymbolDecl(SymbolType type, SymbolId id, SymbolBasic basic, SymbolBool bool, int line, int column) {
        super("Decl", 0);

        this.type = type;
        this.id = id;
        this.basic = basic;
        this.bool = bool;

        if (basic.getSubtype() != bool.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El identificador " + id.getName() +
                    " es del tipo subyacente " + basic.getSubtype() + " y se ha asignado un valor del tipo " + bool.getSubtype());

        //Añadir id de la funcion a la table de simbolos
        if (!symbolTable.add(new Symbol(id.getName(), type.getType(), basic.getSubtype())))
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El ID " + id.getName() +
                    " ya se encuentra en la tabla de símbolos en el ámbito actual");

    }

    @Override
    public void toDot(PrintWriter out) {

        out.print(index + " [label=\"" + name + "\"];\n");

        out.print(index + "->" + type.getIndex() + "\n");
        out.print(index + "->" + id.getIndex() + "\n");
        out.print(index + "->" + basic.getIndex() + "\n");
        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");

        type.toDot(out);
        id.toDot(out);
        basic.toDot(out);
        if (bool != null)
            bool.toDot(out);
    }

}
