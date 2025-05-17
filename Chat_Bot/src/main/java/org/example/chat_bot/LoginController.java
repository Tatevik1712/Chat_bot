/**Саргсян Татев ИВТ-23*/
package org.example.chat_bot;
// Импорты JavaFX компонентов и стандартных библиотек
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



/**
 * Контроллер экрана входа в приложение.
 * Отвечает за обработку пользовательского ввода логина и пароля,
 * отображение логотипа, а также переход к основному окну чата при успешном входе.
 * Этот контроллер связан с FXML-файлом login.fxml и используется на старте приложения.
 */
public class LoginController {
    // Поле для ввода имени пользователя
    @FXML
    private TextField usernameField;
    // Поле для ввода пароля
    @FXML
    private PasswordField passwordField;
    // Элемент ImageView для отображения логотипа
    @FXML
    private ImageView logoImage;

    private ChatBotApp application; // Ссылка на основной класс приложения для переключения экрана
    // Метод, который будет передавать имя пользователя в ChatController
    private static OnLoginListener onLoginListener;

    /// Метод инициализации вызывается автоматически после загрузки FXML
    @FXML
    public void initialize() {
        // Загружаем изображение логотипа и устанавливаем его в ImageView
        Image img = new Image(getClass().getResource("/org/example/chat_bot/Рисунок1.png").toExternalForm());
        logoImage.setImage(img);
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        if (!username.isEmpty()) {
            // Устанавливаем имя пользователя в ChatController
            ChatController.setUsername(username);

            // Вызываем переданный слушатель для перехода в чат
            if (onLoginListener != null) {
                onLoginListener.onLogin(username);
            }
        }
    }

    // Устанавливаем слушатель для обработки авторизации
    public static void setOnLogin(OnLoginListener listener) {
        onLoginListener = listener;
    }

    // Интерфейс для обработки успешной авторизации
    public interface OnLoginListener {
        void onLogin(String username);
    }

    /**
     * Метод для отображения диалога с сообщением об ошибке.
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
     * Устанавливаем ссылку на основной класс приложения.
     *
     * @param application Экземпляр основного приложения
     */
    public void setApplication(ChatBotApp application) {
        this.application = application;
    }
}