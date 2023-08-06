package ma.sir.ged.mappers;

import ma.sir.ged.dto.VersionDTO;
import ma.sir.ged.model.FichierVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VersionMapper {

    @Mapping(source = "versionId", target = "versionId")
    @Mapping(source = "versionNumber", target = "versionNumber")
    VersionDTO fichierVersionToVersionDTO(FichierVersion fichierVersion);
}