package ru.aikam.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.aikam.task.io.FileReader;
import ru.aikam.task.json.Criterion;
import ru.aikam.task.json.CriterionDeserializer;
import ru.aikam.task.json.input.LastNameCriterion;
import ru.aikam.task.json.input.ProductNameCriterion;
import ru.aikam.task.json.input.SearchOperation;
import ru.aikam.task.service.ArgsParser;

import java.nio.file.Path;

@Slf4j
public class DbManager implements DbManagerInterface {
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
        String userJson = fileReader.getContentFromFile(inputFilePath);

        SearchOperation searchOperation = new SearchOperation();
        searchOperation.addCriterion(new LastNameCriterion("ff"));
        searchOperation.addCriterion(new ProductNameCriterion("ff", 7));

        Gson gsonS = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gsonS.toJson(searchOperation);

        Gson gsonD = new GsonBuilder()
                .registerTypeAdapter(Criterion.class, new CriterionDeserializer())
                .create();
        SearchOperation searchInput = gsonD.fromJson(json, SearchOperation.class);
    }

    public static void main(String[] args) {
        new DbManager(args);
    }

    @Override
    public void onInputValueException(String message) {
        System.out.println(message);
        log.error(message);
        System.exit(0);
    }

    @Override
    public void onRuntimeException(String message) {

    }
}
