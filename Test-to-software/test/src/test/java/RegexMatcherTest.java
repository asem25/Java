import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegexMatcherTest {
    @Test
    void testExactMatch() {
        // Проверяем точное соответствие текста регулярному выражению
        String regex = "hello";
        String text = "hello";
        assertTrue(Main.matches(regex, text));
    }

    @Test
    void testPartialMatch() {
        // Регулярное выражение соответствует только части строки
        String regex = "hello";
        String text = "hello world";
        assertFalse(Main.matches(regex, text));
    }
    @Test
    void testEmptyStrings() {
        // Проверяем пустую строку и пустое регулярное выражение
        String regex = "";
        String text = "";
        assertTrue(Main.matches(regex, text));

        // Проверяем пустую строку с непустым регулярным выражением
        regex = "hello";
        assertFalse(Main.matches(regex, ""));
    }
    @Test
    void testNullValues() {
        // Проверяем случай с null значением строки
        String regex = "hello";
        String text1 = null;

        assertThrows(NullPointerException.class, () -> Main.matches(regex, text1));

        // Проверяем случай с null значением регулярного выражения
        String nullRegex = null;
        String text2 = "hello";

        assertThrows(NullPointerException.class, () -> Main.matches(nullRegex, text2));
    }
    @Test
    void testWhitespace() {
        // Проверка на пробелы
        String regex = "\\s+";
        String text = "   "; // Только пробелы
        assertTrue(Main.matches(regex, text));

        text = "text with spaces";
        assertFalse(Main.matches(regex, text));
    }
}
