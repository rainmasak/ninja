# Assignment project

##### Backend:
  - Spring boot ver. 2.0.4
  - Maven ver. 3.5.4 
  - Java ver. 1.8
  - H2 embedded database

##### Client:
  - JavaScript/jQuery

##### Features:
  - Upload image files
  - View uploaded images
  - Delete uploaded image files

##### Additional features:
  - Check for maximum filesize of 3 mb
  - Check for existing files (filename)
  - Highlight existing filename upon uploading if already exists in database
  - Display success/error messages

#### Installation
```sh
git clone https://github.com/rainmasak/ninja.git
cd ninja
mvn spring-boot:run
```
Application visible at: http://localhost:8080/

#### Database testing
  - http://localhost:8080/h2-console/ (local Tomcat)
  - http://ninja.local.pcfdev.io/h2-console/ (CF)
  - Driver class: org.h2.Driver
  - JDBC Url: jdbc:h2:mem:testdb
  - Username: sa
  - Password: *none*

### Cloud Foundry
Included sample manifest.yml
```sh
cd ninja
mvn install
cf push assignment
```