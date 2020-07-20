package compiler.intermediate;

import compiler.syntax.tables.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Optimizer {


    private HashMap<String, Variable> variableTable;

    /**
     * Constructor sin parámetros
     */
    public Optimizer() {
        this.variableTable = VariableTable.getTable();

        ArrayList<ThreeAddressCode> threeAddressCodes = Generator.getThreeAddressCodes();
    }



}
