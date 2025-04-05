package com.pm.projectmanager.common.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile image) throws IOException {
        Storage storage;

        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credentialsPath != null && !credentialsPath.isEmpty()) {
            File credentialsFile = new File(credentialsPath);
            if (credentialsFile.exists()) {
                try (FileInputStream serviceAccountStream = new FileInputStream(credentialsFile)) {
                    storage = StorageOptions.newBuilder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                            .build()
                            .getService();
                }
            } else {
                throw new IllegalStateException("GOOGLE_APPLICATION_CREDENTIALS 경로에 파일이 존재하지 않습니다.");
            }
        } else {
            String keyFileName = "projectmanager_bucket_key.json";
            try (InputStream keyFile = getClass().getClassLoader().getResourceAsStream(keyFileName)) {
                if (keyFile == null) {
                    throw new IllegalStateException("GCS 키 파일을 찾을 수 없습니다: " + keyFileName);
                }
                storage = StorageOptions.newBuilder()
                        .setCredentials(GoogleCredentials.fromStream(keyFile))
                        .build()
                        .getService();
            }
        }

        String originalFileName = image.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uniqueFileName)
                .setContentType(image.getContentType()).build();

        storage.create(blobInfo, image.getInputStream());

        String gcsImageUrl = "https://storage.cloud.google.com/projectmanager_bucket/" + uniqueFileName;
        System.out.println("✅ File uploaded as: " + gcsImageUrl);
        return gcsImageUrl;
    }

    public void deleteImage() {
        System.out.println("good");
    }
}