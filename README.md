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
