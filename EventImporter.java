import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static boolean dateCheck(String input){
        try{
            LocalDate.parse(input.trim(), FORMAT);
            return true;
        }catch(DateTimeParseException e){
            System.out.println(input + " is not a valid date");
            return false;
        }
    }

    public static boolean eventColorCheck(String input, String thing){
        if(!thing.equals("event") && !thing.equals("color"))
            return false;
        if (input.trim().length() == 0){
            System.out.println("ERROR! Empty" + thing + " name.");
            return false;
        }
        if(thing.equals("color") && (!input.trim().equals("red") &&
                !input.trim().equals("green") &&
                !input.trim().equals("blue"))){
            System.out.println("ERROR! Color can only be red, green, or blue.");
            return false;
        }
        return true;
    }


    public static List<Event> importEvents(String filename)
            throws IOException {

        int countSucceed = 0;
        int countFail = 0;

        List<Event> events = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {

            String[] values = line.split(",");

            if(values.length != 3){
                System.out.println("ERROR! Line requires exactly 3 inputs.");
                countFail += 1;
                continue;
            }


            if(!eventColorCheck(values[1], "event")) {
                countFail += 1;
                continue;
            }
            if(!eventColorCheck(values[2], "color")) {
                countFail += 1;
                continue;
            }
            LocalDate date;

            if(dateCheck(values[0].trim())) {
                date =
                        LocalDate.parse(values[0].trim(), FORMAT);
            }
            else{
                countFail += 1;
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim();

            events.add(
                    new Event(date, title, color)
            );
            countSucceed += 1;
        }

        System.out.println("Success count: " + countSucceed);
        System.out.println("Failed count: " + countFail);
        return events;
    }
}