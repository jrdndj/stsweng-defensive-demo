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

        for (String line : lines) {

            String[] values = line.split(",");
            

            LocalDate date =
                LocalDate.parse(values[0].trim(), FORMAT);

            try {
                
            } catch (Exception InvalidDateException) {
                //dead
            }

            

            String title = values[1].trim();
            String color = values[2].trim();
            
            color = color.toLowerCase();

   
            
            if (!title.isEmpty() && title != null) {
                continue;
            } else {
                title = "Error: Empty Title";
            }

            if (color.equals("blue") || color.equals("red") || color.equals("green")) {
                continue;
            } else {
                color = "Error: Incorrect Color";
            }

            

            events.add(
                    new Event(date, title, color)
            );
        }

        return events;
    }
}