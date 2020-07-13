package compiler.syntax.tables;

import compiler.intermediate.Generator;

public class Variable {

    private int size;
    private boolean global;
    private String id;
    private String parentFunction;

    private Subtype subtype;

    public Variable(String id) {
        this.id = id;
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

}