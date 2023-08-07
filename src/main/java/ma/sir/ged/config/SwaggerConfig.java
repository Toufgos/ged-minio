package ma.sir.ged.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gedMinioOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ged-minio documentation")
                        .version("1.0.0")
                        .description("All the endpoints existing to manage minio operations")
                );
    }

}
