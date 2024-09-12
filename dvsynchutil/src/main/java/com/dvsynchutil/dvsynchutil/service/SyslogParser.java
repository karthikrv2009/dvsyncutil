package com.dvsynchutil.dvsynchutil.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dvsynchutil.dvsynchutil.dto.Partition;

@Service
public class SyslogParser {


   // Parse the syslog file and extract table partitions with matching lines
    public List<Partition> parseSyslog(File log) throws IOException {
        List<Partition> partitions = new ArrayList<>();
        int partitionIdCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(log))) {
            String line;

            // Read each line and look for INSERT, UPDATE, or DELETE statements
            while ((line = br.readLine()) != null) {
                if (line.contains("INSERT INTO") || line.contains("UPDATE") || line.contains("DELETE FROM")) {
                    String tableName = extractTableName(line);

                    // Check if a partition already exists for the table
                    Partition existingPartition = findPartitionByTableName(partitions, tableName);
                    if (existingPartition != null) {
                        // Update existing partition by adding the current line
                        existingPartition.addLine(line);
                    } else {
                        // Create a new Partition for the new table name
                        Partition newPartition = new Partition(tableName, partitionIdCounter++, 0); // Initially set status to 0 (not processed)
                        newPartition.addLine(line); // Add the current line to the new partition
                        partitions.add(newPartition); // Add the new partition to the list
                    }
                }
            }
        }

        return partitions;
    }


    // Helper method to find a partition by table name from the list of partitions
    private Partition findPartitionByTableName(List<Partition> partitions, String tableName) {
        for (Partition partition : partitions) {
            if (partition.getTableName().equals(tableName)) {
                return partition;
            }
        }
        return null; // Return null if no matching partition is found
    }


    // Extract table name from SQL operation in the log file
    private String extractTableName(String logLine) {
        if (logLine.contains("INSERT INTO")) {
            return logLine.split("INSERT INTO")[1].split(" ")[1].trim();
        } else if (logLine.contains("UPDATE")) {
            return logLine.split("UPDATE")[1].split(" ")[1].trim();
        } else if (logLine.contains("DELETE FROM")) {
            return logLine.split("DELETE FROM")[1].split(" ")[1].trim();
        }
        return "unknown_table"; // fallback if not identified
    }
}
