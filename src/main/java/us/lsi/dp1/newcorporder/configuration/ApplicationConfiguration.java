package us.lsi.dp1.newcorporder.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Multiset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import us.lsi.dp1.newcorporder.configuration.jackson.MultisetDeserializer;
import us.lsi.dp1.newcorporder.configuration.jackson.MultisetSerializer;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfiguration {

    @Bean
    public Module multisetModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new MultisetSerializer());
        module.addDeserializer(Multiset.class, new MultisetDeserializer());
        return module;
    }
}
