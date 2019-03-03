package apache.camel.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.processor.idempotent.FileIdempotentRepository;
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;
import org.apache.camel.spi.IdempotentRepository;

public class App {
    static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
//        IdempotentRepository<String> repo = MemoryIdempotentRepository.memoryIdempotentRepository();
        FileIdempotentRepository repo = new FileIdempotentRepository();
        repo.setFileStore(new File("/Users/stefan/tmp/dm01_shp_downloaded.txt"));
        repo.setCacheSize(5000);
        repo.setMaxFileStoreSize(51200000);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("repo", repo);
        
        final CamelContext camelContext = new DefaultCamelContext(registry);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
//                    from("file:///Users/stefan/tmp/data/?fileName=ch_254900.itf&noop=true").to("file:///Users/stefan/Downloads/output/");
                    from("ftp://xxxx@ftp.infogrips.ch/\\dm01avso24lv95_2\\shp\\?password=yyyyy&autoCreate=false&noop=true&stepwise=false&separator=Windows&passiveMode=true&binary=true&delay=50000&initialDelay=30000&idempotentRepository=#repo&idempotentKey=${file:name}-${file:size}")
                    .to("file:///Users/stefan/Downloads/output/");
                }
            });
            camelContext.start();
            Thread.sleep(100000000);
            camelContext.stop();
        } catch (Exception camelException) {
            camelException.printStackTrace();
            log.error("Exception trying to copy files - {0}", camelException.toString());
        }
    }
}
