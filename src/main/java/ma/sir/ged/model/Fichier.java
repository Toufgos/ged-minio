package ma.sir.ged.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Fichier {
    @Id
    @GeneratedValue
    private Long id;


    @Column
    private String bucket;

    @Column
    private String path;

    @Column(nullable = false)
    private String fullPath;


    public int getNextVersionNumber() {
        int latestVersionNumber = versions != null && !versions.isEmpty()
                ? versions.stream().mapToInt(FichierVersion::getVersionNumber).max().getAsInt()
                : 0;
        return latestVersionNumber + 1;
    }

    @Column(nullable = false)
    private String objectName;

    @OneToMany(mappedBy = "fichier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FichierVersion> versions;

    public FichierVersion getLatestVersion() {
        if (versions != null && !versions.isEmpty()) {
            // Sort the versions based on the versionNumber in descending order
            versions.sort((v1, v2) -> Integer.compare(v2.getVersionNumber(), v1.getVersionNumber()));
            return versions.get(0); // Return the first (latest) version
        }
        return null; // No versions exist for this fichier
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<FichierVersion> getVersions() {
        if(CollectionUtils.isEmpty(versions)){
            versions = new ArrayList<>();
        }
        return versions;
    }

    public void setVersions(List<FichierVersion> versions) {
        this.versions = versions;
    }
    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
