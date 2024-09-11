package com.dvsynchutil.dvsynchutil.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "data_sync_progress")

public class DataSyncProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partition;
    private String tableName;
    private int copyStatus;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(int copyStatus) {
        this.copyStatus = copyStatus;
    }
}
