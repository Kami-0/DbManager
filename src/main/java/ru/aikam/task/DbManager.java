package ru.aikam.task;

import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.io.FileReader;
import ru.aikam.task.service.ArgsParser;

import java.nio.file.Path;

/**
 * Класс предоставляющей сервис работы с данными в БД
 *
 * @author Kami
 */
@Slf4j
public class DbManager implements DbManagerInterface {
    private static final int ERROR_STATUS = 0;
    private final TransactionType transactionType;
    private final Path inputFilePath;
    private final Path outputFilePath;

    public DbManager(String[] args) {
        String[] argv = new String[3];
        argv[0] = "stat";
        argv[1] = "C:\\Users\\Kami\\Проекты\\DbManager\\test.json";
        argv[2] = "test2.json";
        ArgsParser argsParser = new ArgsParser(argv, this);
        this.transactionType = argsParser.getTransactionType();
        this.inputFilePath = argsParser.getInputFilePath();
        this.outputFilePath = argsParser.getOutputFilePath();

        FileReader fileReader = new FileReader(this);
        String requestJson = fileReader.getContentFromFile(inputFilePath);

    }

    public static void main(String[] args) {
        new DbManager(args);
    }

    /**
     * Метод при ошибке разбора входных параметров, отправляет сообщение об ошибке пользователю и завершает работу
     *
     * @param message сообщение пользователю
     */
    @Override
    public void onInputValueException(String message) {
        System.out.println(message);
        log.error(message);
        System.exit(ERROR_STATUS);
    }

    /**
     * Метод при ошибке во время выполнения программы
     *
     * @param message сообщение пользователю
     */
    @Override
    public void onRuntimeException(String message) {

    }
}
