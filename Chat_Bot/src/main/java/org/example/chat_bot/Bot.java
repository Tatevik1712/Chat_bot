package org.example.chat_bot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.chat_bot.WeatherApi;

/** Саргсян Татев ИВТ-23*/

/**
 * Класс Bot (модель) реализует простую логику ответов на запросы пользователя.
 * Может отвечать на шаблонные фразы и обрабатывать примитивные математические команды.
 */
public class Bot {

    /**
     * Возвращает ответ на стандартные фразы пользователя.
     *
     * @param input входная строка от пользователя
     * @return текст ответа
     */
    public String getResponse(String input) {
        String normalized = input.toLowerCase().trim();

        if (containsApprox(normalized, "привет", 1)) {
            return "Привет, как дела?";
        } else if ((containsApprox(normalized, "который", 1) || containsApprox(normalized, "сколько", 1))
                && (containsApprox(normalized, "час", 1) || containsApprox(normalized, "время", 1) || containsApprox(normalized, "времени", 1))) {
            return "Я не умею говорить время, но могу помочь с другими вопросами!";
        } else {
            return "Извините, я не понял этот запрос.";
        }
    }

    /**
     * Проверяет, содержит ли строка `text` хотя бы одно слово,
     * которое похоже на `target`, с учётом допустимого количества ошибок (опечаток).
     * Использует расстояние Левенштейна для определения "похожести".
     * @param text        строка, в которой нужно искать (например, введённое пользователем сообщение)
     * @param target      слово-образец, с которым сравниваем (например, "привет")
     * @param maxDistance максимальное допустимое расстояние Левенштейна (например, 1 или 2)
     * @return true, если найдено хотя бы одно слово в тексте, которое похоже на target; иначе false
     */
    private boolean containsApprox(String text, String target, int maxDistance) {
        // Разбиваем текст на отдельные слова по пробелам и другим разделителям
        // "\\s+" — регулярное выражение для любого количества пробельных символов (пробелы, табуляции и т.д.)
        for (String word : text.split("\\s+")) {

            // Вычисляем расстояние Левенштейна между словом из текста и целевым словом
            // Если оно меньше или равно допустимому порогу — считаем, что слово "похоже"
            if (levenshtein(word, target) <= maxDistance) {
                return true; // Нашли похожее слово — сразу возвращаем true
            }
        }
        // Если ни одно слово не оказалось достаточно похожим — возвращаем false
        return false;
    }

    /**
     * Вычисляет расстояние Левенштейна между двумя строками.
     * Это минимальное количество операций (вставка, удаление, замена),
     * необходимых для преобразования одной строки в другую.
     * Расстояние Левенштейна — метрика cходства между двумя строковыми последовательностями
     * @param a первая строка
     * @param b вторая строка
     * @return расстояние Левенштейна
     */
    private int levenshtein(String a, String b) {
        // Создаём двумерный массив для хранения промежуточных расстояний
        // dp[i][j] — минимальное расстояние между первыми i символами строки a и j символами строки b
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        // Проходим по всем индексам строк a и b
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {

                // Если i == 0, значит, первая строка пустая — нужно вставить j символов из b
                if (i == 0) {
                    dp[i][j] = j;

                // Если j == 0, значит, вторая строка пустая — нужно удалить i символов из a
                } else if (j == 0) {
                    dp[i][j] = i;

                } else {
                    // Вычисляем "стоимость" замены символа:
                    // если символы совпадают — 0, иначе — 1 (замена)
                    int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

                    // dp[i][j] — это минимум из:
                    // 1. Удаление символа из a (двигаемся вверх в таблице)
                    // 2. Вставка символа в a (двигаемся влево)
                    // 3. Замена (если символы не совпадают)
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1,    // Удаление
                                    dp[i][j - 1] + 1),   // Вставка
                            dp[i - 1][j - 1] + cost       // Замена (или без неё, если символы совпадают)
                    );
                }
            }
        }
        // Результат в правом нижнем углу таблицы — полная "стоимость" преобразования строки a в b
        return dp[a.length()][b.length()];
    }


    /**
     * Обрабатывает математические команды, включая символы (*, +, -, /)
     * и фразы типа "умножь 3 на 5" (с опечатками).
     * @param command команда от пользователя
     * @return результат вычисления или сообщение об ошибке
     */
    public String handleMathCommand(String command) {
        String normalized = command.toLowerCase().trim();

        // Попробуем сначала распознать команды вида "число оператор число", например: 5 * 3
        Pattern symbolPattern = Pattern.compile("(\\d+)\\s*([\\*\\+\\-/])\\s*(\\d+)");
        Matcher matcher = symbolPattern.matcher(normalized);
        if (matcher.find()) {
            try {
                int num1 = Integer.parseInt(matcher.group(1));
                int num2 = Integer.parseInt(matcher.group(3));
                String operator = matcher.group(2);

                return switch (operator) { // "switch - case" - выражение, которое мы можем передать в return
                    case "*" -> String.valueOf(num1 * num2);
                    case "+" -> String.valueOf(num1 + num2);
                    case "-" -> String.valueOf(num1 - num2);
                    case "/" -> num2 != 0 ? String.valueOf(num1 / num2) : "Делить на ноль нельзя!";
                    default -> "Неизвестный оператор.";
                };
            } catch (NumberFormatException e) {
                return "Ошибка при разборе чисел.";
            }
        }

        // Если не найдено — пробуем команду вида "умножь 3 на 5" (с опечатками)
        String[] words = normalized.split("\\s+");
        if (words.length >= 4) {
            String first = words[0];
            String opWord = words[2];

            int distToMultiply = levenshtein(first, "умножь");
            int distToAdd = levenshtein(first, "прибавь");
            int distToSub = levenshtein(first, "вычти");
            int distToDiv = levenshtein(first, "раздели");

            try {
                int num1 = Integer.parseInt(words[1]);
                int num2 = Integer.parseInt(words[3]);

                if (distToMultiply <= 2 && levenshtein(opWord, "на") <= 1) {
                    return String.valueOf(num1 * num2);
                } else if (distToAdd <= 2 && levenshtein(opWord, "и") <= 1) {
                    return String.valueOf(num1 + num2);
                } else if (distToSub <= 2 && levenshtein(opWord, "из") <= 1) {
                    return String.valueOf(num1 - num2);
                } else if (distToDiv <= 2 && levenshtein(opWord, "на") <= 1) {
                    return num2 != 0 ? String.valueOf(num1 / num2) : "Нельзя делить на ноль!";
                }
            } catch (NumberFormatException e) {
                return "Неверные числа в команде.";
            }
        }
        return "Не понял команду. Примеры: 5 * 2, умножь 3 на 4, прибавь 5 и 6.";
    }


    /**
     * Возвращает ответ на запрос о погоде.
     * @param input входная строка от пользователя
     * @return текст с информацией о погоде или сообщение об ошибке
     */
    public String getWeatherResponse(String input) {
        String normalized = input.toLowerCase().trim();

        // Проверка на запрос погоды
        if (containsApprox(normalized, "погода", 1)) {
            // Удаляем всё до слова "погода" включительно
            int index = normalized.indexOf("погода");
            String city = input.substring(index + "погода".length()).trim();

            return WeatherApi.getWeather(city); // Получаем погоду через API
        }
        return "Извините, я не понял этот запрос."; // Если запрос не о погоде
    }
}
