package ru.aikam.task.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileWriter {

    public static void writeToFile(String data, Path path) {
        try (OutputStream file = new FileOutputStream(path.toString())) {
            file.write(data.getBytes());
            file.flush();
        } catch (IOException ex) {
            log.error("Не удалось открыть файл {}", path.toString(), ex);
        }
    }
}
