package ma.sir.ged.repositories;

import ma.sir.ged.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichierRepository extends JpaRepository<Fichier, Long> {
    Optional<Fichier> findFichierByFullPathAndBucket(String fullPath, String bucket);
}
