package ru.aikam.task.entity;

/**
 * Интерфейс для классов которые могут сериализоваться в строку json формат
 */
public interface SerializableInJson {
    public String toJson();
}
