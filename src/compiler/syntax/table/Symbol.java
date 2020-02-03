package compiler.syntax.table;

public class Symbol {

    // Attributes
    private String id;
    private Type type;
    private Subtype subtype;
    private int deep;

    // Constructor
    public Symbol(String id, Type type, Subtype subtype, int deep) {

        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.deep = deep;
    }

    // Methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    // Enums Type & Subtype
    public enum Type {
        VAR, CONST, PROC, ARG, NONE;
    }

    public enum Subtype {
        BOOLEAN, STRING, INT, NULL;
    }
}
