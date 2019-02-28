package apache.camel.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
    static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        final CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:///Users/stefan/tmp/data/?fileName=ch_254900.itf&noop=true").to("file:///Users/stefan/Downloads/output/");
                }
            });
            camelContext.start();
            Thread.sleep(4000);
            camelContext.stop();
        } catch (Exception camelException) {
            camelException.printStackTrace();
            log.error("Exception trying to copy files - {0}", camelException.toString());
        }

        System.out.println("Hallo Welt.");
    }
}
