package ru.aikam.task.json;

import com.google.gson.*;
import ru.aikam.task.entity.Criterion;
import ru.aikam.task.entity.input.BadCustomersCriterion;
import ru.aikam.task.entity.input.LastNameCriterion;
import ru.aikam.task.entity.input.ProductExpensesCriterion;
import ru.aikam.task.entity.input.ProductNameCriterion;

import java.lang.reflect.Type;

/**
 * Класс для десериализации класса Criterion на дочернии классы
 *
 * @author Daniil Makarov (Kami)
 */
public class CriterionDeserializer implements JsonDeserializer<Criterion> {
    @Override
    public Criterion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String criterionType = jsonObject.get("type").getAsString();
        switch (criterionType) {
            case "badCustomers":
                return (BadCustomersCriterion) jsonDeserializationContext.deserialize(jsonObject, BadCustomersCriterion.class);
            case "lastName":
                return (LastNameCriterion) jsonDeserializationContext.deserialize(jsonObject, LastNameCriterion.class);
            case "productExpenses":
                return (ProductExpensesCriterion) jsonDeserializationContext.deserialize(jsonObject, ProductExpensesCriterion.class);
            case "productName":
                return (ProductNameCriterion) jsonDeserializationContext.deserialize(jsonObject, ProductNameCriterion.class);
            default:
                return new Criterion();
        }
    }
}
