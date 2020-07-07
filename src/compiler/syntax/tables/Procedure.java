package compiler.syntax.tables;

public class Procedure {

    public static int numProc = 0;
    public int labStart;
    public int numParams;
    public int size;
    public int deep;

    public int getLabStart() {
        return labStart;
    }

    public void setLabStart(int labStart) {
        this.labStart = labStart;
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

    public static String nextProcedure() {
        return "fun" + ++numProc;
    }
}
