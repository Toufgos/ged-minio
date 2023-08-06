package ma.sir.ged.mappers;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappersProvider {
    @Bean
    public FichierMapper fichierMapper(){
        return Mappers.getMapper(FichierMapper.class);
    }
    @Bean
    public VersionMapper versionMapper(){
        return Mappers.getMapper(VersionMapper.class);
    }
}
