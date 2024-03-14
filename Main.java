


import java.io.*;



public class Main {
    private final String[] numbers = {
            "один",
            "два",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять",
            "десять"
    };
    private final String[] suffix = {
            "дцать",
            "надцать",
            "ста",
            "сот",
            "тысяч"
    };
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Integer intFromConsole = Integer.parseInt(reader.readLine());

        if (intFromConsole < 10000){
            logger(intFromConsole);
        }





    }
    private static void logger(int numb){
        int count = 3;
        for (int i = 1000; i > 0; i /= 10) {
            numb /= i;
            count --;
        }
        if (count == 3){
            System.out.println("число 4-ех значное");

        }else if(count == 2){
            System.out.println("число 3-ух значное");

        }else if(count == 1){
            System.out.println("число 2-ух значное");

        }else if (count == 0){
            System.out.println("число 1-о значное");

        }
    }
}