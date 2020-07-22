package compiler.intermediate;

import compiler.output.Output;
import compiler.syntax.tables.Subtype;
import compiler.syntax.tables.Type;
import compiler.syntax.tables.Variable;
import compiler.syntax.tables.VariableTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Optimizer {

    private HashMap<String, Variable> variableTable;
    private ArrayList<ThreeAddressCode> threeAddressCodes;

    private int optimizations = 0;

    /**
     * Clase que aplica las distintas optimizaciones de código máquina vistas en clase
     */
    public Optimizer() {
        variableTable = VariableTable.getTable();
        threeAddressCodes = Generator.getThreeAddressCodes();
    }

    public int optimize() {

        //Hacer optimizaciones mientras haya cambios
        while (true) {

            if (asignacionesDiferidas(threeAddressCodes))
                continue;
            if (operacionesConstantes(threeAddressCodes))
                continue;
            if (operacionesConstantesAritmeticas(threeAddressCodes))
                continue;
            if (operacionesConstantesBooleanas(threeAddressCodes))
                continue;
            if (operacionesConstantesRelacionales(threeAddressCodes))
                continue;
            if (operacionesConstantesComparaciones(threeAddressCodes))
                continue;
            //if (eliminacionCodigoInaccesible(threeAddressCodes))
              //  continue;

            //Si no hay cambios se terminan las optimizaciones
            break;
        }

        ArrayList<String> unusedVariables = new ArrayList<>();

        //Recorrer la tabla de variables y eliminar las variables que no se utilizan en el C3D
        variableTable.forEach((id, variable) -> {

            //No eliminar varibles de tipo argumento
            if (variable.getType() == Type.ARG)
                return;

            boolean used = false;

            for (ThreeAddressCode tAC : threeAddressCodes) {
                if (tAC.getOperand1().equals(id) || tAC.getOperand2().equals(id) || tAC.getDestination().equals(id)) {
                    used = true;
                    break;
                }
            }

            //Si no se usa añadirla a la lista para eliminarla
            if (!used) unusedVariables.add(id);

        });

        //Recorrer la tabla de variables y eliminar las variables que no se utilizan en el C3D
        for (String unusedVariable : unusedVariables)
            variableTable.remove(unusedVariable);

        Output.writeInfo("Se han eliminado " + unusedVariables.size() + " variables en desuso de la tabla de variables.");

        return optimizations;
    }

    /**
     * 1 - Brancaments Adjacents
     * <p>
     * Ya está optimizado por defecto
     */
    private void saltosAdyacentes(ArrayList<ThreeAddressCode> threeAddressCodes) {

    }

    /**
     * 2 - Brancaments sobre brancaments
     * <p>
     * No se puede dar en caso en nuestro código porque se aplica la optimización 1 - Brancaments adjacents
     * y los IFGOTO está negado
     */
    private void saltosSobreSaltos(ArrayList<ThreeAddressCode> threeAddressCodes) {

    }

    /**
     * 3 - Asignación de booleanos
     * <p>
     * Ya está optimizado por defecto
     */
    private void asignacionBooleanos(ArrayList<ThreeAddressCode> threeAddressCodes) {
    }

    /**
     * 4.0 - Operaciones constantes
     *
     * @return
     */
    private boolean operacionesConstantes(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Si no es una operación COPY_LITERAL ignorar
            if (!tAC.getOperation().equals("COPY_LITERAL"))
                continue;

            Variable destino = variableTable.get(tAC.getDestination());

            //Si la variable de destino es de tipo String ignorar
            if (destino.getSubtype() == Subtype.STRING)
                continue;

            //Si el destino no es una constante o una variable temporal ignorar
            if (destino.getType() != Type.CONST && !destino.getId().contains("$t#"))
                continue;

            String literalId = tAC.getDestination();

            //Sustituir constante por el valor literal en todas las operaciones que aparezca
            for (int i = 0; i < threeAddressCodes.size(); i++) {

                //Si el operando 1 es igual al nombre de la constante
                if (threeAddressCodes.get(i).getOperand1().equals(literalId)) {

                    String value = tAC.getOperand1();

                    if (value.equals("true") || value.equals("True"))
                        value = "1";
                    else if (value.equals("false") || value.equals("False"))
                        value = "0";

                    threeAddressCodes.get(i).setOperand1(value);

                    optimizations++;
                }

                //Si el operando 2 es igual al nombre de la constante
                if (threeAddressCodes.get(i).getOperand2().equals(literalId)) {

                    String value = tAC.getOperand1();

                    if (value.equals("true") || value.equals("True"))
                        value = "1";
                    else if (value.equals("false") || value.equals("False"))
                        value = "0";

                    threeAddressCodes.get(i).setOperand2(value);

                    optimizations++;
                }

                //Si el destino es igual al nombre de la constante
                if (threeAddressCodes.get(i).getDestination().equals(literalId)) {

                    String value = tAC.getOperand1();

                    if (value.equals("true") || value.equals("True"))
                        value = "1";
                    else if (value.equals("false") || value.equals("False"))
                        value = "0";

                    threeAddressCodes.get(i).setDestination(value);

                    optimizations++;
                }
            }

            //Eliminar copy literal original
            threeAddressCodes.remove(tAC);

            Output.writeInfo("Aplicada optimización de operaciones constantes, sustituida constante por valor literal");

            return true;
        }
        return false;
    }

    /**
     * 4.1 - Operaciones constantes aritmeticas
     *
     * @return
     */
    private boolean operacionesConstantesAritmeticas(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Comprobar si es una operación arimética con valores constantes
            if (!tAC.getOperation().equals("PLUS") && !tAC.getOperation().equals("MINUS") &&
                    !tAC.getOperation().equals("MULTI") && !tAC.getOperation().equals("DIV")) {
                continue;
            }

            //Valor de los operandos
            int value1;
            int value2;

            if (tAC.getOperand1().contains("$t#") && tAC.getOperand2().contains("$t#")) {
                //Ambos operandos son variables temporales

                //Leer las dos variables de la tabla de variables
                Variable variable1 = variableTable.get(tAC.getOperand1());
                Variable variable2 = variableTable.get(tAC.getOperand2());

                //Ambos operandos tienen que tener valor definido
                if (variable1.getValue() == null || variable2.getValue() == null)
                    continue;

                value1 = Integer.parseInt(variable1.getValue());
                value2 = Integer.parseInt(variable2.getValue());
            } else if (!tAC.getOperand1().contains("$") && tAC.getOperand2().contains("$t#")) {
                //El primer operando es temporal y el segundo literal

                Variable variable2 = variableTable.get(tAC.getOperand2());

                //La variable temporal tiene que tener valor
                if (variable2.getValue() == null)
                    continue;

                //El valor literal tiene que ser numerico
                if (!tAC.getOperand1().matches("-?\\d+"))
                    continue;

                value1 = Integer.parseInt(tAC.getOperand1());
                value2 = Integer.parseInt(variable2.getValue());
            } else if (!tAC.getOperand1().contains("$t#") && tAC.getOperand2().contains("$")) {
                //El primer operando es temporal y el segundo literal

                Variable variable1 = variableTable.get(tAC.getOperand2());

                //La variable temporal tiene que tener valor
                if (variable1.getValue() == null)
                    continue;

                //El valor literal tiene que ser numerico
                if (!tAC.getOperand2().matches("-?\\d+"))
                    continue;

                value1 = Integer.parseInt(variable1.getValue());
                value2 = Integer.parseInt(tAC.getOperand2());
            } else if (!tAC.getOperand1().contains("$") && tAC.getOperand2().contains("$")) {
                //Ambos valores son literales


                //Los valores literales tienen que ser numericos
                if (!tAC.getOperand1().matches("-?\\d+") || !tAC.getOperand2().matches("-?\\d+"))
                    continue;

                value1 = Integer.parseInt(tAC.getOperand1());
                value2 = Integer.parseInt(tAC.getOperand2());
            } else {
                //No se cumplen los requisitos
                continue;
            }

            //Buscar los dos COPY_LITERAL de los dos operandos, si existen
            int posCopyLiteral1 = -1;
            int posCopyLiteral2 = -1;

            for (int i = 0; i < threeAddressCodes.size(); i++) {
                if (threeAddressCodes.get(i).getOperation().equals("COPY_LITERAL")
                        && threeAddressCodes.get(i).getDestination().equals(tAC.getOperand1()))
                    posCopyLiteral1 = i;

                if (threeAddressCodes.get(i).getOperation().equals("COPY_LITERAL")
                        && threeAddressCodes.get(i).getDestination().equals(tAC.getOperand2()))
                    posCopyLiteral2 = i;
            }

            //Eliminar los dos COPY_LITERAL de códio de tres direcciones, si se han encontrado
            if (posCopyLiteral2 > -1)
                threeAddressCodes.remove(posCopyLiteral2);
            if (posCopyLiteral1 > -1)
                threeAddressCodes.remove(posCopyLiteral1);

            int result = 0;

            switch (tAC.getOperation()) {
                case "PLUS":
                    result = value1 + value2;
                    break;
                case "MINUS":
                    result = value1 - value2;
                    break;
                case "MULTI":
                    result = value1 * value2;
                    break;
                case "DIV":
                    result = value1 / value2;
                    break;
            }

            //Cambiar instrucción de aritmética a COPY_LITERAL con el valor ya calculado
            tAC.setOperation("COPY_LITERAL");
            tAC.setOperand1(String.valueOf(result));
            tAC.setOperand2(null);

            //Actualizar valor de destino en la tabla de variables
            Variable destino = variableTable.get(tAC.getDestination());
            destino.setValue(String.valueOf(result));

            Output.writeInfo("Aplicada optimización de operaciones constantes aritméticas");

            optimizations++;
            return true;

        }
        return false;
    }

    /**
     * 4.2 - Operaciones constantes booleanas
     *
     * @return
     */
    private boolean operacionesConstantesBooleanas(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Comprobar si es una operación booleana con valores constantes
            if (!tAC.getOperation().equals("OR") && !tAC.getOperation().equals("AND") && !tAC.getOperation().equals("NOT"))
                continue;

            //Si el operando 1 es una variable ignorar
            if (!tAC.getOperand1().equals("0") && !tAC.getOperand1().equals("1"))
                continue;

            //Si hay operando 2 (No es NOT) y es una variable, ignorar
            if (!tAC.getOperation().equals("NOT") && !tAC.getOperand2().equals("0") && !tAC.getOperand2().equals("1"))
                continue;

            //Cambiar operandoss en función de la operación y sus valores
            switch (tAC.getOperation()) {
                case "NOT":
                    if (tAC.getOperand1().equals("0"))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "AND":
                    if (tAC.getOperand1().equals("1") && tAC.getOperand2().equals("1"))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "OR":
                    if (tAC.getOperand1().equals("1") || tAC.getOperand2().equals("1"))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
            }

            //Cambiar operacion a COPY_LITERAL
            tAC.setOperation("COPY_LITERAL");

            //Eliminar segundo operando
            tAC.setOperand2("");

            Output.writeInfo("Aplicada optimización de operaciones constantes booleanas");

            optimizations++;
            return true;

        }
        return false;
    }

    /**
     * 4.3 - Operaciones constantes relacionales
     *
     * @return
     */
    private boolean operacionesConstantesRelacionales(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Comprobar si es una operación relacional
            if (!tAC.getOperation().equals("LT") && !tAC.getOperation().equals("LTEQU") &&
                    !tAC.getOperation().equals("GT") && !tAC.getOperation().equals("GTEQU"))
                continue;

            //Si uno de los operandos es una variable ignorar
            if (tAC.getOperand1().contains("$") || tAC.getOperand2().contains("$"))
                continue;

            //Cambiar operandos en función de la operación y sus valores
            switch (tAC.getOperation()) {
                case "LT":
                    if (Integer.parseInt(tAC.getOperand1()) < Integer.parseInt(tAC.getOperand2()))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "LTEQU":
                    if (Integer.parseInt(tAC.getOperand1()) <= Integer.parseInt(tAC.getOperand2()))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "GT":
                    if (Integer.parseInt(tAC.getOperand1()) > Integer.parseInt(tAC.getOperand2()))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "GTEQU":
                    if (Integer.parseInt(tAC.getOperand1()) >= Integer.parseInt(tAC.getOperand2()))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
            }

            //Cambiar operacion a COPY_LITERAL
            tAC.setOperation("COPY_LITERAL");

            //Eliminar segundo operando
            tAC.setOperand2("");

            Output.writeInfo("Aplicada optimización de operaciones constantes relacionales");

            optimizations++;
            return true;
        }
        return false;
    }

    /**
     * 4.4 - Operaciones constantes de comparacion
     *
     * @return
     */
    private boolean operacionesConstantesComparaciones(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Comprobar si es una operación comparativa
            if (!tAC.getOperation().equals("EQU") && !tAC.getOperation().equals("NOTEQU"))
                continue;

            //Si uno de los operandos es una variable ignorar
            if (!tAC.getOperand1().contains("$") || !tAC.getOperand2().contains("$"))
                continue;

            //Cambiar operandoss en función de la operación y sus valores
            switch (tAC.getOperation()) {
                case "EQU":
                    if (tAC.getOperand1().equals(tAC.getOperand2()))
                        tAC.setOperand1("1");
                    else
                        tAC.setOperand1("0");
                    break;
                case "NOTEQU":
                    if (tAC.getOperand1().equals(tAC.getOperand2()))
                        tAC.setOperand1("0");
                    else
                        tAC.setOperand1("1");
                    break;
            }

            //Cambiar operacion a COPY_LITERAL
            tAC.setOperation("COPY_LITERAL");

            //Eliminar segundo operando
            tAC.setOperand2("");

            Output.writeInfo("Aplicada optimización de operaciones constantes de comparaciones");

            optimizations++;
            return true;
        }
        return false;
    }

    /**
     * 5 - Eliminacion de código inaccesible
     *
     * @return
     */
    private boolean eliminacionCodigoInaccesible(ArrayList<ThreeAddressCode> threeAddressCodes) {
        for (int i = 0; i < threeAddressCodes.size(); i++) {
            ThreeAddressCode tAC = threeAddressCodes.get(i);

            //Si no es una operación condicional ignorar
            if (!tAC.getOperation().equals("IFGOTO"))
                continue;

            String condicion = tAC.getOperand1();

            //Si la condición es una variable, ignorar
            if (condicion.contains("$"))
                continue;

            boolean condIsTrue = condicion.equals("1");
            boolean condIsFalse = condicion.equals("0");

            String falseLabel = tAC.getDestination();
            String trueLabel = falseLabel.replace("false", "true");

            String endLabel = "";

            int init = 0;
            int end = 0;

            if (condIsTrue) {
                //Eliminar entre GOTO true y SKIP true
                endLabel = trueLabel;
                for (int j = i + 1; j < threeAddressCodes.size(); j++) {
                    if (threeAddressCodes.get(j).getOperation().equals("GOTO") && threeAddressCodes.get(j).getDestination().equals(trueLabel)) {
                        init = j;
                        break;
                    }
                }

                //Eliminar IFGOTO
            } else if (condIsFalse) {
                //Eliminar entre IFGOTO y SKIP false
                init = i;
                endLabel = falseLabel;
                //Eliminar SKIP true
            }

            //Buscar posicion de end Label
            for (int j = init + 1; j < threeAddressCodes.size(); j++) {
                if (threeAddressCodes.get(j).getOperation().equals("SKIP") && threeAddressCodes.get(j).getDestination().equals(endLabel)) {
                    end = j;
                    break;
                }
            }

            //Eliminar entre init y end, desde atras hacia alante para no romperlo tot
            for (int k = end; k > init - 1; k--) {
                threeAddressCodes.remove(k);
            }

            //Eliminar IFGOTO o SKIP true en función de si es true o false
            if (condIsTrue) {
                for (int l = 0; l < threeAddressCodes.size(); l++) {
                    if (threeAddressCodes.get(l).getOperation().equals("IFGOTO") && threeAddressCodes.get(l).getDestination().equals(falseLabel)) {
                        threeAddressCodes.remove(l);
                        break;
                    }
                }
            } else if (condIsFalse) {
                for (int l = 0; l < threeAddressCodes.size(); l++) {
                    if (threeAddressCodes.get(l).getOperation().equals("SKIP") && threeAddressCodes.get(l).getDestination().equals(trueLabel)) {
                        threeAddressCodes.remove(l);
                        break;
                    }
                }
            }

            Output.writeInfo("Aplicada optimización de eliminación de código inaccesible");

            optimizations++;
            return true;
        }
        return false;
    }

    /**
     * 8 - Asignaciones diferidas
     *
     * @return
     */
    private boolean asignacionesDiferidas(ArrayList<ThreeAddressCode> threeAddressCodes) {
        for (int i = 0; i < threeAddressCodes.size(); i++) {
            ThreeAddressCode tAC = threeAddressCodes.get(i);

            //Si no es COPY_LITERAL ignorar
            if (!tAC.getOperation().equals("COPY_LITERAL"))
                continue;

            //Si el destino no es una variable temporal ignorar
            if (!tAC.getDestination().contains("$t#"))
                continue;

            //Si el destino es de tipo String ignorar
            if (variableTable.get(tAC.getDestination()).getSubtype() == Subtype.STRING)
                continue;

            //Buscar COPY diferido
            for (ThreeAddressCode copyDiferido : threeAddressCodes) {
                //Buscamos operación COPY con operando1 igual al destino anterior
                if (copyDiferido.getOperation().equals("COPY") && copyDiferido.getOperand1().equals(tAC.getDestination())) {

                    //Cambiar operación de COPY a COPY_LITERAL
                    copyDiferido.setOperation("COPY_LITERAL");

                    //Cambiar operando1 a valor literal
                    copyDiferido.setOperand1(tAC.getOperand1());

                    //Obtener valor diferido
                    String valorDiferido = variableTable.get(tAC.getDestination()).getValue();

                    //Cambiar valor de la nueva variable de destino en tabla de variables
                    variableTable.get(copyDiferido.getDestination()).setValue(valorDiferido);

                    //Eliminar COPY_LITERAL original
                    threeAddressCodes.remove(i);

                    Output.writeInfo("Aplicada optimización de asignaciones diferidas");

                    optimizations++;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 9 - Reducció de fuerza
     */
    private void reduccionFuerza(ArrayList<ThreeAddressCode> threeAddressCodes) {
        //No está implementada
    }
}
