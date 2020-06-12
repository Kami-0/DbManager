package ru.aikam.task.app;

/**
 * Интерфейс описывающий DbManager
 *
 * @author Daniil Makarov (Kami)
 */
public interface DbManagerInterface {
    void onInputValueException(String message);

    void onRuntimeException(String message);
}
