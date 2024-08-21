package com.personal.project.angi.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.personal.project.angi.service.FileService;
import com.personal.project.angi.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private  Cloudinary cloudinary;

    @Override
    public List<Map> uploadMultiFiles(MultipartFile[] multipartFiles, String id,  String typeUpload) throws IOException {
        List<Map> results = new ArrayList<>();
        String filePathKey = getFilePathKey(id, typeUpload);
        Map params = ObjectUtils.asMap(
                "use_filename", false,
                "unique_filename", true,
                "folder", filePathKey
        );
        for (MultipartFile multipartFile : multipartFiles) {
            File file = convert(multipartFile);
            Map result = cloudinary.uploader().upload(file, params);
            results.add(result);
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete temporary file: " + file.getAbsolutePath());
            }
        }
        return results;
    }

    @Override
    public Map uploadFile(MultipartFile multipartFile, String id, String typeUpload) throws IOException {
        File file = convert(multipartFile);
        String filePathKey = getFilePathKey(id, typeUpload);

        // Set up the parameters for Cloudinary upload
        Map params = ObjectUtils.asMap(
                "use_filename", false,
                "unique_filename", true,
                "folder", filePathKey
        );

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

    private String getFilePathKey(String originalFilename, String userId, String typeUpload) {
        // Generate new filename
        Date current = new Date();

        String newFileName = String.valueOf(current.getTime());
        String modifiedFilename = modifyFilename(originalFilename, newFileName);

        return Util.generateFileDirectory(typeUpload, userId, modifiedFilename);
    }

    private String getFilePathKey(String userId, String typeUpload) {
        return Util.generateFileDirectory(typeUpload, userId);
    }

    private String modifyFilename(String originalFilename, String newFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        return newFilename + fileExtension;
    }
}
