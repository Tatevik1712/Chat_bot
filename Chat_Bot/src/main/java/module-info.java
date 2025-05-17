module org.example.chat_bot {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens org.example.chat_bot to javafx.fxml, com.google.gson;
    exports org.example.chat_bot;
}
