package com.dvsynchutil.dvsynchutil.dto;

import java.util.List;

public class TableSchema {
    private String tableName;
    private String primaryKey;
    private List<String> columns;

    public TableSchema(String tableName, String primaryKey, List<String> columns) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.columns = columns;
    }

    // Getters and Setters
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TableSchema{" +
                "tableName='" + tableName + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", columns=" + columns +
                '}';
    }
}

