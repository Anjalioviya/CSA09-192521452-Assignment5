# Part A – Persistence Design & Pseudocode

## A1 – File format and operations
Text record: `passId|regNo|name|routeNo|boardingStop|validUntil|amountPaid`. Escape delimiter and escape character inside names, or use a standards-compliant quoted CSV parser. Binary DataOutputStream order: writeUTF(passId), writeUTF(regNo), writeUTF(name), writeInt(routeNo), writeUTF(boardingStop), writeLong(validUntilEpochDay), writeDouble(amountPaid). Add validates and appends; Find scans and returns matching ID; List scans and emits matching route. Use BufferedReader/BufferedWriter for character records and try-with-resources. Detect malformed field counts/types, log line numbers and continue. Missing file returns not-found/empty for reads and is created on add. Protect against partial writes with temp-file plus atomic rename or framed records with length/checksum.
### Pseudocode
```
ALGORITHM ADD_PASS(pass)
  VALIDATE all fields
  OPEN BufferedWriter in APPEND mode
  WRITE escaped delimited record; WRITE newline
  FLUSH and CLOSE
END
ALGORITHM FIND_PASS(id)
  IF file does not exist THEN RETURN NOT_FOUND
  OPEN BufferedReader
  WHILE line := READ_LINE is not EOF
    PARSE and VALIDATE line
    IF damaged THEN LOG line number; CONTINUE
    IF record.passId = id THEN CLOSE; RETURN record
  END WHILE
  CLOSE; RETURN NOT_FOUND
END
ALGORITHM LIST_ROUTE(route)
  OPEN BufferedReader
  WHILE line := READ_LINE is not EOF
    PARSE; IF valid AND record.routeNo = route THEN OUTPUT record
  END WHILE
  CLOSE
END
```
RandomAccessFile fixed slots: seek to `i × recordSize` is O(1) if index i is already known; lookup by ID still O(n) without an ID index. Padding wastes `maxFieldLength - actualLength` bytes per field.

## A2 – Buffering
50 MiB byte-at-a-time input performs about 52,428,800 Java read calls and is slow due to per-call overhead. A 64 KiB buffer requires about 800 buffer fills for 50 MiB. Both algorithms are O(N) time; byte-at-a-time auxiliary space O(1), buffered space O(B). `Files.readAllLines()` uses O(N) memory for file contents and line objects, appropriate only when the file fits comfortably in memory. Buffering helps less when data is already memory-buffered or CPU work dominates.

## A3 – DAO pattern
Repeated steps: acquire connection, create/bind SQL, execute, map ResultSet, handle SQLException, close resources. Entity/table, SQL, key and mapping differ. `Dao<T,K>` exposes insert, findById, findAll, update, delete; StudentDao contains Student SQL and mapping. Student fields map to `student(id PRIMARY KEY, name, email)`. DAO centralizes schema changes; embedding SQL in UI/service scatters edits. Per-operation asymptotic time and space are essentially unchanged.

## A4 – Serialization
Mark password and live database Connection transient. Connection is a live external resource; password should not be serialized in plaintext. Serializing Hostel traverses its reachable serializable graph (Rooms and Residents). Every reachable non-transient object must be serializable. Use explicit serialVersionUID. With compatible UID, a newly added field in a newer class is defaulted when reading an older stream; incompatible evolution can cause InvalidClassException. CSV/JSON is more interoperable and easier to migrate but needs explicit parsing; Java serialization is convenient but class-version coupled.

## A5 – Transaction and injection
```
BEGIN
  UPDATE source wallet SET balance=balance-500
    WHERE wallet_id=source AND balance>=500
  IF affected rows != 1 THEN ROLLBACK; RETURN failure
  UPDATE destination wallet SET balance=balance+500 WHERE wallet_id=destination
  IF affected rows != 1 THEN ROLLBACK; RETURN failure
COMMIT
ON SQLException: ROLLBACK and report
```
String concatenation permits input such as `' OR '1'='1' -- ` to alter SQL logic. PreparedStatement binds id and password as values, not SQL syntax. Two separate auto-commit statements can debit without credit after a mid-way failure. A transaction adds commit/rollback overhead but preserves atomicity. Indexed updates are typically O(log n) in a B-tree index.
