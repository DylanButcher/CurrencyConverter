import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;


public class convert {
    private static HashMap<String, Double> currencies = new HashMap<>(); //hashmap which holds currnecy name and its value
    private static Scanner scan = new Scanner(System.in); //allows scanner to be accessed anywhere


    public static void main(String[] args) {
        regFromFile();  //imports value from text file
        currencies.put("GBP", 1.00);// base currency, do not mess with him
        selection(); //sends user to options menu
    }

    private static void selection() {
        Scanner sc = new Scanner(System.in); //had to declare another otherwise it fucked up
        System.out.println("--------------------");
        System.out.println("What would you like to do? : ");
        String input = (sc.nextLine()).toUpperCase();   //changes it all to uppercase so it can compare
        switch (input) {
            case "REG":
                reg();
                break;
            case "CONVERT":
                convert();
                break;
            case "EXIT":
                System.out.println("-------------------- \nBye Bye");
                System.exit(0);
                break;
             default:
                selection();    //if it doesnt recognise, it just goes back to the start
                break;
        }
    }

    private static void regFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("regulate.txt")); //sets up file reader and points to which file
            String z;   //string z holds the current line within text file
            while ((z = reader.readLine()) != null) {   //while z contains text
                String splits[] = z.split(":"); //splits it at the semicolon
                switch (splits[0].toUpperCase()) {  //slot 0 holds the key initials eg eur
                    case "EUR":
                        currencies.put("EUR", Double.parseDouble(splits[1]));  //slot 1 holds the value of said key
                        break;
                    case "USD":
                        currencies.put("USD", Double.parseDouble(splits[1]));
                        break;
                    case "JPY":
                        currencies.put("JPY", Double.parseDouble(splits[1]));
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("error importing values, values have been set. ");   //if it cant find the fule or it fucks up, it just imports some preset ones
            currencies.put("EUR", 1.10);
            currencies.put("USD", 1.24);
            currencies.put("JPY", 134.20);
        }
    }

    private static int currencyOptions() {  //i kept typing this out so i put it into a function for ease of use
        System.out.println("[1]Pound Sterling\n[2]Euro\n[3]US Dollar\n[4]Japaneese Yen");
        return scan.nextInt();
    }

    private static void reg() {
        System.out.println("--------------------");
        double value;
        System.out.println("select which currency you want to regulate: ");
        int x = currencyOptions();
        switch (x) {
            case 1:
                System.out.println("1 POUND WILL ALWAYS EQUAL 1 POUND - DO NOT MESS WITH BASE CURRENCY THANK YOU"); // since its base currency no need to change it
                break;
            case 2:
                System.out.println("How many euros is £1? : ");
                value = scan.nextDouble();
                currencies.replace("EUR", value);
                break;
            case 3:
                System.out.println("How many dollars is £1? : ");
                value = scan.nextDouble();
                currencies.replace("USD", value);
                break;
            case 4:
                System.out.println("How many yen is £1? : ");
                value = scan.nextDouble();
                currencies.replace("JPY", value);
                break;
        }
        boolean repeat = false;
        do {
            System.out.println("Would you like to enter another?(Y/N)");
            char yeorne;
            yeorne = scan.next().charAt(0);
            if (yeorne == 'y') {
                reg();
            } else if (yeorne == 'n') {
                selection();
            } else {
                System.out.println("invalid, try again");
                repeat = true;
            }
        }while(repeat);
    }

     private static void convert() {
        System.out.println("--------------------");
        System.out.println("Select what currency you're converting from: ");
        int x = currencyOptions();
        double fromCurrency = 0;
        switch (x) {        //depending on what currency they selected, it sets fromcurrency to that keys value
            case 1:
                fromCurrency = currencies.get("GBP");
                break;
            case 2:
                fromCurrency = currencies.get("EUR");
                break;
            case 3:
                fromCurrency = currencies.get("USD");
                break;
            case 4:
                fromCurrency = currencies.get("JPY");
                break;

        }
        System.out.println("Select what currency you're converting to: ");
        x = currencyOptions();
        double toCurrency = 0;
        switch (x) {        // same as above but it sets the key value now to toCurrency
            case 1:
                toCurrency = currencies.get("GBP");
                break;
            case 2:
                toCurrency = currencies.get("EUR");
                break;
            case 3:
                toCurrency = currencies.get("USD");
                break;
            case 4:
                toCurrency = currencies.get("JPY");
                break;
        }
        System.out.println("How much of said currency do you have? :");
        double value = scan.nextDouble();
            value = Math.round((value / fromCurrency) * toCurrency) *100d / 100d;   //rounded to 2 dp cause currencies innit

         System.out.println("you have "+value);
        selection();    //goes back to beginning
    }
}

