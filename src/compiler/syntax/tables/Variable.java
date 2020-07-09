package compiler.syntax.tables;

public class Variable {

    private static int NUMVAR = 0;

    private String id;

    private int size;

    private boolean global;

    private String parentFunction;

    private Subtype subtype;

    public Variable() {
        this.id = nextVariable();
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

    public String getId() {
        return id;
    }

    private static String nextVariable() {
        return "t" + ++NUMVAR;
    }
}