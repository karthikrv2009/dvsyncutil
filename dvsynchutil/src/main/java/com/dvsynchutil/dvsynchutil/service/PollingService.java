package com.dvsynchutil.dvsynchutil.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dvsynchutil.dvsynchutil.dto.Partition;
import com.dvsynchutil.dvsynchutil.dto.TableSchema;
import com.dvsynchutil.dvsynchutil.repository.DataSyncProgressRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PollingService {

    private String modelPath;
    private String logPath;
    private boolean processing = false;

    @Autowired
    private SyslogParser syslogParser;

    @Autowired
    private DataSyncProgressRepository dataSyncProgressRepository;

    @Autowired
    private SchemaParser schemaParser;

    @Autowired
    private SQLGenerator sqlGenerator; 

    // Set the paths from the user upload
    public void setPaths(String modelPath, String logPath) {
        this.modelPath = modelPath;
        this.logPath = logPath;
    }

    // Poll the log path every 20 seconds
    @Scheduled(fixedRate = 20000)
    public void pollForLogs() {
        if (processing || logPath == null) return;

        processing = true;
        try {
            File logDir = new File(logPath);
            if (logDir.exists() && logDir.isDirectory()) {
                File[] logs = logDir.listFiles((dir, name) -> name.endsWith(".log"));
                if (logs != null && logs.length > 0) {
                    // Process the log files
                    for (File log : logs) {
                        System.out.println("Processing file: " + log.getName());
                        // Call your Polybase utility to process the log files
                        try {
                           List<Partition> partitions=syslogParser.parseSyslog(log);
                           List<TableSchema> modelJson=schemaParser.parseModelSchema(modelPath);
                           // Map<String, String> data, List<String> columns, String primaryKeyColumn
                          String Operation=null; 
                          Map<String, String> data=null;TableSchema currentSchema=null;
                          for(Partition iter : partitions){
                            for(TableSchema t : modelJson){
                                if(iter.getTableName().equals(t.getTableName())){
                                currentSchema=t;
                                }

                            }
                            for(String i : iter.getMatchingLines()){
                                Operation=sqlGenerator.getOperationFromLog(i);
                                data=sqlGenerator.getDataFromLog(i);
                                sqlGenerator.generateSQL(iter.getTableName(), Operation, data, currentSchema.getColumns(), logPath);
                            }
                          }
                          // dataSyncProgressRepository.saveAll(partitions);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        processLogFile(log);
                    }
                }
            }
        } finally {
            processing = false;
        }
    }

    // Simulated method for processing the log file
    private void processLogFile(File log) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        // Here you can call your Java code to process partitions in parallel
        // Use multi-threading to process partitions in parallel.
        // After processing, update the DataSyncProgress table with success or failure status.
    }

    // Sample method to get the number of tables created and their columns
    public String getTables() {
        // Simulate reading from the database
        return "Tables and columns displayed here";
    }

    // Sample method to get partition status
    public String getPartitionStatus() {
        // Simulate reading from the database
        return "Partition status displayed here";
    }
}

