package ma.sir.ged.service.facade.admin;

import ma.sir.ged.dto.FichierDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinIOService {

    Boolean bucketExists(String bucket);

    FichierDTO upload(MultipartFile file, String bucket);
    FichierDTO upload(MultipartFile file);

    FichierDTO uploadToBucket(MultipartFile file, String bucket, String path);
    FichierDTO uploadToBucket(MultipartFile file, String bucket, String superior, String entity);

    int saveBucket(String bucket);

    List<String> findAllDocuments(String bucket);
    List<String> findAllDocuments(String bucket, String path);

    byte[] downloadAllDocumentsAsZip(String bucket);
    byte[] downloadAllDocumentsAsZip(String bucket, String path);

    int createDirectory(String bucket, String path);

}
