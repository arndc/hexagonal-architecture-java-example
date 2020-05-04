# Core module

The core module is the beating heart of the application. All the business terms and logic reside in this module. 
In other words you can say it reflects the language of the business.

**What does that mean technically?**

The core module consists of three parts: 
_[Domain](./src/main/java/me/arnedc/examples/architecture/hexagonal/core/domain), 
[Ports](./src/main/java/me/arnedc/examples/architecture/hexagonal/core/ports) and 
[Application](./src/main/java/me/arnedc/examples/architecture/hexagonal/core/application)_.

## Domain

The domain holds all the business terms (models) and logic (services). The domain services should only be available in the core module and only be used by application services. 

## Ports

Ports provide interfaces / contracts to modules (including itself) to implement. 

There are **2** types of ports:

1. [driving](./src/main/java/me/arnedc/examples/architecture/hexagonal/core/ports/driving) (also known as primary ports)
2. [driven](./src/main/java/me/arnedc/examples/architecture/hexagonal/core/ports/driven) (also known as secondary ports)

### Driving ports

Driving ports drive the application. This means that they start a business process in the core module. 
Application services provide an implementation for these ports.

### Driven ports

Driven ports are driven by the core module. The implementation for these ports are called adapters. 
Adapters live outside the core module and provide a certain service such as persistence, an external API call...
The `Provider` suffix is often used for the Driven Ports contracts to make them more recognizable, but this is not a requirement.

## Application

The application contains services that uses one or more domain services. 
The application services are the implementation of the driving ports.
These services put everything into motion, they drive the entire application.
