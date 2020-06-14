package ru.aikam.task.app;

import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.api.CriterionHandler;
import ru.aikam.task.entity.input.SearchOperation;
import ru.aikam.task.entity.input.StatOperationCriterion;
import ru.aikam.task.entity.output.ExceptionRequest;
import ru.aikam.task.entity.output.SearchOutputRequest;
import ru.aikam.task.io.FileReader;
import ru.aikam.task.io.FileWriter;
import ru.aikam.task.service.ArgsParser;

import java.nio.file.Path;

/**
 * Класс предоставляющей сервис работы с данными в БД
 *
 * @author Daniil Makarov (Kami)
 */
@Slf4j
public class DbManager implements DbManagerInterface {
    private static final int ERROR_STATUS = 0;
    private final Path inputFilePath;
    private final Path outputFilePath;

    public DbManager(String[] args) {
        String[] argv = new String[3];
        argv[0] = "search";
        argv[1] = "C:\\Users\\Kami\\Проекты\\DbManager\\test.json";
        argv[2] = "test2.json";
        ArgsParser argsParser = new ArgsParser(argv, this);

        this.inputFilePath = argsParser.getInputFilePath();
        this.outputFilePath = argsParser.getOutputFilePath();

        TransactionType transactionType = argsParser.getTransactionType();
        String requestJson = getUserJson();
        handleJsonTransaction(requestJson, transactionType);
    }

    public static void main(String[] args) {
        new DbManager(args);
    }

    public void handleJsonTransaction(String json, TransactionType transactionType) {
        CriterionHandler criterionHandler = new CriterionHandler();
        switch (transactionType) {
            case SEARCH:
                try {
                    SearchOperation searchOperation = SearchOperation.fromJson(json);
                    //Проверяем на целостность десериализованный запрос
                    if (searchOperation.isEmptyCriteriaList()) {
                        onRuntimeException("Request empty or incorrect format");
                    }
                    //Проверяем на целостность десериализованные критерие в запросе
                    if (searchOperation.isIncompleteCriteriaList()) {
                        onRuntimeException("Criteria in search query are corrupted");
                    }
                    SearchOutputRequest request = criterionHandler.handleSearchOperation(searchOperation);
                    writeResult(request.toJson());
                    break;
                } catch (Exception ex) {
                    onRuntimeException("No criterion type");
                }
            case STAT:
                criterionHandler.handleStatOperation(StatOperationCriterion.fromJson(json));
        }
    }

    /**
     * Метод записывает результат в файл
     *
     * @param data данные
     */
    public void writeResult(String data) {
        FileWriter fileWriter = new FileWriter(this);
        fileWriter.writeToFile(data, outputFilePath);
        log.info("The data is written to the output file");
    }

    /**
     * Метод получает json запрос пользователя
     *
     * @return json запрос пользователя
     */
    public String getUserJson() {
        FileReader fileReader = new FileReader(this);
        String data = fileReader.getContentFromFile(inputFilePath);
        log.info("Content from file successfully retrieved");
        return data;
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
        log.error(message);
        writeResult(new ExceptionRequest(message).toJson());
        System.exit(ERROR_STATUS);
    }
}
