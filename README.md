# Data Deduplication
Data Deduplication is a JAVA project that splits data using hashing technique and removes duplicate blocks to save cloud storage. This project also uses CloudSim framework for cloud storage simulation.

### Pre-requisites
1. JAVA JDK1.8
2. Netbeans IDE 8.2
3. MySQL Server
4. phpMyAdmin

### How to run?
1. Clone/download this repo.
2. Open project into the Netbeans IDE.
3. Run phpMyAdmin and create a database "data_deduplication" and import `data_deduplication.sql` file (this file is located into the db folder).
4. Now, run the `DataDeduplication.java` file and login using "admin" as an username and password.
5. Try uploding and downloading files.

**Note:** Update ***tempFilePath*** and ***outputFilePath*** into the `CommonProperties.java` file as per your directory structure and you can also change split chunk size from this file.
