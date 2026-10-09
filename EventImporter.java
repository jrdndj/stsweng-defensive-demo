import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Event> importEvents(String filename)
            throws IOException {

        List<Event> events = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filename));

        int errorCount = 0;
        int validCount = 0;

        for (String line : lines) {

            String[] values = line.split(",");
        
            try {
                LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (Exception e) {
                System.out.println("Invalid date: " + values[0].trim());
                errorCount++;
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim();
            
            // Check if date is valid
            

            // Invalid colors
            if (!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("green") && !color.equalsIgnoreCase("blue")){
                System.out.println("Invalid color: " + color);
                errorCount++;
                continue;
            }

            // Title not empty
            if (title.isEmpty()) {
                System.out.println("Title is empty");
                errorCount++;
                continue;
            } 
                
            // Check if all values r there
            if (date == null || title == null || color == null) {
                System.out.println("Missing values: " + line);
                errorCount++;
                continue;
            }

            events.add(
                new Event(date, title, color)
            );
            validCount++;
        }

        System.out.println("Number of valid events: " + validCount);
        System.out.println("Number of errors: " + errorCount);
        

        return events;
    }
}