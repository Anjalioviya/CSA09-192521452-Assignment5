# CSA09 – Assignment 5: Data Persistence Engineering

**Register number:** 192521452  
**Student name:** Anjali Oviya  
**Java:** 21  
**Build:** Maven

## Project contents

- `src/main/java/assignment5/` – Java implementations and demonstrations.
- `sql/schema.sql` – MySQL database and table setup.
- `docs/PartA_Design_and_Pseudocode.md` – Part A design notes and pseudocode.
- `data/README.txt` – generated test-data notes.
- `outputs/README.txt` – output-evidence folder notes.
- `pom.xml` – Maven configuration.

## Requirements

- JDK 21
- Apache Maven 3.9+
- MySQL Server 8.x

## Build

```bash
mvn clean compile
```

## Configure database access

Set these environment variables in the same Terminal session. Do not commit credentials or put real passwords in source files.

```bash
export DB_URL="jdbc:mysql://localhost:3306/assignment5"
export DB_USER="root"
export DB_PASSWORD="YOUR_MYSQL_PASSWORD"
```

Create the database schema from the project root in the MySQL client:

```sql
SOURCE sql/schema.sql;
```

## Run demonstrations

```bash
mvn -q exec:java -Dexec.mainClass=assignment5.PassStore
mvn -q exec:java -Dexec.mainClass=assignment5.BufferBenchmark
mvn -q exec:java -Dexec.mainClass=assignment5.PassGrowthBenchmark
mvn -q exec:java -Dexec.mainClass=assignment5.DaoDemo
mvn -q exec:java -Dexec.mainClass=assignment5.SerializationDemo
mvn -q exec:java -Dexec.mainClass=assignment5.AdversarialLogin
mvn -q exec:java -Dexec.mainClass=assignment5.TransactionDemo
mvn -q exec:java -Dexec.mainClass=assignment5.DbTasks
mvn -q exec:java -Dexec.mainClass=assignment5.MigrationDemo
```

The database-dependent demonstrations require the MySQL schema and environment variables above. Benchmark runs create generated files under `data/`; those generated files are excluded from Git by `.gitignore`.

## Evidence and submission note

Place genuine screenshots of your own execution in `outputs/` before submission. Include the required test inputs, expected-versus-actual results, measurement interpretation, and any additional evidence specified by your course brief. Verify the final submission against the official assignment instructions.
