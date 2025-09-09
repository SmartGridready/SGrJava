# SGrJava

## Index

[Summary](#summary)  
[Description](#description)  
[Build and publish for development](#build-and-publish-for-development)

## Summary

SGrJava is home for the SmartGridready core components, the communication handler implementations.
Current list of interfaces supported by the communication handler:

- Modbus
- REST APIs
- MQTT

A communication handler maps the SmartGridready generic interface to a device/product specific interface. The interface mapping is defined within an external interface description in XML. See <https://github.com/SmartGridready/SGrSpecifications/tree/master/XMLInstances/ExtInterfaces>

The external interface description XML (EI-XML) describes for every functionalprofile-data-point tuple the properties needed to read/write date from/to the data-point.

## Description

The SGrJava repository contains of three projects:

### Specification

Provides JAXB generated classes based on the XML schema definitions within the SGrSpecifications GitHub repository.  
[JavaDoc](https://smartgridready.github.io/SGrJava/docs/specification/)

### GenDriverAPI

API used to integrate different device drivers for Modbus, REST and MQTT.  
[JavaDoc](https://smartgridready.github.io/SGrJava/docs/gen-driver-api/)

### CommHandler

Code that interprets the EI-XML and adapts the communication interface to the device communication.  
[JavaDoc](https://smartgridready.github.io/SGrJava/docs/commhandler/)

## Build and Publish for Development

### Prerequisites

1. Java JDK version >= 11

2. **To build Specification if required:** Clone the SGrSpecifications repository <https://github.com/SmartGridready/SGrSpecifications/> in  parallel to the SGrJava repository. This is required that the `Specification` project finds the XML-Schema files needed to generate the JAXB classes.

```text
Required folder structure:

sgr-projects-root
 |_SGrJava
 |_SGrSpecifications
```

### Run the gradle build and publish to the local maven repository

Each project can be built separately:

- To build the project run in the project root of the respective project (CommHandler, SGrSpecification, GenDriverAPI):

    ```bash
    ./gradlew clean build
    ```

- To publish to the local Maven repository run:

    ```bash
    ./gradlew publishToMavenLocal
    ```

The dependencies and therefore the build order for the projects are:

1. Specification (optional, by default the Specification release version from the official SGr Maven repository is used when building the CommHandler)
2. GenDriverAPI (optional, by default  the GenDriverAPI release version from the official SGr Maven repository is used when building the CommHandler)
3. CommHandler
