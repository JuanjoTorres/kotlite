package compiler.syntax.table;

public class Variable {

    public static int numVar = 0;

    public int size;
    public Subtype subtype;

    public Variable(int size, Subtype subtype) {
        this.size = size;
        this.subtype = subtype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }

    public static String nextVariable() {
        return "t" + ++numVar;
    }
}