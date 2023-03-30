package com.openwebinars.rest.Converter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies;

@Configuration
@EnableJpaAuditing
public class MiConfiguracion {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/producto**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }

        };
    }

    @Component
    public class RestConfiguration implements RepositoryRestConfigurer {

        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.setRepositoryDetectionStrategy(RepositoryDetectionStrategies.ALL);
        }

    }

}
