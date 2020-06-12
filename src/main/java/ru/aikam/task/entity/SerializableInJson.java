package ru.aikam.task.entity;

/**
 * Интерфейс для классов которые могут сериализоваться в строку json формата
 *
 * @author Daniil Makarov (Kami)
 */
public interface SerializableInJson {
    String toJson();
}
