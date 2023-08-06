package ma.sir.ged;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.sir.ged.service.impl.admin.MinIOServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
//@EnableFeignClients("ma.sir.ged.required.facade")
public class GedApplication implements ApplicationRunner {
    public static ConfigurableApplicationContext ctx;
    @Autowired
    private MinIOServiceImpl minIOService;

    public static void main(String[] args) {
        ctx = SpringApplication.run(GedApplication.class, args);
    }


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        minIOService.getAllVersions("ged", "1.png");
    }
}


