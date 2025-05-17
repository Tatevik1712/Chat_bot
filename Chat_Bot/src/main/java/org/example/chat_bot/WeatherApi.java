package org.example.chat_bot;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

//* Класс для работы с API OpenWeatherMap
public class WeatherApi {
    private static final String WEATHER_API_KEY = "63cf2d03ac8c4022d39b14bc73d4d358";

    /* Получает текущую погоду для указанного города
     * @param city название города на русском или английском языке
     * @return строка с описанием погоды или сообщение об ошибке */
    public static String getWeather(String city) {
        try {
            String url = "http://api.openweathermap.org/data/2.5/weather?q="
                    + URLEncoder.encode(city, StandardCharsets.UTF_8)
                    + "&appid=" + WEATHER_API_KEY
                    + "&units=metric&lang=ru";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // Парсинг ответа
            String description = json.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("description").getAsString();
            double temp = json.getAsJsonObject("main").get("temp").getAsDouble();

            return String.format("Погода %s: %s, %.1f°C", city, description, temp);

        } catch (Exception e) {
            return "Не удалось получить погоду. Проверьте название города";
        }
    }
}
