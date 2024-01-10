# Data Warehouse Service

### Application Status

![example workflow](https://github.com/engrceey/Data-warehouse-migration/actions/workflows/maven.yml/badge.svg)

## Introduction
This service consist of three endpoint whose functions are
* to upload deal records in csv/excel format
* to upload deal record in Json rest data format
* to fetch deals in paginated format

### Validations Added
* Only unique Deal data is persisted
* Validates uploaded file type
* Validates DateTime format (MM/dd/yyyy HH:mm:ss)
* No rollback if data already exist

### Sample test csv is available in the resource folder of this service

### How to Run Locally
Have docker running on your machine and run docker-compose up
**Swagger Documentation** available at :: http://localhost:9001/swagger-ui/index.html

### Variables
JDBC_DATASOURCE_URL = jdbc:mysql://localhost:3306/sample_db
JDBC_DATASOURCE_USERNAME = root
JDBC_DATASOURCE_PASSWORD = zurum-mysql


### Technology Used:
* Java 21
* SpringBoot
* MySql
* Swagger Docs
* Docker
* CSV file reader (apache.commons csv)

#### Images


![Screenshot 2024-01-10 at 00 54 55 (2)](https://github.com/engrceey/Data-warehouse-migration/assets/50442301/a415ccd3-aafb-4c55-b3a5-00fd635b219f)


![Screenshot 2024-01-10 at 00 58 26 (2)](https://github.com/engrceey/Data-warehouse-migration/assets/50442301/f7d72c54-f6fc-437f-b348-e09eba135d8d)

![Uploading Screenshot 2024-01-10 at 00.58.44 (2).png…]()




