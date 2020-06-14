package ru.aikam.task.app;

import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.api.CriterionHandler;
import ru.aikam.task.entity.input.SearchOperation;
import ru.aikam.task.entity.input.StatOperationCriterion;
import ru.aikam.task.entity.output.ExceptionResponse;
import ru.aikam.task.entity.output.SearchResponse;
import ru.aikam.task.entity.output.StatResponse;
import ru.aikam.task.exception.InvalidDateRangeException;
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

    private DbManager(String[] args) {
        ArgsParser argsParser = new ArgsParser(args, this);

        this.inputFilePath = argsParser.getInputFilePath();
        this.outputFilePath = argsParser.getOutputFilePath();

        TransactionType transactionType = argsParser.getTransactionType();
        String requestJson = getUserJson();
        handleJsonTransaction(requestJson, transactionType);
    }

    public static void main(String[] args) {
        new DbManager(args);
    }

    /**
     * Метод для обработки транзакций
     *
     * @param json            строка в формате json
     * @param transactionType тип транзакции
     */
    private void handleJsonTransaction(String json, TransactionType transactionType) {
        CriterionHandler criterionHandler = new CriterionHandler();
        switch (transactionType) {
            case SEARCH:
                try {
                    SearchOperation searchOperation = SearchOperation.fromJson(json);
                    if (searchOperation.isEmptyCriteriaList()) {
                        onRuntimeException("Request empty or incorrect format");
                    }
                    if (searchOperation.isIncompleteCriteriaList()) {
                        onRuntimeException("Criteria in search query are corrupted");
                    }
                    SearchResponse response = criterionHandler.handleSearchOperation(searchOperation);
                    writeResult(response.toJson());
                    break;
                } catch (Exception ex) {
                    onRuntimeException("No criterion type");
                }
            case STAT:
                try {
                    StatOperationCriterion statOperation = StatOperationCriterion.fromJson(json);
                    if (statOperation.isIncomplete()) {
                        onRuntimeException("Request empty or incorrect format");
                    }
                    StatResponse response = criterionHandler.handleStatOperation(statOperation);
                    writeResult(response.toJson());
                } catch (InvalidDateRangeException ex) {
                    onRuntimeException(ex.getMessage());
                } catch (JsonSyntaxException ex) {
                    onRuntimeException("Wrong date format");
                }
                break;
        }
    }

    /**
     * Метод записывает результат в файл
     *
     * @param data данные
     */
    private void writeResult(String data) {
        FileWriter fileWriter = new FileWriter(this);
        fileWriter.writeToFile(data, outputFilePath);
        log.info("The data is written to the output file");
    }

    /**
     * Метод получает json запрос пользователя
     *
     * @return json запрос пользователя
     */
    private String getUserJson() {
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
        writeResult(new ExceptionResponse(message).toJson());
        System.exit(ERROR_STATUS);
    }
}
