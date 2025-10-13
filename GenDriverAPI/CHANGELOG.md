# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [unreleased]

### Added

- Support for Modbus RTU over TCP
- Modbus exception propagation
- HTTP header constants

### Changed

- updated dependencies


## [2.3.2] - 2025-10-03

### Changed

- cleaned up documentation and javadoc
- updated Gradle build file


## [2.3.1] - 2025-05-13

### Added

- added javadoc documentation to all public interfaces

### Fixed

- HTTP status code 403 was 500, fixed


## [2.3.0] - 2025-04-14

### Added

- added option to disable SSL certificate validation to REST interface


## [2.2.0] - 2025-02-10

### Added

- improved Modbus interface methods
- support Modbus RTU with ASCII encoding

### Changed

- deprecated 'old' Modbus interface methods


## [2.1.0] - 2024-11-28

### Added

- support Modbus UDP
- support contacts interface

### Changed

- uses `java-library` plugin in build.
- added build parameter to use snapshots of SGr dependencies.
- set libraries as `api` dependencies, when they are exposed in interfaces.
- added URI builder to factory interface.
- refactored message filter handling in messaging.


## [2.0.0] - 2024-10-08

### Changed

- Refactored all driver interfaces.


## [1.1.0] - 2024-06-28

### Added

- Initial official release.


## Note about Releases prior to 1.1.0

Releases prior to version 1.1.0 were created in a different package namespace
and were never meant to be used in production.
