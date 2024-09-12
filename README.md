# dvsyncutil

# DataSync Utility Microservice

## Overview

The DataSync Utility microservice is a Spring Boot-based application designed for data synchronization across tables using PolyBase with Synapse logs. The microservice processes Synapse logs, partitions them based on table names, and performs efficient data insertion or updates into the tables. The utility uses multi-threading to process data from logs in parallel and maintains the processing status for each partition.

## Features

- Polls for new Synapse log files every 20 seconds from the provided directory path.
- Reads table metadata from a provided `model.json`.
- Inserts, updates, or deletes data in the corresponding database tables based on the change feed log.
- Maintains a `DataSyncProgress` table to track the success or failure of each operation.
- Leverages multi-threading to handle multiple tables in parallel.

## Components

### 1. **Synapse Log Processor**

- Reads the Synapse log file and partitions the data based on table names.
- Handles insert, update, and delete operations for each table.
- Implements error handling to manage retry mechanisms.

### 2. **PolyBase Utility**

- Leverages PolyBase or other mechanisms for bulk loading data from log partitions into the database.
- Executes an `initiateLoad()` function to query the database for the current partition to be loaded.

### 3. **Multi-Threading Support**

- Each table's data is processed in parallel, with a status update for success or failure maintained in the `DataSyncProgress` table.

### 4. **Database Components**

- `DataSyncProgress` Table:
  - Tracks the progress of data synchronization for each table partition.
  - Contains fields such as `Partition`, `TableName`, and `CopyStatus` (0 for pending, 1 for success, and 2 for failure).
- Staging tables for bulk data operations.

## Prerequisites

- **PostgreSQL**: The microservice connects to a PostgreSQL database for table management and data processing.
- **Java 17** or above.
- **Maven**: The project is built using Maven.

## Project Structure

```bash
dvsyncutil/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.dvsynchutil.dvsynchutil/
│   │   │       ├── entity/                 # Contains JPA entities (DataSyncProgress, etc.)
│   │   │       ├── repository/             # Contains Spring Data JPA Repositories
│   │   │       ├── service/                # Contains services for processing logs and PolyBase
│   │   │       ├── config/                 # Contains configuration classes
│   │   │       └── controller/             # Contains REST API Controllers
│   │   └── resources/
│   │       ├── application.properties      # Configuration for database and other properties
│   └── test/                               # Contains unit and integration tests
│
├── target/                                 # Contains built JAR files after running Maven package
└── README.md                               # Documentation file



1. Clone the Repository
git clone https://github.com/karthikrv2009/dvsyncutil.git
cd dvsyncutil/dvsynchutil

2. Build the Project
Make sure you have Maven installed, then build the project using the following command:
mvn clean package

3. Running the Application
Once the JAR is created, you can run the application using:
java -jar target/dvsynchutil-0.0.1-SNAPSHOT.jar

You can also configure the database connection by editing the application.properties file or passing the parameters in the command:
java -jar target/dvsynchutil-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb \
  --spring.datasource.username=yourusername \
  --spring.datasource.password=yourpassword

4. Access the Application
Once the service is up and running, you can access it via the default port 8080:
http://localhost:8080

5. Uploading Files
Use the web interface to upload the model.json and specify the directory where the synapse.log file is located.

6. Monitoring Data Sync Progress
A web interface allows you to monitor the progress of each table.
You can view:
    Total number of partitions processed.
    The success or failure status for each table.
    How many rows were inserted/updated during each sync.


Usage Flow
    Upload the model.json and specify the path for synapse.log.
    The service starts polling the specified path every 20 seconds for new log files.
    Logs are partitioned based on table names, and updates are processed in parallel.
    The service bulk inserts or updates data in staging tables and merges it with the actual table.
    The success or failure of each operation is tracked in the DataSyncProgress table.

```
