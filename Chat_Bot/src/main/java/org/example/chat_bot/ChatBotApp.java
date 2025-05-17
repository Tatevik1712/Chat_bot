/**Саргсян Татев ИВТ-23*/

package org.example.chat_bot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

/**
 * Главный класс приложения, расширяющий javafx.application. Application.
 * Запускает приложение и отображает окно авторизации.
 */
public class ChatBotApp extends Application {

    private ChatController controller; // Ссылка на контроллер чата для сохранения истории сообщений

    /**
     * Метод start запускает приложение и отображает окно авторизации.
     * Загружает FXML для экрана логина и применяет CSS-стили.
     *
     * @param stage Основная сцена, которая будет отображать окно приложения.
     * @throws IOException если не удается загрузить FXML или стили
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Загружаем FXML файл для окна авторизации (login.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(ChatBotApp.class.getResource("login.fxml"));
        // Создаем сцену с указанными размерами
        Scene scene = new Scene(fxmlLoader.load(), 440, 800);
        // Применяем стили для окна авторизации (styleLogin.css)
        scene.getStylesheets().add(getClass().getResource("styleLogin.css").toExternalForm());

        stage.setTitle("SMART HEALTH - Авторизация");
        stage.setScene(scene);
        stage.show();

        // Получаем ссылку на контроллер окна авторизации
        LoginController loginController = fxmlLoader.getController();
        loginController.setOnLogin((username) -> {
            ChatController.setUsername(username);

            // Загружаем главный чат
            try {
                FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("chat.fxml"));
                Scene chatScene = new Scene(chatLoader.load(), 440, 800);
                stage.setScene(chatScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Метод stop вызывается при закрытии приложения.
     * Здесь мы сохраняем историю сообщений, если контроллер чата был инициализирован.
     */
    @Override
    public void stop() {
        try {
            // Проверяем, был ли контроллер чата и сохраняем историю сообщений перед выходом
            if (controller != null) {
                //controller.saveHistory(); // Сохраняем историю сообщений
            }
        } catch (Exception e) {
            e.printStackTrace(); // Выводим ошибку, если не удалось сохранить историю
        }
    }


    /**
     * Метод для переключения на главный экран чата после успешного логина.
     *
     * @param username Имя пользователя для чата
     */
    public void showChatScreen(String username) {
        try {
            // Загружаем FXML для главного экрана чата
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent root = loader.load();

            // Получаем контроллер главного экрана чата
            controller = loader.getController();
            controller.setUsername(username); // Устанавливаем имя пользователя

            // Загружаем историю сообщений перед переходом
            //controller.loadHistory();

            // Создаем сцену для чата и устанавливаем на сцену
            Scene chatScene = new Scene(root, 440, 800);
            Stage stage = new Stage();
            stage.setTitle("Чат");
            stage.setScene(chatScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // В случае ошибки при загрузке чата
            showErrorDialog("Ошибка загрузки чата", "Не удалось загрузить чат.");
        }
    }

    /**
     * Показывает диалог с сообщением об ошибке.
     *
     * @param title Заголовок окна ошибки
     * @param message Сообщение об ошибке
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Основной метод для запуска приложения.
     * Этот метод будет автоматически вызван для старта приложения.
     */
    public static void main(String[] args) {
        launch(); // Запуск приложения
    }
}
