import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(matches("123", "123"));
    }
    public static boolean matches(String regex, String text){
        return Pattern.compile(regex).matcher(text).matches();
    }
}
