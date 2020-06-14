package ru.aikam.task.entity.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Класс сущность уникального товара в запросе типа stat
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatUniqueProduct {
    private String name;
    private int expenses;

    public static List<StatUniqueProduct> valuesOf(Map<String, Integer> productsMap) {
        List<StatUniqueProduct> statUniqueProductList = new LinkedList<>();
        productsMap.forEach((name, expense) -> statUniqueProductList.add(new StatUniqueProduct(name, expense)));
        return statUniqueProductList;
    }
}
