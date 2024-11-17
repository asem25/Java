import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Утилитный класс для работы с регулярными выражениями
*/
public class Main {
    /**
     * Проверяет, соответствует ли указанный текст заданному регулярному выражению.
     *
     * Исправлены ошибки:
     *  1. Использование Boolean вместо примитивного boolean. Использование примитивного типа позволяет избежать дополнительной
     *  упаковке/распаковке, благодаря чему повышается производительность
     *  2. Переменная result избыточна, ее значение сразу же возвращается.
     *  3. Добавлена проверка на null
     *
     * @param regex регулярное выражение, которое нужно скомпилировать
     * @param text текст, который нужно проверить на соответствие
     * @return {@code true}, если текст соответствует регулярному выражению, {@code false} в противном случае
     * @throws IllegalArgumentException если {@code regex} или {@code text} равны {@code null},
     *                                  либо если регулярное выражение {@code regex} недействительно
     */
    public static boolean matches(String regex, String text){
        if (regex == null || text == null) {
            throw new IllegalArgumentException("Аргументы не должны быть null");
        }
        return Pattern.compile(regex).matcher(text).matches();
    }
}
