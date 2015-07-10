package ca.umanitoba.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Boilerplate to configure REST service through Camel.
 *
 * @author danny stolen by whikloj
 */
public class ServletConfigurator extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet");
    }
}
