package org.example.chat_bot;

import javafx.application.Application;

/**
 * Класс Launcher служит в качестве точки входа (entry point) для JavaFX-приложения.
 * Это необходимо, поскольку при упаковке JavaFX-приложений в JAR-файл
 * основной класс должен иметь простой статический метод main.
 *
 * Здесь явно вызывается метод Application.launch(), чтобы запустить JavaFX-приложение.
 * Основной класс JavaFX-приложения указан как ChatBotApp.class.
 */
public class Launcher {
    /**
     * Метод main — стандартная точка входа в Java-приложение.
     * Он вызывает launch() и передаёт в него класс ChatBotApp,
     * который наследуется от javafx.application.Application.
     *
     * @param args аргументы командной строки (если есть)
     */
    public static void main(String[] args) {
        Application.launch(ChatBotApp.class, args);
    }
}
