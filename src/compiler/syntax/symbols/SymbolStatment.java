package compiler.syntax.symbols;

import compiler.output.Output;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;

import java.io.PrintWriter;

public class SymbolStatment extends SymbolBase {

    private SymbolId id;
    private SymbolBool bool;
    private SymbolBool bool2;
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

        //Comprobar si es una constante
        if (id.getVariable().getType() == Type.CONST)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La variable " + id.getName() +
                    " es de tipo constante. No se le puede asignar un valor");

        if (id.getSubtype() != bool.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El id " + id.getName() +
                    " es del tipo subyacente " + id.getSubtype() + " y se le ha asignado un valor del tipo " + bool.getSubtype());

        // Copiar valor de retorno en la variable del ID
        generator.addThreeAddressCode("COPY", bool.getVariable().getId(), "", id.getVariable().getId());
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

        //Comprobar si es una constante
        if (id.getVariable().getType() == Type.CONST)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La variable " + id.getName() +
                    " es de tipo constante. No se le puede asignar un valor del retorno de una función.");

        if (id.getSubtype() != functioncall.getSubtype())
            Output.writeError("Error semántico en posición " + line + ":" + column + " - El id " + id.getName() +
                    " es del tipo subyacente " + id.getSubtype() + " y se la ha asignado un valor de retorno de función del tipo " + functioncall.getSubtype());

        // Copiar valor de retorno en la variable del ID
        generator.addThreeAddressCode("COPY", functioncall.getVariable().getId(), "", id.getVariable().getId());
    }

    // [FORMA] Statment ::= Functioncall SEMICOLON
    public SymbolStatment(SymbolFunctioncall functioncall) {

        super("Statment", 0);
        this.functioncall = functioncall;
    }

    // [FORMA] Statment ::= Id ASSIGN GET LPAREN RPAREN SEMICOLON
    public SymbolStatment(SymbolId id, int line, int column) {
        super("Statment", 0);

        this.id = id;

        //Comprobar si es una constante
        if (id.getVariable().getType() == Type.CONST)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La variable " + id.getName() +
                    " es de tipo constante. No se le puede asignar un valor de entrada");

        //Comprobar si no es de tipo entero
        if (id.getVariable().getSubtype() != Subtype.INT)
            Output.writeError("Error semántico en posición " + line + ":" + column + " - La función GET solo soporta números enteros " +
                    "como  valor de entrada, y la variable" + id.getName() + "es del tipo " + id.getVariable().getSubtype());

        //Generar variable y meterla en la tabla de variables
        Variable variable = new Variable(generator.generateVariable(), generator.peekFunctionLabel(), true);
        variable.setType(Type.VAR);
        variable.setSubtype(Subtype.INT);
        variable.setDeep(symbolTable.getDeep());
        variableTable.put(variable.getId(), variable);

        // Get a variable temporal y copy de variable temporal al id
        generator.addThreeAddressCode("GET", "", "", variable.getId());
        generator.addThreeAddressCode("COPY", variable.getId(), "", id.getVariable().getId());
    }

    // [FORMA] Statment ::= PRINT LPAREN Bool RPAREN SEMICOLON
    public SymbolStatment(SymbolBool bool, int line, int column) {
        super("Statment", 0);

        this.bool = bool;

        // ¿Comprobar si es una constante y ya tiene valor asignado?

        if (bool.getSubtype() != Subtype.STRING)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El primer parametro de la función print() debe ser del tipo subyacente STRING " +
                    " y es del tipo subyacente " + bool.getSubtype());

        // Copiar valor de retorno en la variable del ID
        generator.addThreeAddressCode("PRINT", bool.getVariable().getId(), "", "");
    }

    // [FORMA] Statment ::= PRINT LPAREN Bool Bool RPAREN SEMICOLON
    public SymbolStatment(SymbolBool bool, SymbolBool bool2, int line, int column) {
        super("Statment", 0);

        this.bool = bool;
        this.bool2 = bool2;

        // ¿Comprobar si es una constante y ya tiene valor asignado?

        if (bool.getSubtype() != Subtype.STRING)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - El primer parametro de la función print() debe ser del tipo subyaacente STRING " +
                    " y es del tipo subyacente " + bool.getSubtype());

        if (bool2.getSubtype() == Subtype.STRING)
            Output.writeError("Error semántico en posición " + line + ":" + column +
                    " - La función print() no admite 2 strings. Se ha encontrado: " + bool.getSubtype() + " - "  + bool.getSubtype());

        String instruction;

        if (bool2.getSubtype() == Subtype.INT)
            instruction = "PRINTINT";
        else if (bool2.getSubtype() == Subtype.BOOLEAN)
            instruction = "PRINTBOOL";
        else
            instruction = "PRINT";

        // Copiar valor de retorno en la variable del ID
        generator.addThreeAddressCode(instruction, bool.getVariable().getId(), bool2.getVariable().getId(), "");
    }

    @Override
    public void toDot(PrintWriter out) {
        out.print(index + " [label=\"" + name + "\"];\n");

        if (id != null)
            out.print(index + "->" + id.getIndex() + "\n");
        if (bool != null)
            out.print(index + "->" + bool.getIndex() + "\n");
        if (bool2 != null)
            out.print(index + "->" + bool2.getIndex() + "\n");
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
        if (bool2 != null)
            bool2.toDot(out);
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
