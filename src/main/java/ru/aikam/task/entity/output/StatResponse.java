package ru.aikam.task.entity.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aikam.task.entity.SerializableInJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс сущность ответа на запрос от пользователя типа stat
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatResponse implements SerializableInJson {
    private String type = "stat";
    private int totalDays;
    private List<StatCustomer> customers = new LinkedList<>();
    private int totalExpenses;
    private double avgExpenses;

    public void addCustomer(StatCustomer statCustomer) {
        customers.add(statCustomer);
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(this);
    }

}
