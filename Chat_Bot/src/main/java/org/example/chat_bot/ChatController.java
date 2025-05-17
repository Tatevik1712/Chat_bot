package org.example.chat_bot;
/**Саргсян Татев ИВТ-23*/
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Главный контроллер чата.
 * Управляет логикой взаимодействия пользователя с интерфейсом:
 * отправка сообщений, ответы бота, отображение сообщений, сохранение и загрузка истории.
 */
public class ChatController {

    @FXML
    private VBox chatArea;  // Область, где отображаются сообщения

    @FXML
    private TextField messageField;  // Поле ввода сообщения

    @FXML
    private Button sendButton;  // Кнопка отправки


    private final Bot bot = new Bot();  // Экземпляр бота
    private static String username = "Пользователь";  // Имя пользователя

    // Хранит историю сообщений (в оперативной памяти) //название пер-ой
    private final List<String> messageHistory = new ArrayList<>(); //отдельно в класс
    // Файл для записи истории сообщений
    //private final File historyFile = new File("message_history.txt");
    private File historyFile;

    /**
     * Устанавливает имя пользователя, заданное в окне входа.
     * @param name имя пользователя
     */
    public static void setUsername(String name) {
        username = name;
    }

    /**
     * Инициализация контроллера.
     * Загружает историю сообщений и настраивает обработчики кнопок и клавиш.
     */
    @FXML
    private void initialize() {
        // Проверяем, существует ли файл с историей для этого пользователя
        historyFile = new File(username + "_history.txt");

        // Загружаем историю сообщений при запуске
        //loadHistory();

        // Обработчик кнопки
        sendButton.setOnAction(event -> sendMessage());

        // Обработка нажатия клавиш Enter и Ctrl + Enter
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                event.consume();
                sendMessage();
            }
        });
    }

    /**
     * Отправляет сообщение пользователя и получает ответ от бота.
     */
    private void sendMessage() {
        String message = messageField.getText().trim();  // Удалить лишние пробелы
        if (!message.isEmpty()) {
            addMessage(message, "user-message", username);  // Сообщение пользователя
            String botResponse = getBotReply(message);       // Ответ бота
            addMessage(botResponse, "bot-message", "Бот");   // Добавляем его в чат
            messageField.clear();  // Очистить поле ввода
        }
    }


    /**
     * Генерирует ответ от бота на основе сообщения пользователя.
     * @param message сообщение пользователя
     * @return ответ бота
     */
    private String getBotReply(String message) {
        // Проверка запроса на погоду
        String weatherResponse = bot.getWeatherResponse(message);
        if (!weatherResponse.equals("Извините, я не понял этот запрос.")) {
            return weatherResponse;
        }

        // Проверка на математические выражения
        String mathResponse = bot.handleMathCommand(message);
        if (!mathResponse.startsWith("Не понял команду")) { // Если команда распознана
            return mathResponse;
        }
        // Ответ на общие фразы
        return bot.getResponse(message);
    }



    /**
     * Добавляет сообщение в чат и сохраняет его в истории.
     * @param message текст сообщения
     * @param styleClass CSS-класс оформления (бот / пользователь)
     * @param author автор сообщения
     */
    private void addMessage(String message, String styleClass, String author) {
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add(styleClass);
        messageLabel.setWrapText(true);       // Перенос по строкам
        messageLabel.setMaxWidth(350);        // Ширина облака сообщения

        HBox wrapper = new HBox();
        wrapper.setMaxWidth(Double.MAX_VALUE);
        wrapper.setFillHeight(true);

        Region spacer = new Region();         // Элемент для выравнивания
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Выравнивание по правому краю для пользователя, по левому — для бота
        if (styleClass.equals("user-message")) {
            wrapper.setAlignment(Pos.TOP_RIGHT);
            wrapper.getChildren().addAll(spacer, messageLabel);
        } else {
            wrapper.setAlignment(Pos.TOP_LEFT);
            wrapper.getChildren().addAll(messageLabel, spacer);
        }

        chatArea.getChildren().add(wrapper);  // Добавляем сообщение в чат

        // Добавляем сообщение в список истории с меткой времени
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        messageHistory.add(String.format("[%s] %s: %s", timestamp, author, message));

        scrollToBottom();  // Прокрутка вниз
    }

    /**
     * Автоматически прокручивает чат к последнему сообщению.
     */
    private void scrollToBottom() {
        chatArea.layout();  // Обновить интерфейс
        if (!chatArea.getChildren().isEmpty()) {
            chatArea.getChildren().get(chatArea.getChildren().size() - 1).requestFocus();
        }
    }
}
