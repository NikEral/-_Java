import java.util.Scanner;
import java.util.HashMap;
import java.util.*;
public class Main {
    private static final NavigableMap<Integer, String> units;
    static {
        NavigableMap<Integer, String> initMap = new TreeMap<>();
        initMap.put(1000, "M");
        initMap.put(900, "CM");
        initMap.put(500, "D");
        initMap.put(400, "CD");
        initMap.put(100, "C");
        initMap.put(90, "XC");
        initMap.put(50, "L");
        initMap.put(40, "XL");
        initMap.put(10, "X");
        initMap.put(9, "IX");
        initMap.put(5, "V");
        initMap.put(4, "IV");
        initMap.put(1, "I");
        units = Collections.unmodifiableNavigableMap(initMap);
    }
    private static int romanToArabic(String roman) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int end = roman.length()-1;
        char[] arr = roman.toCharArray();
        int arabian;
        int result = map.get(arr[end]);
        for (int i = end-1; i >=0; i--) {
            arabian = map.get(arr[i]);

            if (arabian < map.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }
    public static String arabicToRoman(int number) {
        if (number >= 4000 || number <= 0)
            return null;
        StringBuilder result = new StringBuilder();
        for(Integer key : units.descendingKeySet()) {
            while (number >= key) {
                number -= key;
                result.append(units.get(key));
            }
        }
        return result.toString();
    }
    public static void main(String[] args) {
        //scanning and parsing the string
        Scanner in = new Scanner(System.in);
        String input[] = in.nextLine().split(" ");

        //regexes for parts of string
        String arabic = "^[0-9]+$";
        String roman = "[I|V|X|L|C|D|M]*";
        String signs = "[+-//*/]";

        //no comment
        int a = 0;
        int b = 0;
        char sign;
        boolean isArabic = false;

        int countArabic = 0;
        int countRoman = 0;
        int countSign = 0;
        for(int i = 0; i < input.length; i++){
            if(input[i].matches(arabic)) countArabic++;
            if(input[i].matches(roman)) countRoman++;
            if(input[i].matches(signs)) countSign++;
        }
        if(countSign == 0) {
            System.out.println("throws Exception //т.к. строка не является математической операцией ");
            return;
        }
        if(countSign != 1 || (countRoman + countArabic) != 2) {
            System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            return;
        }


        //checking for format of input
        if(input[0].matches(arabic) && input[1].matches(signs) && input[2].matches(arabic)){
            a = Integer.parseInt(input[0]);
            sign = input[1].charAt(0);
            b = Integer.parseInt(input[2]);
            isArabic = true;
        } else if (input[0].matches(roman) && input[1].matches(signs) && input[2].matches(roman)) {
            a = romanToArabic(input[0]);
            sign = input[1].charAt(0);
            b = romanToArabic(input[2]);
        } else{
            System.out.println("throws Exception// т.к. используются одновременно разные системы счисления ");return;
        }

        int result = 0;
        switch (sign) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/': result = a / b; break;
            default: System.out.println("throws Exception// вообще понятия не имею что могло пойти не так на этом этапе"); return;
        }
        if(isArabic)
            System.out.println(result);
        else System.out.println(arabicToRoman(result) == null ? "throws Exception //т.к. в римской системе нет отрицательных чисел" : arabicToRoman(result));

    }
}
