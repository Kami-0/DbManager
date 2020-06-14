package ru.aikam.task.entity.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import ru.aikam.task.entity.SerializableInJson;

/**
 * Класс сущность ошибки
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
public class ExceptionResponse implements SerializableInJson {
    private String type = "error";
    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(this);
    }
}
