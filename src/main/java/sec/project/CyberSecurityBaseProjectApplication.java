package sec.project;

import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class, args);
    }
        
    public void customize(ConfigurableEmbeddedServletContainer cesc) {
        ((TomcatEmbeddedServletContainerFactory) cesc).addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context cntxt) {
                cntxt.setUseHttpOnly(true);
            }
            
        });
    }
}
