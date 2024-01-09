package com.progresssoft.clustereddatawarehouse.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Data Warehouse Service")
                        .description("Data Warehouse Service API Documentation For Deals")
                        .version("1.0.0")
                );
    }
}
