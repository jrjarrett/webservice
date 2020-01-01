# Unique Email Webservice

This is a Spark (https://sparkjava.com) based web service for counting Gmail-unique email addresses 
from a list of email addresses provided via a POST request.

## Getting Started

* Clone the project
* `mvn package` && `mvn exec:java`
* POST a list of email addresses as strings to http://localhost:4567/emails

The payload is expected to be a list of strings that represent email address, and defines "unique" email addresses to be be delivered to the same account using Gmail account matching. Specifically: Gmail will ignore the placement of "." in the username. And it will ignore any portion of the username after a "+".

`[ "a@b.com" ]` will return 1.

`["test.email@gmail.com", "test.email+spam@gmail.com", "testemail@gmail.com"]` will also return 1

`["test.email@gmail.com", "test.email@lypsinc.org", "test.email+spam@gmail.com"]` would return 2.

### Prerequisites

You will need maven (> 3.3) to be able to build the project.
You will need Java 8 to build the project.

The `webservice` file included at the top level of the directory is a built, standalone version
of the application that can be executed via `java -jar webservice`. It's included as a convenience to get up and running faster. It is also rebuilt every time the Maven project is packaged in the /target folder
of the project as `webservice-0.0.1-SNAPSHOT-jar-with-dependencies.jar`


## Deployment

Spark allows for webservices to be deployed to a servlet container or application server; see http://sparkjava.com/documentation#other-web-server. However, this implementation just uses Spark's built-in Jetty server to host the service.

## Built With

* [Spark](http://sparkjava.com/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Jim Jarrett** - [https://github.com/jrjarrett]


## License

I make this freely available for use.
