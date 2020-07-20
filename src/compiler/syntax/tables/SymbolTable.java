package compiler.syntax.tables;

import compiler.output.Output;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    private Stack<HashMap> hashMapStack = new Stack<>();
    private int deep = 0;

    public SymbolTable() {

        //Iniciar tabla de símbolos
        init();
    }

    /**
     * Vaciar la tabla de símbolo
     */
    public void init() {

        //Vaciar pila e inicializar nivel 0
        hashMapStack = new Stack<>();
        hashMapStack.push(new HashMap());

        //Vaciar contenido de fichero de info
        Output.truncateInfo();

        //Vaciar contenido de fichero de errores
        Output.truncateErrors();

        //Vaciar contenido de fichero de tokens
        Output.truncateTokens();

        //Inicializar fichero de tabla de simbolos
        Output.initSymbolTable();

        /*
        //YA NO ES NECESARIO, EL PRINT ESTÁ INCLUIDO EN LA GRAMÁTICA

        //Añadir función print() simple a la tabla de simbolos
        Symbol print = new Symbol("print", Type.PROC, Subtype.NONE);
        print.getArgs().add(new Symbol("text", Type.ARG, Subtype.STRING));
        add(print);

        //Añadir función printInt() simple a la tabla de simbolos
        Symbol printInt = new Symbol("printInt", Type.PROC, Subtype.NONE);
        printInt.getArgs().add(new Symbol("text", Type.ARG, Subtype.STRING));
        printInt.getArgs().add(new Symbol("int", Type.ARG, Subtype.INT));
        add(printInt);

        //Añadir función printInt() simple a la tabla de simbolos
        Symbol printBool = new Symbol("printBool", Type.PROC, Subtype.NONE);
        printBool.getArgs().add(new Symbol("text", Type.ARG, Subtype.STRING));
        printBool.getArgs().add(new Symbol("bool", Type.ARG, Subtype.BOOLEAN));
        add(printBool);

        */
    }

    public void startBlock() {
        //Aumentar nivel insertando un nuevo hashmap en la pila
        hashMapStack.push(new HashMap());
        deep++;
    }

    public void endBlock() {
        //Reducir nivel eliminando un hashmap de la pila
        hashMapStack.pop();
        deep--;
    }

    public boolean add(Symbol symbol) {
        //Comprobar que no exista el identificador en el ámbito actual de la tabla de simbolos
        if (!hashMapStack.peek().containsKey(symbol.getId())) {
            //Insertar identificador en la tabla de símbolos
            hashMapStack.peek().put(symbol.getId(), symbol);

            //Añadir symbol al fichero de la tabla de símbolos
            Output.writeSymbol(symbol, hashMapStack);
        } else {
            return false;
        }

        return true;
    }

    public Symbol getId(String id) {

        //Recorrer la tabla de símbolos desde el nivel superior hacia el inferior
        for (int i = hashMapStack.size() - 1; i >= 0; i--) {

            //Comprobar si existe el identificador en el nivel actual
            if (hashMapStack.get(i).containsKey(id))
                //Devolver identificador
                return (Symbol) hashMapStack.get(i).get(id);

        }

        return null;
    }

    /**
     * Obtener profundidad
     * @return deep
     */
    public int getDeep() {
        return deep;
    }
}
