package com.dvsynchutil.dvsynchutil.service;

import com.dvsynchutil.dvsynchutil.dto.TableSchema;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SchemaParser {

    public static List<TableSchema> parseModelSchema(String filePath) {
        List<TableSchema> tableSchemas = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(filePath));
            JsonNode tables = root.get("tables");

            if (tables != null && tables.isArray()) {
                for (JsonNode tableNode : tables) {
                    String tableName = tableNode.get("name").asText();
                    List<String> columns = new ArrayList<>();
                    JsonNode columnArray = tableNode.get("columns");

                    if (columnArray != null && columnArray.isArray()) {
                        for (JsonNode columnNode : columnArray) {
                            columns.add(columnNode.asText());
                        }
                    }

                    // Assume primary key is the first column (or you can define logic to determine the primary key)
                    String primaryKey = columns.isEmpty() ? null : columns.get(0);

                    // Create a TableSchema object
                    TableSchema tableSchema = new TableSchema(tableName, primaryKey, columns);
                    tableSchemas.add(tableSchema);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableSchemas;
    }
}

