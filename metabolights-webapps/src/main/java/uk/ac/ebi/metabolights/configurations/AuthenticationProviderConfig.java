package uk.ac.ebi.metabolights.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.ac.ebi.metabolights.authenticate.IsaTabAuthenticationProvider;

@Configuration
public class AuthenticationProviderConfig {
    
    @Bean
    IsaTabAuthenticationProvider isaTabAuthenticationProvider(){
        return new IsaTabAuthenticationProvider();
    } 
}
