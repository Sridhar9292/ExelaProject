package com.exelatech.ecxapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApi30Config {

  private final String appName;
  private final String appVersion;
  private final String appTimestamp;
  private final String appDescription;

  public OpenApi30Config(
      @Value("${openapi.app.name}") String appName,
      @Value("${openapi.app.version}") String appVersion, 
      @Value("${openapi.app.timestamp}") String appTimestamp,
      @Value("${openapi.app.description}") String appDescription) {
            this.appName = appName;
            this.appVersion = appVersion;
            this.appTimestamp = appTimestamp;
            this.appDescription = appDescription;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    final String apiTitle = String.format("%s", appName.toUpperCase());
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
        )
        .info(new Info().title(apiTitle).version(appVersion).description(appDescription));
  }
}