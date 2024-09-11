package com.dvsynchutil.dvsynchutil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvsynchutil.dvsynchutil.entity.DataSyncProgress;

public interface DataSyncProgressRepository extends JpaRepository<DataSyncProgress, Long> {
}