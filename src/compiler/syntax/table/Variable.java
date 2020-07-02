package compiler.syntax.table;

public class Variable {

    public int variableSize;
    public Subtype subtype;

    public Variable(int ocup, Subtype ts) {
        this.variableSize = ocup;
        this.subtype = ts;
    }

    public int getVariableSize() {
        return variableSize;
    }

    public void setVariableSize(int variableSize) {
        this.variableSize = variableSize;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }
}