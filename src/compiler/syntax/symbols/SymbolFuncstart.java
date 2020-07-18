package compiler.syntax.symbols;

import compiler.syntax.tables.Procedure;

import java.io.PrintWriter;

public class SymbolFuncstart extends SymbolBase {

    // FORMA Fun ::= .
    public SymbolFuncstart() {
        super("Functionstart", 0);

        String funId = generator.popIdLabel();
        generator.pushFunctionLabel(funId);

        //Si todavía no está en la tabla de procedimientos,
        // es que estamos en la definición de una función
        Procedure procedure = new Procedure(funId);
        procedureTable.put(funId, procedure);

        //Añadir código de tres direcciones con skip y la etiqueta
        generator.addThreeAddressCode("SKIP", "", "", procedure.getStartLabel());
        generator.addThreeAddressCode("PMB", "", "", procedure.getStartLabel());

    }

    @Override
    public void toDot(PrintWriter out) {
    }
}
