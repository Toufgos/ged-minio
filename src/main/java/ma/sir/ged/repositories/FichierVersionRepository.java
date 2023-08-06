package ma.sir.ged.repositories;

import ma.sir.ged.model.FichierVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierVersionRepository extends JpaRepository<FichierVersion, String> {
}
