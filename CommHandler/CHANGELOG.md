# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
