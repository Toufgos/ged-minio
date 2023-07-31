package ma.sir.ged.service.impl.admin;

import io.minio.BucketExistsArgs;
import io.minio.GetBucketVersioningArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RestoreObjectArgs;
import io.minio.Result;
import io.minio.SetBucketVersioningArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import io.minio.messages.VersioningConfiguration;
import ma.sir.ged.service.facade.admin.MinIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class MinIOServiceImpl implements MinIOService {

    @Autowired
    private MinioClient minioClient;

    @Override
    public Boolean bucketExists(String name) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int upload(MultipartFile file, String bucket) {
        if (! bucketExists(bucket))
            return 0;
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket)
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int saveBucket(String bucket) {
        if (bucketExists(bucket))
            return 0;
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            VersioningConfiguration config = new VersioningConfiguration(VersioningConfiguration.Status.ENABLED, false);
            minioClient.setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucket).config(config).build());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<String> findAllDocuments(String bucket) {
        List<String> documents = new ArrayList<>();
        if (! bucketExists(bucket))
            return documents;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(true).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                documents.add(item.objectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
    @Override
    public List<String> findAllDocuments(String bucket, String path) {
        List<String> documents = new ArrayList<>();
        if (! bucketExists(bucket))
            return documents;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).prefix(path).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                documents.add(item.objectName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public byte[] downloadAllDocumentsAsZip(String bucket) {
        if (! bucketExists(bucket))
            return null;
        try {
            List<String> documentNames = findAllDocuments(bucket);
            // Create a byte array output stream to hold the zip data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            // Buffer for reading data
            byte[] buffer = new byte[8192];

            // Loop through each document and add it to the zip
            for (String documentName : documentNames) {
                // Get the document object from MinIO
                GetObjectResponse response = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(documentName)
                                .build()
                );
                // Get the input stream containing the document data
                InputStream documentStream = response;
                // Create a new entry in the zip for the document
                ZipEntry zipEntry = new ZipEntry(documentName);
                zipOut.putNextEntry(zipEntry);
                // Write the document data to the zip
                int bytesRead;
                while ((bytesRead = documentStream.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, bytesRead);
                }
                // Close the entry for the document
                zipOut.closeEntry();
                // Close the input stream for the current document
                documentStream.close();
            }

            // Close the zip output stream
            zipOut.close();
            // Return the zip data as a byte array
            return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }
    @Override
    public byte[] downloadAllDocumentsAsZip(String bucket, String path) {
        if (! bucketExists(bucket))
            return null;
        try {
            List<String> documentNames = findAllDocuments(bucket, path);
            // Create a byte array output stream to hold the zip data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            // Buffer for reading data
            byte[] buffer = new byte[8192];

            // Loop through each document and add it to the zip
            for (String documentName : documentNames) {
                // Get the document object from MinIO
                GetObjectResponse response = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(documentName)
                                .build()
                );
                // Get the input stream containing the document data
                InputStream documentStream = response;
                // Create a new entry in the zip for the document
                ZipEntry zipEntry = new ZipEntry(documentName);
                zipOut.putNextEntry(zipEntry);
                // Write the document data to the zip
                int bytesRead;
                while ((bytesRead = documentStream.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, bytesRead);
                }
                // Close the entry for the document
                zipOut.closeEntry();
                // Close the input stream for the current document
                documentStream.close();
            }

            // Close the zip output stream
            zipOut.close();
            // Return the zip data as a byte array
            return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }

    @Override
    public int createDirectory(String bucket, String path) {
        if (! bucketExists(bucket))
            return 0;
        try {

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucket).object(path).stream(
                            new ByteArrayInputStream(new byte[] {}), 0, -1).build()
            );
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void restoreObject(String bucketName, String objectPath, String versionId){
        try {
            minioClient.restoreObject(RestoreObjectArgs.builder().bucket(bucketName).object(objectPath).versionId(versionId).build());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception : "+e.getMessage());
        }

    }
    public List<String> getAllVersions(String bucketName, String objectPath) {
        try {
            // Check if bucket versioning is enabled
            VersioningConfiguration versioningConfig = minioClient.getBucketVersioning(GetBucketVersioningArgs.builder().bucket(bucketName).build());
            if (!versioningConfig.status().equals("Enabled")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bucket versioning is not enabled.");
            }
            // Get all versions of the object
            GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectPath).build());
            List<String> versionIds = Collections.emptyList();
            //object.
            if (versionIds.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No versions of the object found.");
            }

            return versionIds;
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Minio Error: " + e.getMessage());
        }
        catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception : "+e.getMessage());
        }
    }

}
