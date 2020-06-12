package ru.aikam.task.io;

import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.app.DbManagerInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Класс для записи данных в файл
 *
 * @author Daniil Makarov (Kami)
 */
@Slf4j
public final class FileWriter {
    private final DbManagerInterface dbManager;

    public FileWriter(DbManagerInterface dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Записывает строку в файл
     *
     * @param data данные которые нужно записать в файл
     * @param path путь до файла
     */
    public void writeToFile(String data, Path path) {
        try (OutputStream file = new FileOutputStream(path.toString())) {
            file.write(data.getBytes());
            file.flush();
        } catch (IOException ex) {
            log.error("Could not open output file: {}", path.toString(), ex);
            dbManager.onInputValueException("Could not open output file " + path);
        }
    }
}
