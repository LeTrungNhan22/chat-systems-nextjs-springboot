package com.snow.oauth2.socialoauth2.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                contact = @Contact(
                        name = "letrungnhan",
                        email = "letrungnhan220801@gmail.com",
                        url = "https://github.com/LeTrungNhan22"
                ),
                title = "OpenAPI Documentation for Chat Application by letrungnhan",
                version = "1.0",
                description = "Chat application with social login APIs (to get token access url: http://localhost:8080/oauth2/authorize/google?redirect_uri=http://localhost:3000/ouath2/redirect)",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "http://swagger.io/terms/"
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080",
                        variables = {}
                ),
                @Server(
                        description = "Heroku Server",
                        url = "https://social-oauth2.herokuapp.com",
                        variables = {}
                )

        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }


)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        description = "JWT Bearer"


)

@Configuration
public class OpenApiConfig {


}
