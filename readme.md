# SGrJava

## Index
[Summary](#summary)<br>
[Description](#description)<br>
[Build and publish for development](#build-and-publish-for-development)<br>


## Summary
SGrJava is home for the SmartgridReady core components, the communication handler implementations.
Current list of interfaces supported by the communication handler:
- Modbus
- REST API's
- MQTT

A communication handler maps the SmartgridReady generic interface to a device/product specific interface. The interface mapping is defined within an external interface description in XML. (see https://github.com/SmartgridReady/SGrSpecifications/tree/master/XMLInstances/ExtInterfaces)

The external interface description XML (EI-XML) describes for every functionalprofile-datapoint tuple the properties needed to read/write date from/to the datapoint.

## Description

The SGrJava repository contains of three projects:

### CommHandler 
Code that interprets the EI-XML and adapts the communication interface to the device communication. 
[JavaDoc](https://smartgridready.github.io/SGrJava/docs/commhandler/)
 
### SGrSpecification
Provides JAXB generated classes based on the XML schema definitions within the SGrSpecifications GitHub repository.


### GenDriverAPI
API used to integrate different device drivers for Modbus, REST and MQTT. 
[JavaDoc](https://smartgridready.github.io/SGrJava/docs/gen-driver-api/)


## Build and publish for development

### Prerequisites
1. Java JDK version >= 1.11


2. **To build SGrSpecification if required:** Clone the SGrSpecifications repository (https://github.com/SmartGridready/SGrSpecifications/) in  parallel to the SGrJava repository. This is required that the `SGrSpecification` project finds the XML-Schema files needed to generate the JAXB classes.
```
Required folder structure:

sgr-projects-root
 |_SGrJava
 |_SGrSpecifications
```

### Run the gradle build and publish to the local maven repository

Each project can be built separately:

- To build the project run in the project root of the respective project (CommHandler, SGrSpecification, GenDriverAPI):
    ```
    bash>./gradle clean build
    ```

- To publish to the local Maven repository run:
    ```
    bash>gradle publishToMavenLocal
    ```
The dependencies and therefore the build order for the projects are:
1. GenDriverAPI (optional, by default  the GenDriverAPI release version from the official SGr Maven repository is used when building the CommHandler)
2. SGrSpecification (optional, by default the SGrSpecification release version from the official SGr Maven repository is used when building the CommHandler)
3. CommHandler


