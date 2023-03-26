package uk.ac.ebi.metabolights.configurations;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig extends AbstractHttpSessionApplicationInitializer {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        JndiTemplate jndi = new JndiTemplate();
        try {
            String hostName = jndi.lookup("java:comp/env/redisHost", java.lang.String.class);
            int port = jndi.lookup("java:comp/env/redisPort", java.lang.Integer.class);
            String password = jndi.lookup("java:comp/env/redisPassword", java.lang.String.class);
            int database = jndi.lookup("java:comp/env/redisDBIndex", java.lang.Integer.class);
            factory.setHostName(hostName);
            factory.setPort(port);
            factory.setDatabase(database);
            factory.setPassword(password);
        } catch (NamingException e) {
            throw new RuntimeException("", e);
        }
        return factory;
    }

    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
        RedisHttpSessionConfiguration config = new RedisHttpSessionConfiguration();
        config.setMaxInactiveIntervalInSeconds(14400);
        return config;
    }
}