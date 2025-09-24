# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.4.2] - 2025-09-24

### Fixed

- Null reference of string value decoded as JSON null node

### Added

- Javadoc for all public interfaces and methods

### Changed

- Updated specification to `2.1_2025-09-02`, adds W/m2
- updated Gradle build file
- query parameters and headers are not added to REST request if their value is empty

## [2.4.1] - 2025-05-20

### Fixed

- ignores all non-readable data points when reading all device values
- fixed conversion issues between JSON value and boolean, bitmap or array
- fixed HTTP status code in driver API

### Changed

- updated driver API
- updated Apache driver


## [2.4.0] - 2025-04-15

### Fixed

- fixed configuration parameter replacement in EID content when certain characters are used

### Added

- implemented value mappings in messaging interface similar to REST interface
- added option to disable SSL certificate validation to REST interface
- implemented introspection methods of dynamic data point parameters

### Changed

- requires updated SGr specification
- data point "{{value}}" placeholders must be replaced with "\[\[value\]\]"


## [2.3.0] - 2025-03-13

### Fixed

- fixed unit conversion in messaging

### Added

- added support for datetime data type (as ISO-8601 string or 64bit time stamp)
- added support for json data type (with JsonNode and conversion to objects)

### Changed

- improved internal processing of JSON data


## [2.2.0] - 2025-02-10

### Changed

- uses updated Modbus driver API
- adapted Modbus implementation to new API
- changed 'easymodbus' snapshot parameter to 'sgr-driver-modbus'
- j2mod is new default Modbus driver


## [2.1.2] - 2025-01-06

### Changed

- updated specification to `2.0_2024-12-19`


## [2.1.1] - 2024-12-09

### Fixed

- fixed handling of missing ordinals in ENUM values


## [2.1.0] - 2024-11-28

### Fixed

- CI build of driver API
- fixed ENUM data type
- fixed request-specific parameters

### Changed

- decoupled REST driver from CommHandler, removed Apache dependencies except for testing.
- decoupled messaging driver from CommHandler, removed HiveMQ dependencies except for testing.
- supports separate messaging drivers per platform
- decoupled Modbus driver from CommHandler, removed EasyModbus dependencies except for testing.
- changed Modbus factory handling


## [2.0.1] - 2024-10-24

### Fixed

- REST: Calls do not add headers with no value.
- REST: Form parameters are encoded with UTF-8.
- Messaging: Outbound messages correctly replace `{{value}}` placeholder.

### Changed

- uses `java-library` plugin in build
- added build parameter to use snapshots of SGr dependencies.
- set libraries as `api` dependencies, when they are exposed in interfaces.


## [2.0.0] - 2024-10-08

### Changed

- Uses separate package `sgr-specification:2.0_2024-10-08`.
- Uses separate package `sgr-driver-api:2.0.0`.
- Uses separate package `easymodbus:2.0.0`.


## [1.1.1] - 2024-08-27

### Fixed

- Modbus: Connection to shared Modbus RTU transport no longer fails.


## [1.1.0] - 2024-06-28

### Added

- Initial official release.
- Uses specification built on the same day.


## Note about Releases prior to 1.1.0

Releases prior to version 1.1.0 were created in a different package namespace
and were never meant to be used in production.
