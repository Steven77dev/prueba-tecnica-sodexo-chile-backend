package com.sodexo.msc.noticias.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Value("${app.api.swagger.version}")
    private String version;
    @Value("${app.api.swagger.titulo}")
    private String title;
    @Value("${app.api.swagger.descripcion}")
    private String description;
    @Value("${app.api.swagger.basePackage}")
    private String basePackage;
    @Value("${app.api.swagger.contacto-nombre}")
    private String contactName;
    @Value("${app.api.swagger.contacto-correo}")
    private String contactEmail;
    @Value("${app.api.swagger.path-pattern}")
    private String pathPattern;

    @Bean
    public GroupedOpenApi apiInfo() {
        return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan(basePackage) // Paquete donde se encuentran tus controladores
                .addOpenApiCustomiser(openApi -> {
                    Info info = new Info()
                            .title(title)
                            .version(version)
                            .description(description)
                            .contact(new io.swagger.v3.oas.models.info.Contact()
                                    .name(contactName)
                                    .email(contactEmail)
                                    .url("https://www.linkedin.com/in/steven-llaja-3a896975/"));
                    openApi.setInfo(info);
                })
                .build();
    }
}