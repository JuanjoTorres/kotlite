package compiler.syntax.tables;

public class Procedure {

    private static int NUM_PROC = 0;

    private String startLabel;
    private int numParams;
    private int size;
    private int numProcedure;
    private int deep;

    public Procedure(String name) {
        this.startLabel = Procedure.nextProcedure(name);
        this.numProcedure = ++NUM_PROC;
        this.deep = 1;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public int getNumParams() {
        return numParams;
    }

    public void setNumParams(int numParams) {
        this.numParams = numParams;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDeep() {
        return deep;
    }

    public static String nextProcedure(String funName) {
        return "fun#" + funName;
    }


}
