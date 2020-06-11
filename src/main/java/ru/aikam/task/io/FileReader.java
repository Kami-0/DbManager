package ru.aikam.task.io;

import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.DbManagerInterface;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

@Slf4j
public final class FileReader {
    private final DbManagerInterface dbManager;

    public FileReader(DbManagerInterface dbManager) {
        this.dbManager = dbManager;
    }

    public String getContentFromFile(Path path) {
        if (PathValidator.isNotValidFilePath(path)) {
            log.error("Invalid path to input file: {}", path);
            dbManager.onRuntimeException("Invalid path to input file");
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(path);
            if (!scanner.hasNext()) {
                log.error("Empty input file: {}", path);
                dbManager.onRuntimeException("Empty input file");
            }
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (IOException ex) {
            log.error("Input file unavailable ", ex);
            dbManager.onRuntimeException("Input file unavailable");
        }
        return stringBuilder.toString();
    }
}
