package compiler.syntax.symbols;

import compiler.intermediate.Generator;
import compiler.intermediate.ThreeAddressCode;
import compiler.syntax.tables.Procedure;

import java.io.PrintWriter;

public class SymbolFuncstart extends SymbolBase {

    // FORMA Fun ::= .
    public SymbolFuncstart() {
        super("Functionstart", 0);

        String funId = Generator.popFunctionLabel();

        System.out.println("Pop id: " + funId);

        //Si todavía no está en la tabla de procedimientos,
        // es que estamos en la definición de una función
        Procedure procedure = new Procedure();
        procedureTable.put(funId, procedure);

        //Añadir código de tres direcciones con skip y la etiqueta
        Generator.addThreeAddressCode(new ThreeAddressCode("SKIP", "", "", procedure.getStartLabel()));

        System.out.println("Generando etiqueta: " + procedure.getStartLabel());

    }

    @Override
    public void toDot(PrintWriter out) {
    }
}
