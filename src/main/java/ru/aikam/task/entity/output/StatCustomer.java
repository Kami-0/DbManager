package ru.aikam.task.entity.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс сущность покупателя в запросе типа stat
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatCustomer {
    private String name;
    private List<StatUniqueProduct> purchases = new LinkedList<>();
    private int totalExpenses;
}
