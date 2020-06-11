package ru.aikam.task.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Класс для проверки валидности пути
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathValidator {
    public static boolean isValidFilePath(Path path) {
        if (path == null) {
            return false;
        }
        return Files.isRegularFile(path) && Files.exists(path);
    }

    public static boolean isNotValidFilePath(Path path) {
        return !isValidFilePath(path);
    }
}