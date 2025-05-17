import org.example.chat_bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**Саргсян Татев ИВТ-23*/

public class BotTest {

    @Test
    void testGreetingResponse() {
        Bot bot = new Bot();
        assertEquals("Привет, как дела?", bot.getResponse("Привет"));
        assertEquals("Привет, как дела?", bot.getResponse("превет")); // с опечаткой
    }

    @Test
    void testTimeQuestionResponse() {
        Bot bot = new Bot();
        assertTrue(bot.getResponse("Который час?").contains("не умею говорить время"));
        assertTrue(bot.getResponse("Сколько времени?").contains("не умею говорить время"));
    }

    @Test
    void testUnknownResponse() {
        Bot bot = new Bot();
        assertEquals("Извините, я не понял этот запрос.", bot.getResponse("Открой книжку"));
    }

    @Test
    void testMathWithSymbols() {
        Bot bot = new Bot();
        assertEquals("30", bot.handleMathCommand("5 * 6"));
        assertEquals("7", bot.handleMathCommand("10 - 3"));
        assertEquals("8", bot.handleMathCommand("4 + 4"));
        assertEquals("2", bot.handleMathCommand("10 / 5"));
        assertEquals("0", bot.handleMathCommand("0 / 5"));
        assertEquals("Делить на ноль нельзя!", bot.handleMathCommand("10 / 0"));
    }

    @Test
    void testMathWithWords() {
        Bot bot = new Bot();
        assertEquals("15", bot.handleMathCommand("умножь 3 на 5"));
        assertEquals("11", bot.handleMathCommand("прибавь 5 и 6"));
        assertEquals("3", bot.handleMathCommand("вычти 5 из 8"));
        assertEquals("2", bot.handleMathCommand("раздели 10 на 5"));
    }

    @Test
    void testMathWithTypos() {
        Bot bot = new Bot();
        assertEquals("15", bot.handleMathCommand("умнож 3 на 5")); // опечатка
        assertEquals("20", bot.handleMathCommand("фмножь 4 на 5")); // опечатка
        assertEquals("11", bot.handleMathCommand("прибав 5 и 6")); // опечатка
        assertEquals("11", bot.handleMathCommand("пребавь 5 и 6")); // опечатка

    }

    @Test
    void testWeatherRequest() {
        Bot bot = new Bot();
        String result = bot.getWeatherResponse("Какая погода в Москве?"); //мы не можем писать в сообщении другие формы слова, кроме изначального Москве|Москва
        assertNotEquals("Извините, я не понял этот запрос.", result);
    }

}

