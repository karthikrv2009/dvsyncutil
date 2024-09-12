package com.dvsynchutil.dvsynchutil.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dvsynchutil.dvsynchutil.dto.TableSchema;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SQLGenerator {

    public String generateSQL(String tableName, String operation, Map<String, String> data, List<String> columns, String primaryKeyColumn) {
    StringBuilder sql = new StringBuilder();

    switch (operation.toUpperCase()) {
        case "INSERT":
            sql.append("INSERT INTO ").append(tableName).append(" (");
            columns.forEach(col -> sql.append(col).append(", "));
            sql.setLength(sql.length() - 2); // Remove the last comma
            sql.append(") VALUES (");
            columns.forEach(col -> sql.append("'").append(data.get(col)).append("', "));
            sql.setLength(sql.length() - 2); // Remove the last comma
            sql.append(");");
            break;

        case "UPDATE":
            sql.append("UPDATE ").append(tableName).append(" SET ");
            columns.forEach(col -> sql.append(col).append(" = '").append(data.get(col)).append("', "));
            sql.setLength(sql.length() - 2); // Remove the last comma
            sql.append(" WHERE ").append(primaryKeyColumn).append(" = '").append(data.get(primaryKeyColumn)).append("';");
            break;

        case "DELETE":
            sql.append("DELETE FROM ").append(tableName).append(" WHERE ").append(primaryKeyColumn).append(" = '").append(data.get(primaryKeyColumn)).append("';");
            break;

        default:
            throw new IllegalArgumentException("Unsupported operation: " + operation);
    }

    return sql.toString();
}


    public List<TableSchema> parseModelSchema(JsonNode modelJson) {
    List<TableSchema> tableSchemas = new ArrayList<>();

    // Assuming the model JSON has an array of tables
    JsonNode tables = modelJson.get("tables");
    if (tables != null && tables.isArray()) {
        for (JsonNode table : tables) {
            String tableName = table.get("tableName").asText();
            String primaryKey = null;
            List<String> columns = new ArrayList<>();

            // Fetch the columns and primary key
            JsonNode fields = table.get("fields");
            if (fields != null && fields.isArray()) {
                for (JsonNode field : fields) {
                    String columnName = field.get("name").asText();
                    columns.add(columnName);

                    // Check if it's the primary key
                    if (field.get("isPrimaryKey").asBoolean()) {
                        primaryKey = columnName;
                    }
                }
            }

            // Create TableSchema object and add it to the list
            tableSchemas.add(new TableSchema(tableName, primaryKey, columns));
        }
    }
    return tableSchemas;
}


    public String getOperationFromLog(String logLine) {
    String[] logParts = logLine.split(" \\| ");
    if (logParts.length >= 2) {
        return logParts[1];  // The second part of the log is the operation
    }
    return null;  // Return null if the log format is invalid
    }


public String getTableNameFromLog(String logLine) {
    String[] logParts = logLine.split(" \\| ");
    if (logParts.length >= 3) {
        return logParts[2];  // The third part of the log is the table name
    }
    return null;  // Return null if the log format is invalid
    }
    
    public Map<String, String> getDataFromLog(String logEntry) {
    // Split the log entry using the " | " delimiter
    String[] logParts = logEntry.split(" \\| ");
    
    // The data part is expected to be the fourth part
    if (logParts.length >= 4) {
        String jsonData = logParts[3].trim();
        
        // Parse the JSON data into a Map<String, String>
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> dataMap = mapper.readValue(jsonData, new TypeReference<Map<String, String>>() {});
            return dataMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Return an empty map if parsing fails or log format is incorrect
    return new HashMap<>();
}

   
}
