# UofM Camel Route

### SUMMARY
***
A simple REST to Gsearch bridge to allow batch reindexing of Fedora 3 content

This exposes a GET-only endpoint at http://**hostname**:**port**/uofm-camel-route/rest/reindex/**pid**

It takes the **pid** and generates a JMS Fedora Update message and sends it to the topic/queue 
you have configured.


### REQUIREMENTS
***

### INSTALLATION
***

Build the war by executing
```
mvn clean install
```
in the root directory, then copy the **uofm-camel-route.war** from the **target** directory to your web container.

### CONFIGURATION
***

It accepts 3 java options:
* uofm-route.jms.host - the hostname of the broker, defaults to "localhost"
* uofm-route.dynamic.jms.port - port number of the JMS broker, defaults to "61616"
* uofm-route.jms.endpoint - topic/queue name, defaults to "queue:fedora.apim.update"

You can alter these in the application.properties file or pass them to the JVM like ```-Duofm-route.jms.endpoint=topic:fedora.apim.access```
