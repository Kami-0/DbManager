package ru.aikam.task.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.app.DbManagerInterface;
import ru.aikam.task.app.TransactionType;
import ru.aikam.task.io.PathValidator;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для парсинга входных параметров в класс типа DbManagerInterface
 *
 * @author Daniil Makarov (Kami)
 */
@Slf4j
public final class ArgsParser {
    private final static int ARGS_NUMBER = 3;
    private final static int TRANSACTION_TYPE_INDEX = 0;
    private final static int INPUT_FILE_INDEX = 1;
    private final static int OUTPUT_FILE_INDEX = 2;
    @Getter
    private TransactionType transactionType;
    @Getter
    private Path inputFilePath;
    @Getter
    private Path outputFilePath;

    public ArgsParser(String[] args, DbManagerInterface dbManager) {
        if (args.length != ARGS_NUMBER) {
            dbManager.onInputValueException("Incorrect number of parameters:" + args.length + " necessary:" + ARGS_NUMBER);
        }

        String userTransactionType = args[TRANSACTION_TYPE_INDEX];
        try {
            this.transactionType = TransactionType.valueOf(userTransactionType.toUpperCase());
        } catch (IllegalArgumentException ex) {
            dbManager.onInputValueException("Incorrect input parameters: " + userTransactionType);
        }

        String userInputPathString = args[INPUT_FILE_INDEX];
        Path userInputPath = Paths.get(userInputPathString).normalize();
        boolean inputPathIsValid = PathValidator.isValidFilePath(userInputPath);
        if (!inputPathIsValid) {
            dbManager.onInputValueException("Incorrect input file: " + userInputPathString);
        }
        this.inputFilePath = userInputPath;

        String userOutputPathString = args[OUTPUT_FILE_INDEX];
        Path userOutputPath = Paths.get(userOutputPathString).normalize();
        boolean outputPathIsValid = PathValidator.isValidFilePath(userOutputPath);
        if (!outputPathIsValid) {
            dbManager.onInputValueException("Incorrect output file: " + userOutputPathString);
        }
        this.outputFilePath = userOutputPath;
        log.info("Parsing input parameters has ended");
    }
}
