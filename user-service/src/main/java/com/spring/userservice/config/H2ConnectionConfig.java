package com.spring.userservice.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class H2ConnectionConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .url("mem:userdb;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4")
                        .username("sa")
                        .build());
    }
}
