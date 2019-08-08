# Customer Web App - Spring REST and Hibernate

This is a project of Spring, Spring Web (REST), Hibernate and MySQL.
It consist of a Customer Relationship Management (CRM) project with the CRUD operations. Also this app recognizes the:

* GET
* POST
* PUT
* DELETE

The response is automatically converted to JSON using Jackson's Project.
It also follows the [REST API Naming Conventions](https://restfulapi.net/resource-naming/)

<br />
This project is for purely academic purposes.

### Routes

*  **GET**         /api/customers           <br>
*  **GET**         /api/customers/{id}      <br>
*  **POST**        /api/customers           <br>
*  **DELETE**      /api/customers/{id}      <br>
*  **PUT**         /api/customers/{id}      <br>


#### MySQL Database

This project was built using MySQL 8.0, so you will need to have it installed in order to it function.

The MySQL scripts used to build the database can be found in the SQL Scripts folder in the root directory.
You can execute them with a administrator users. They will come with some mock up users.

The <b>JDBC Driver for MySQL</b> also needs to be included into the project or pom.xml in so it can establish a 
connection with the database. 
This process may change based on your IDE.

## Built With

* [MySQL DB](https://www.mysql.com/products/community/) - 8.0
* [Java](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) - 8
* [Hibernate ORM](http://hibernate.org/orm/) - 5.4.4
* [Spring](https://spring.io/) - 5.1.9.RELEASE
* [Jackson's Project](https://github.com/FasterXML/jackson) - 2.9.9.2