package com.dvsynchutil.dvsynchutil.dto;

import java.util.ArrayList;
import java.util.List;

public class Partition {
    private String tableName;
    private int partitionId;
    private int status;
    private List<String> matchingLines;

    public Partition(String tableName, int partitionId, int status) {
        this.tableName = tableName;
        this.partitionId = partitionId;
        this.status = status;
        this.matchingLines = new ArrayList<>(); // Initialize the list for matching lines
    }

    public String getTableName() {
        return tableName;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getMatchingLines() {
        return matchingLines;
    }

    public void addLine(String line) {
        matchingLines.add(line);
    }
}
