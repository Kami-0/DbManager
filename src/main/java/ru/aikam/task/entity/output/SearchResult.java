package ru.aikam.task.entity.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс сущность результата критерия запроса типа search
 *
 * @author Daniil Makarov (Kami)
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String lastName;
    private String firstName;
}
