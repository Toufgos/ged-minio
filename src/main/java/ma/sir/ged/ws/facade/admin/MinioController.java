package ma.sir.ged.ws.facade.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ma.sir.ged.dto.FichierDTO;
import ma.sir.ged.service.facade.admin.MinIOService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


//1- download minio from :::: https://min.io/docs/minio/windows/index.html
//2- run the command (on the exe file) : .\minio.exe server C:\minio --console-address :9090
//3- change minio.accessKey=Vh4IZZ6xwTg781Upj1qp and minio.secretKey=nScFlyHHJGdnosTi5FOsFOyDsVBGJp7TmxNmFp5B
// in app-dev.prop to new value defined in http://192.168.0.100:9090/access-keys

@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinIOService minIOService;


    //--- Check if bucket exists or not ---
    //curl "http://localhost:8036/minio/bucket/my-bucket"
    @Operation(summary = "Check if bucket exists or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check if bucket exits or not.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    @GetMapping("/bucket/{name}")
    public Boolean bucketExists(@PathVariable String name) {
        return minIOService.bucketExists(name);
    }

    //--- Upload a file to the bucket ---
    //curl -X POST -F "file=@./file01.pdf" http://localhost:8036/minio/upload/file/my-bucket
    @Operation(summary = "Upload a file to the bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload a file to the bucket",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    @PostMapping("/upload/file/{bucket}")
    public FichierDTO upload(@RequestParam("file") MultipartFile file, @PathVariable String bucket) {
        return minIOService.upload(file, bucket);
    }
    @PostMapping("/upload/file-with-path/{bucket}")
    public FichierDTO upload(@RequestParam("file") MultipartFile file, @PathVariable String bucket, @RequestParam("path") String path) {
        return minIOService.uploadToBucket(file, bucket, path);
    }
    @PostMapping("/upload/file-structured/{bucket}")
    public FichierDTO upload(@RequestParam("file") MultipartFile file, @PathVariable String bucket, @RequestParam("superior") String superior, @RequestParam("entity") String entity) {
        return minIOService.uploadToBucket(file, bucket, superior, entity);
    }
    //--- Create a new bucket ---
    //curl -X POST -F "bucketName=my-new-bucket" http://localhost:8036/minio/bucket
    @Operation(summary = "Create a new bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create a new bucket",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    @PostMapping("/bucket")
    public int saveBucket(@RequestParam("bucketName") String bucket) {
        return minIOService.saveBucket(bucket);
    }

    //--- List all files of a bucket ---
    //curl -X GET http://localhost:8036/minio/findAll/bucket/my-bucket
    @Operation(summary = "List all files of a bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all the files of a bucket",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    @GetMapping("/findAll/bucket/{bucket}")
    public List<String> findAllDocuments(@PathVariable String bucket) {
        return minIOService.findAllDocuments(bucket);
    }

    //--- Download all files from a bucket ---
    //curl -o D:/GED/all_documents.zip http://localhost:8036/minio/downloadAll/bucket/my-bucket
    @Operation(summary = "Download all files from a bucket as a zip file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Donwload all files from a bucket as a single zip file",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    @GetMapping(value = "/downloadAll/{bucket}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadAllDocumentsAsZip(@PathVariable String bucket, @RequestParam(name ="path", required = false) String path) {
        byte[] zipData = (StringUtils.isNoneBlank(path)) ?
                    minIOService.downloadAllDocumentsAsZip(bucket, path) :
                    minIOService.downloadAllDocumentsAsZip(bucket);
        if (zipData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "all_documents.zip");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(zipData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //--- Create a new directory inside a bucket ---
    //curl -X POST -F "bucketName=my-new-bucket" http://localhost:8036/minio/bucket/directory
    @PostMapping("/bucket/directory")
    @Operation(summary = "Create a new directory inside a bucket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create a new directory inside a bucket",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) })})
    public int saveBucket(@RequestParam("bucketName") String bucket, @RequestParam("directoryPath") String path) {
        return minIOService.createDirectory(bucket, path);
    }

}
