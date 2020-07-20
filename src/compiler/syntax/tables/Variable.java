package compiler.syntax.tables;

public class Variable {

    private static int numVar = 0;

    private int size;
    private int deep;
    private int numVariable;
    private String id;
    private String parentFunction;
    private String value;

    private Type type;
    private Subtype subtype;

    public Variable(String id, boolean count) {
        this.id = id;

        if (count)
            this.numVariable = ++numVar;
    }

    public Variable(String id, String parentFunction, boolean count) {

        String prefix = (parentFunction == null) ? "global" : parentFunction;

        this.id = prefix + "$" + id;
        this.parentFunction = parentFunction;

        if (count)
            this.numVariable = ++numVar;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getNumVariable() {
        return numVariable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentFunction() {
        return parentFunction;
    }

    public void setParentFunction(String parentFunction) {
        this.parentFunction = parentFunction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }
}