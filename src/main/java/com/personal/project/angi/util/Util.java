package com.personal.project.angi.util;

import com.personal.project.angi.constant.FileConstant;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Util {
    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String generateFileDirectory(String... arg) {
        return String.join(FileConstant.DIRECTORY_DIVIDE, arg);
    }
}
