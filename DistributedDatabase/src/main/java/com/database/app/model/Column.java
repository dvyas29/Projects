package com.database.app.model;

public class Column {

    private final String name;

    private final String type;

    private final String constraint;

    private final int index;

    private final String foreignKeyTable;

    private final String foreignKeyColumn;

    public Column(String name, String type, String constraint, int index, String foreignKeyTable, String foreignKeyColumn) {
        this.name = name;
        this.type = type;
        this.constraint = constraint;
        this.index = index;
        this.foreignKeyTable = foreignKeyTable;
        this.foreignKeyColumn = foreignKeyColumn;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getConstraint() {
        return constraint;
    }

    public int getIndex() {
        return index;
    }

    public String getForeignKeyTable() {
        return foreignKeyTable;
    }

    public String getForeignKeyColumn() {
        return foreignKeyColumn;
    }

}
