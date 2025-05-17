package org.example.chat_bot;
/**Саргсян Татев ИВТ-23*/

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс MessageHistory управляет историей сообщений в чате.
 */
public class MessageHistory {
    private List<Message> history;

    public MessageHistory() {
        this.history = new ArrayList<>();
    }

    /**
     * Добавляет новое сообщение с текущими датой и временем.
     * @param author  автор сообщения
     * @param message текст сообщения
     */
    public void addMessage(String author, String message) {
        LocalDateTime now = LocalDateTime.now();
        history.add(new Message(author, message, now));
    }

    public void saveHistory() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("messageHistory.txt"))) {
            out.writeObject(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHistory() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("messageHistory.txt"))) {
            history = (List<Message>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getHistory() {
        return history;
    }
}

/**
 * Класс Message представляет отдельное сообщение.
 */
class Message implements Serializable {
    private String author;
    private String message;
    private LocalDateTime timestamp;

    public Message(String author, String message, LocalDateTime timestamp) {
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        // Формат: 09.04.2025 19:30:12
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }
}
