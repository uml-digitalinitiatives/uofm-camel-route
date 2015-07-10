package ca.umanitoba.routes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;


public class JMSCreator extends RouteBuilder {

    final String template_msg =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:fedora-types=\"http://www.fedora.info/definitions/1/0/types/\">" +
                    "<id>urn:uuid:fake</id><updated>${exchangeProperty.date}</updated><author><name>fedoraAdmin</name><uri>http://localhost:8080/fedora</uri></author>" +
                    "<title type=\"text\">modifyObject</title>" +
                    "<category term=\"${in.header.pid}\" scheme=\"fedora-types:pid\" label=\"xsd:string\"></category>" +
                    "<category term=\"A\" scheme=\"fedora-types:state\" label=\"xsd:string\"></category>" +
                    "<summary type=\"text\">${in.header.pid}</summary>" +
                    "<content type=\"text\">${exchangeProperty.date}</content>" +
                    "</entry>";

    private final DateFormat df = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");

    /*
     * (non-Javadoc)
     * @see org.apache.camel.builder.RouteBuilder#configure()
     */
    @Override
    public void configure() throws Exception {

        // Rest endpoint
        rest("/reindex/")
        .get("/{pid}")
        .description("Takes a PID and passes a JMS message to Gsearch")
        .to("direct:to_jms");

        // Re-indexing route
        from("direct:to_jms")
                .setExchangePattern(ExchangePattern.InOnly)
                .setProperty("date", constant(getDate()))
        .setBody(simple(template_msg))
                .log("Sending JMS message to queue for PID ${in.header.pid}")
        .removeHeaders("*")
        .to("{{output.stream}}?jmsMessageType=Text&transferException=true");
    }

    private String getDate() {
        final Date date = new Date();
        final String date_string = df.format(date);
        return date_string;
    }

}
