# restaurant-frontend-service
Frond end service application integrating to show the list of RESTAURANTS in Open, Closed status

The back-end service requires Java 11. The code is in the ``src/main/java`` directory.

### How to build and execute the back-end?

The docker compose for the project is present in the path:
docker/docker-compose.yml

- Run the following on the command line: (Mac OS)
```bash
cd PATH/TO/PROJECT
./mvnw clean spring-boot:run

```
- Run the following on the command line: (Windows)
```bash
cd PATH/TO/PROJECT
docker-compose up
mvnw.cmd clean spring-boot:run
```
- __Or__ from Intellij IDEA (during the development process): Run spring application (manually selecting in the run menu or just clicking on the play button next to the main method inside the ``de.idealo.international.reservation.Application`` class)

## Technologies that have been used

- [Spring Framework](https://spring.io/projects/spring-framework): Java based enterprise application framework
- [Spring Boot](https://spring.io/projects/spring-boot): Spring based web application framework
- [Maven](https://maven.apache.org/): Software project management and comprehension tool
- [KAFKA](https://kafka.apache.org/): Open-source distributed event streaming platform

# API's Exposed
The following REST API endpoints provides control over the source of truth in JUST EAT Takeaway regarding the restaurant’s open state

- __GET__

    - __/restaurant/__  
      A read endpoint which get an optional status as request param and return the list of the restaurant from DB.

- __POST__

    - __/restaurant__  
      An endpoint for creating a restaurant.
      The input to the API is a list of restaurant details:
        - __name__: Name of the restaurant.
        - __status__: Restaurant Status (OPEN/CLOSE).

- __PUT__

    - __/restaurant/{id}__  
      A update endpoint which update the details of a restaurant based on the restaurant id.
    
## Explanation and thoughts
The KAFKA consumer will be listening to a topic called 'UPDATE-STATUS' which is being produced by the OPEN-CLOSE micro-service.
Whenever the event is emitted from the PRODUCER(OPEN-CLOSE micro-service), the FE service consumer gets invoked, parses the data, and updates the status of restaurant based on the restaurant id.