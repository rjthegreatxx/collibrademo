# Demo

### Testing

Database and webTestClient tests can be run.  I've also attached a Postman collection to test the controller.

### Setup
Local Redis running on port 6379

Main application should be running when running integration tests

Gradle:

```gradle build -x test```

```gradle bootRun```

Docker:

```docker build -t collibrademo:latest .```

```docker run -dp 8080:8080 collibrademo```

(Docker networking may have to be adjusted when running against a containerized version of Redis)


### TODO

OpenAPI

### Assessment

Consider a metadata system that persists “asset” information. A sample format of some assets:

    Asset A (contains)
    
        Asset B (contains)
    
            Asset D (contains)
    
                Asset F
    
                Asset G
    
            Asset E (contains)
    
                Asset H
    
                Asset I
    
        Asset C (contains)
    
            Asset J (contains)
    
                Asset K
    
                Asset L



### Requirements:

Create a RESTful microservice with endpoints that allows you to manage assets and perform promotion of those assets. This microservice should be capable of the following:

Create, Read, Update, Delete assets.

Use of a version control system (preferably Github).

We would need to be able to provide a way to “promote” an asset. When an asset is promoted:

A new system is informed of the promotion.

The assets that are nested under that asset and its ancestors are also promoted.

The promoted assets are marked as promoted.

Follow REST standards

Logging

JUnit 5 tested code

Full coverage is not necessary. Do enough to show you can write thoughtful/thorough test cases.



### Nice to Haves:

Although not required the following would be nice to have in your solution to express your ability level of technologies/practices we abide by amongst our team:

Containerized solution

Spring based application

Integration test from controller to database layer

Use of Java 17(+)

Use OpenAPI, one of the following approaches:

API First, use OpenAPI doc to generate code stubs. This approach is currently used by all of Collibra.

Code First, use OpenAPI annotations in code to generate OpenAPI doc.

Use of persistent RDBMS or NOSQL store

Use of an eventing platform
