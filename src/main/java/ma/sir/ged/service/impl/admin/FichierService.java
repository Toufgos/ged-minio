package ma.sir.ged.service.impl.admin;

import ma.sir.ged.dto.FichierDTO;
import ma.sir.ged.mappers.FichierMapper;
import ma.sir.ged.model.Fichier;
import ma.sir.ged.model.FichierVersion;
import ma.sir.ged.repositories.FichierRepository;
import ma.sir.ged.repositories.FichierVersionRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FichierService {
    @Autowired
    private final FichierRepository fichierRepository;
    @Autowired
    private final FichierVersionRepository versionRepository;
    @Autowired
    private final FichierMapper fichierMapper ;

    public FichierService(FichierRepository fichierRepository, FichierVersionRepository versionRepository, FichierMapper fichierMapper) {
        this.fichierRepository = fichierRepository;
        this.versionRepository = versionRepository;
        this.fichierMapper = fichierMapper;
    }
    private String extractFileName(String fullPath) {
        Path path = Paths.get(fullPath);
        return path.getFileName().toString();
    }
    private String extractFilePath(String fullPath) {
        Path path = Paths.get(fullPath);
        Path parentPath = path.getParent();
        if (parentPath != null) {
            return parentPath.toString();
        } else {
            return ""; // If there is no parent path, return an empty string or handle as needed
        }
    }
    public FichierDTO saveFichier(String bucket, String fullFilePath, String versionId){
        boolean isVersioningEnabled = StringUtils.isNotEmpty(versionId);
        Fichier fichier = fichierRepository.findFichierByFullPathAndBucket(fullFilePath, bucket).orElse(new Fichier());
        fichier.setBucket(bucket);
        fichier.setFullPath(fullFilePath);
        fichier.setObjectName(extractFileName(fullFilePath));
        fichier.setPath(extractFilePath(fullFilePath));
        fichierRepository.save(fichier);
        if(isVersioningEnabled){
            FichierVersion version = new FichierVersion();
            int nextVersionNumber = fichier.getNextVersionNumber();
            version.setVersionId(versionId);
            version.setVersionNumber(nextVersionNumber);
            version.setFichier(fichier);
            versionRepository.save(version);
            fichier.getVersions().add(version);
            fichierRepository.save(fichier);
        }
        return fichierMapper.fichierToFichierDTO(fichier);
    }

}
