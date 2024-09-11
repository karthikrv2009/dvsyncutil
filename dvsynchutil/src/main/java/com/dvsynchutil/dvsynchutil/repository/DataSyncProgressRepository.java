package com.dvsynchutil.dvsynchutil.repository;

import com.dvsynchutil.dvsynchutil.entity.DataSyncProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSyncProgressRepository extends JpaRepository<DataSyncProgress, Long> {
}