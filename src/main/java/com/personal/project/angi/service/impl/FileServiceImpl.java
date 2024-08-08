package com.personal.project.angi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.personal.project.angi.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    Cloudinary cloudinary;
    @Value("${cloudinary.cloud-name}")
    private String CLOUD_NAME;
    @Value("${cloudinary.api-key}")
    private String API_KEY;
    @Value("${cloudinary.api-secret}")
    private String API_SECRET;

    public FileServiceImpl() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", CLOUD_NAME);
        valuesMap.put("api_key", API_KEY);
        valuesMap.put("api_secret", API_SECRET);
        cloudinary = new Cloudinary(valuesMap);
    }

    @Override
    public List<Map> uploadMultiFiles(MultipartFile[] multipartFiles, String folderName) throws IOException {
        List<Map> results = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            File file = convert(multipartFile);
            Map params = ObjectUtils.asMap("folder", folderName); // Đặt thư mục trong đây
            Map result = cloudinary.uploader().upload(file, params);
            results.add(result);
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
            }
        }
        return results;
    }

    @Override
    public Map uploadFile(MultipartFile multipartFile, String folderName) throws IOException {
        File file = convert(multipartFile);
        Map params = ObjectUtils.asMap("folder", folderName);
        Map result = cloudinary.uploader().upload(file, params);
        return result;
    }

    @Override
    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    @Override
    public File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
