package ma.sir.ged.mappers;

import ma.sir.ged.dto.FichierDTO;
import ma.sir.ged.dto.VersionDTO;
import ma.sir.ged.model.Fichier;
import ma.sir.ged.model.FichierVersion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface FichierMapper {

    @Mapping(source = "objectName", target = "name")
    @Mapping(source = "versions", target = "versions")
    @Mapping(source = "bucket", target = "bucket")
    FichierDTO fichierToFichierDTO(Fichier fichier);

    @Mapping(source = "name", target = "objectName")
    Fichier fichierDTOToFichier(FichierDTO fichierDTO);

    default VersionDTO mapFichierVersionToVersionDTO(FichierVersion fichierVersion) {
        return new VersionDTO(fichierVersion.getVersionId(), fichierVersion.getVersionNumber());
    }

    default FichierVersion mapVersionDTOToFichierVersion(VersionDTO versionDTO) {
        FichierVersion fichierVersion = new FichierVersion();
        fichierVersion.setVersionId(versionDTO.getVersionId());
        fichierVersion.setVersionNumber(versionDTO.getVersionNumber());
        return fichierVersion;
    }

    @AfterMapping
    default void setActualVersion(Fichier fichier, @MappingTarget FichierDTO fichierDTO) {
        FichierVersion latestVersion = fichier.getLatestVersion();
        if (latestVersion != null) {
            fichierDTO.setActualVersion(mapFichierVersionToVersionDTO(latestVersion));
        }
    }
}