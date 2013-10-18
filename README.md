[![Build Status](https://travis-ci.org/energyos/OpenESPI-ThirdParty-java.png)](https://travis-ci.org/energyos/OpenESPI-ThirdParty-java)

OpenESPI-ThirdParty
======================

The Open Energy Services Provider Interface (ESPI) Third Party Repository Providing implementations of the interface used to provide energy usage information to retail customers.

## Setup

First clone the project from github:

```bash
git clone https://github.com/energyos/OpenESPI-ThirdParty-java.git
cd OpenESPI-ThirdParty-java/
```

Start tomcat7 using maven:

```bash
mvn tomcat7:run
```

Now the application should be available at [http://localhost:9000/](http://localhost:9000/).

## IDE Setup

### Eclipse Setup

Open Eclipse and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### Spring Tool Suite Setup

Open Spring Tool Suite and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### IntelliJ Setup

Open IntelliJ and open the project (File > Open...).

## Testing

### Unit Tests

To run all Unit tests:

```bash
mvn test
```

Run a single test class:

```bash
mvn -Dtest=<TestClassName> test
mvn -Dtest=HomeControllerTests test
```

Run a single test in a single class:

```bash
mvn -Dtest=<TestClassName>#<testMethodName> testMethodName
mvn -Dtest=HomeControllerTests#index_whenNotLoggedIn_displaysHomeView test
```

### Cucumber Features

To run all Cucumber features:

```bash
mvn verify
```

