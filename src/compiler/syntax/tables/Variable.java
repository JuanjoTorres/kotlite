package compiler.syntax.tables;

public class Variable {

    private int size;
    private boolean global;
    private String id;
    private String parentFunction;
    private String value;

    private Type type;
    private Subtype subtype;

    public Variable(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
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