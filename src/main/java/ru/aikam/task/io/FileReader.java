package ru.aikam.task.io;

import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.app.DbManagerInterface;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Класс для чтения из файла
 *
 * @author Daniil Makarov (Kami)
 */
@Slf4j
public final class FileReader {
    private final DbManagerInterface dbManager;

    public FileReader(DbManagerInterface dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Извлекает содержимое файла
     *
     * @param path путь до файла
     * @return содержимое файла
     */
    public String getContentFromFile(Path path) {
        if (PathValidator.isNotValidFilePath(path)) {
            dbManager.onRuntimeException("Invalid path to input file " + path);
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(path);
            if (!scanner.hasNext()) {
                dbManager.onRuntimeException("Empty input file " + path);
            }
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(System.lineSeparator());
            }
        } catch (IOException ex) {
            dbManager.onRuntimeException("Input file unavailable " + path);
        }
        return stringBuilder.toString();
    }
}
