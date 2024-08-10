package com.personal.project.angi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    List<Map> uploadMultiFiles(MultipartFile[] multipartFiles, String folderName) throws IOException;

    Map uploadFile(MultipartFile multipartFile, String folderName) throws IOException;

    Map delete(String id) throws IOException;

    File convert(MultipartFile multipartFile) throws IOException;
}
