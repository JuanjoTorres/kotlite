package compiler.syntax.tables;

public class Procedure {

    public static int NUMPROC = 0;
    public String startLabel;
    public int numParams;
    public int size;
    public int deep;

    public Procedure(String name) {
        this.startLabel = Procedure.nextProcedure(name);
    }

    public String getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(String startLabel) {
        this.startLabel = startLabel;
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

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public static String nextProcedure(String funName) {
        return "fun_" + funName + "_" + ++NUMPROC;
    }
}
