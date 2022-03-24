# Fuel Price API

Fuel Price API is based on Spring boot framework. It is an application that tracks the prices of different fuels in gas stations and cities across the country. Some of it's features are based of the web site `fuelo.net`.

The application gives to the user the ability to add new cities, gas stations related to them and different fuels in each gas station. On every price change, the old price is saved, so the differences can be easily tracked down.

## Tech stack
 - Spring Boot Framework v2.6.4
 - Hibernate ORM
 - Lombok (used for quicker code scaffolding)
 - MySQL Database backend
 - springdoc-openapi-ui (for Swagger documentation)