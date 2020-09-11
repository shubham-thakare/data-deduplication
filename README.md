# Data Deduplication
Data Deduplication is a JAVA project that splits data using hashing technique and removes duplicate blocks to save cloud storage. This project also uses CloudSim framework for cloud storage simulation.

### Pre-requisites
1. JAVA JDK1.8
2. Netbeans IDE 8.2
3. MySQL Server
4. phpMyAdmin
5. cloudsim-3.0.3.jar
6. mysql-connector-java-3.1.14-bin.jar

### How to run?
1. Clone/download this repo.
2. Open [Data Deduplication](/Data%20Deduplication) project into the Netbeans IDE.
3. Import `cloudsim-3.0.3.jar` and `mysql-connector-java-3.1.14-bin.jar` into the project.
4. Run phpMyAdmin and create a database "data_deduplication" and import [`data_deduplication.sql`](/db/data_deduplication.sql) file.
5. Now, run the [`DataDeduplication.java`](/Data%20Deduplication/src/data/deduplication/DataDeduplication.java) file and login using "admin" as an username and password.
6. Try uploding and downloading files.

> Screenshots are also available [here](/Screenshots).

**Note:** Update [***tempFilePath***](/Data%20Deduplication/src/utils/CommonProperties.java#L4) and [***outputFilePath***](/Data%20Deduplication/src/utils/CommonProperties.java#L5) from the [`CommonProperties.java`](/Data%20Deduplication/src/utils/CommonProperties.java) file as per your directory structure and you can also change split [chunk](/Data%20Deduplication/src/utils/CommonProperties.java#L6) size from this file.
