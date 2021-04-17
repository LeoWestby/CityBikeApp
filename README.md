# City Bike App
This application integrates with Oslo Bysykkel's open APIs (https://oslobysykkel.no/apne-data/sanntid)
to display a list of bike stations and available bikes. The frontend is a single page application written in React
that communicates with a backend written in Kotlin.

## Installation

### Backend
Ensure that a Java Development Kit (JDK) is installed, and that the environment variable JAVA_HOME points to its
root directory (not the /bin folder). Building has been tested to work with
[JDK version 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

Then run the following command in the project's root directory:
```bash
./gradlew bootRun
```

This should compile and start the Kotlin backend at localhost:8080.

To run all integration tests in the project, run the following command:
```bash
./gradlew test
```

### Frontend

The frontend requires [Node Package Manager](https://www.npmjs.com/get-npm) to be installed.

To build and run the fronend application, run the following commands starting in the project's root directory:

```bash
cd src\main\client
npm install
npm start
```

This should host the app at localhost:3000.