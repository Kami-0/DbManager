package ru.aikam.task.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс сущность представляющий критерий запроса от пользователя
 *
 * @author Daniil Makarov (Kami)
 */
@Getter
@Setter
public abstract class Criterion {
    public String type;

    public abstract boolean isIncomplete();
}
