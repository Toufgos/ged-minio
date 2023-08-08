package ma.sir.ged.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    private static final String LICENSE_NAME = "Ged-Minio";
    private static final String LICENSE_URL = "https://www.ged.ma";

    @Bean
    public OpenAPI openAPI(@Value("${info.app.version}") String appVersion,
                           @Value("${info.app.description}") String appDescription) {
        return new OpenAPI()
                .info(new Info()
                        .title("ged-minio documentation")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License()
                                .name(LICENSE_NAME)
                                .url(LICENSE_URL))
                );
    }
}
