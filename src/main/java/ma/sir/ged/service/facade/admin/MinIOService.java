package ma.sir.ged.service.facade.admin;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinIOService {

    Boolean bucketExists(String bucket);

    int upload(MultipartFile file, String bucket);

    int saveBucket(String bucket);

    List<String> findAllDocuments(String bucket);
    List<String> findAllDocuments(String bucket, String path);

    byte[] downloadAllDocumentsAsZip(String bucket);
    byte[] downloadAllDocumentsAsZip(String bucket, String path);

    int createDirectory(String bucket, String path);

}
