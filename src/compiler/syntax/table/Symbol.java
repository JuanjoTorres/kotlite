package compiler.syntax.table;

import java.util.ArrayList;

public class Symbol {

    // Attributes
    private String id;
    private Type type;
    private Subtype subtype;
    private ArrayList<Symbol> args = new ArrayList<>();

    // Constructor
    public Symbol(String id, Type type, Subtype subtype) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
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

    public ArrayList<Symbol> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", subtype=" + subtype +
                ", args=" + args +
                '}';
    }
}
