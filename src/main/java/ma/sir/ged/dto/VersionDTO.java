package ma.sir.ged.dto;

public class VersionDTO {
    String versionId;
    Integer versionNumber;

    public VersionDTO(String versionId, Integer versionNumber) {
        this.versionId = versionId;
        this.versionNumber = versionNumber;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }
}
