package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.output.Output;
import compiler.syntax.tables.Symbol;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolDecl extends SymbolBase {

    private SymbolType type;
    private SymbolId id;
    private SymbolBasic basic;
    private SymbolBool bool;

    private Variable variable;

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

        //Obtener variable
        variable = bool.getVariable();

        //TODO Meter la variable en la tabla de variables
        //TODO ¿En la variable que del bool o una nueva? ¿Que se mete en ella?
        variableTable.put(id.getName(), variable);

        Variable boolVar = bool.getVariable();

        //Añadir código de tres direcciones con la operacion
        generator.addThreeAddressCode(new ThreeAddressCode("COPY", boolVar.getId(), "", id.getName()));
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
