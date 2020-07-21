package compiler.intermediate;

import compiler.output.Output;
import compiler.syntax.tables.Variable;
import compiler.syntax.tables.VariableTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Optimizer {

    private HashMap<String, Variable> variableTable;
    private ArrayList<ThreeAddressCode> threeAddressCodes;

    private boolean cambios = true;
    private int optimizations = 0;

    /**
     * Clase que aplica las distintas optimizaciones de código máquina vistas en clase
     */
    public Optimizer() {
        variableTable = VariableTable.getTable();
        threeAddressCodes = Generator.getThreeAddressCodes();
        //Operaciones constantes
            /*
            1 - Recorrer todo el arraylist
            2 - If operator contains ADD, SUM, DIV, MULT
            3 - Buscar operandos, que no sean null y los dos son literales,
                cambiar operacion por COPY, poner resultado en operador 1 y cambiar destino
             */

        //Asignaciones diferidas -> Quitar variables temporales de un solo uso

        //Eliminación de código inaccesible
            /*
                Si hay un IF que es de constantes, se eliminan todas las instrucciones entre el jmp y el skip
             */

    }

    public int optimize() {
        while (cambios) {
            cambios = false;
            saltosAdyacentes(threeAddressCodes);
            saltosSobreSaltos(threeAddressCodes);
            asignacionBooleanos(threeAddressCodes);
            operacionesConstantes(threeAddressCodes);
            eliminacionCodigoInaccesible(threeAddressCodes);
            asignacionesDiferidas(threeAddressCodes);
            reduccionFuerza(threeAddressCodes);
        }

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
     * 4 - Operaciones constantes
     */
    private void operacionesConstantes(ArrayList<ThreeAddressCode> threeAddressCodes) {

        for (ThreeAddressCode tAC : threeAddressCodes) {

            //Comprobar si es una operación arimética
            if (tAC.getOperation().equals("PLUS") || tAC.getOperation().equals("MINUS") ||
                    tAC.getOperation().equals("MULTI") || tAC.getOperation().equals("DIV")) {

                //Ambos operandos tienen que ser variables temporales
                if (!tAC.getOperand1().contains("$t#") || !tAC.getOperand2().contains("$t#"))
                    continue;

                //Leer las dos variables de la tabla de variables
                Variable variable1 = variableTable.get(tAC.getOperand1());
                Variable variable2 = variableTable.get(tAC.getOperand2());

                //Ambos operandos tienen que tener valor definido
                if (variable1.getValue() == null || variable2.getValue() == null)
                    continue;

                //Buscar los dos COPY_LITERAL de los dos operandos
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

                //Comprobar que se han encontrado las dos posiciones
                if (posCopyLiteral1 == -1 || posCopyLiteral2 == -1)
                    continue;

                //Eliminar primero la instrucción que está en una posición más alta
                if (posCopyLiteral1 > posCopyLiteral2) {
                    int swap = posCopyLiteral2;
                    posCopyLiteral2 = posCopyLiteral1;
                    posCopyLiteral1 = swap;
                }

                //Eliminar los dos COPY_LITERAL de códio de tres direcciones
                threeAddressCodes.remove(posCopyLiteral2);
                threeAddressCodes.remove(posCopyLiteral1);

                int result = 0;

                switch (tAC.getOperation()) {
                    case "PLUS":
                        result = Integer.parseInt(variable1.getValue()) + Integer.parseInt(variable2.getValue());
                        break;
                    case "MINUS":
                        result = Integer.parseInt(variable1.getValue()) - Integer.parseInt(variable2.getValue());
                        break;
                    case "MULTI":
                        result = Integer.parseInt(variable1.getValue()) * Integer.parseInt(variable2.getValue());
                        break;
                    case "DIV":
                        result = Integer.parseInt(variable1.getValue()) / Integer.parseInt(variable2.getValue());
                        break;
                }

                System.out.println("Resultado de la operacion " + tAC.getOperation() + " = " + result);

                //Cambiar instrucción de aritmética a COPY_LITERAL con el valor ya calculado
                tAC.setOperation("COPY_LITERAL");
                tAC.setOperand1(String.valueOf(result));
                tAC.setOperand2(null);

                //Actualizar valor de destino en la tabla de variables
                Variable destino = variableTable.get(tAC.getDestination());
                destino.setValue(String.valueOf(result));

                //Eliminar variables temporales de la tabla de variables
                variableTable.remove(tAC.getOperand1());
                variableTable.remove(tAC.getOperand2());

                Output.writeInfo("Aplicada optimización de operaciones constantes");

                //Hay cambios
                cambios = true;
                optimizations++;
                break;

            }
        }
    }

    /**
     * 5 - Eliminacion de código inaccesible
     */
    private void eliminacionCodigoInaccesible(ArrayList<ThreeAddressCode> threeAddressCodes) {
        for (int i = 0; i < threeAddressCodes.size(); i++) {
            ThreeAddressCode tAC = threeAddressCodes.get(i);

            //Comprobar si es una operación condicional
            if (tAC.getOperation().equals("IFGOTO")) {

                //Leer la variable que contiene la condicion
                Variable variableCond = variableTable.get(tAC.getOperand1());

                String condicion = variableCond.getValue();

                //Si no tiene valor, ignorarla
                if (condicion == null)
                    continue;

                boolean condIsTrue = condicion.equals("true") || condicion.equals("True") || condicion.equals("1");
                boolean condIsFalse = condicion.equals("false") || condicion.equals("False") || condicion.equals("0");

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
                for (int k = end; k > init - 1; k--)
                    threeAddressCodes.remove(k);

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

                //Hay cambios
                cambios = true;
                optimizations++;
                break;
            }
        }
    }

    /**
     * 8 - Asignaciones diferidas
     */
    private void asignacionesDiferidas(ArrayList<ThreeAddressCode> threeAddressCodes) {
        for (int i = 0; i < threeAddressCodes.size(); i++) {
            ThreeAddressCode tAC = threeAddressCodes.get(i);

            //Buscamos operación COPY_LITERAL con variables temporales
            if (!tAC.getOperation().equals("COPY_LITERAL") || !tAC.getDestination().contains("$t#"))
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

                    //Eliminar variable temporal de la tabla de variables
                    variableTable.remove(tAC.getDestination());

                    //Eliminar COPY_LITERAL original
                    threeAddressCodes.remove(i);

                    Output.writeInfo("Aplicada optimización de asignaciones diferidas");

                    //Hay cambios
                    cambios = true;
                    optimizations++;
                    break;
                }
            }
        }
    }

    /**
     * 9 - Reducció de fuerza
     */
    private void reduccionFuerza(ArrayList<ThreeAddressCode> threeAddressCodes) {

    }
}
