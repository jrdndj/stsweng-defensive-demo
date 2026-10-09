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

            if(values.length != 3) {
                System.out.println("`values` does not have 3 elements: " + values.length + "\n");
                continue;
            }

            LocalDate date;
            try {
                date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage() + "\n");
                continue;
            }

            String title = values[1].trim();
            if(title.isEmpty()) {
                System.out.println("Title is empty" + "\n");
                continue;
            }
            String color = values[2].trim();
            String[] expectedColors = {"red", "green", "blue"};
            boolean colorMatches = false;
            for(String expectedColor : expectedColors) {
                if(color.equalsIgnoreCase(expectedColor)) {
                    //System.out.println("Invalid color: " + color);
                    colorMatches = true;
                }
            }
            if(!colorMatches) {
                System.out.println("Invalid color: " + color + "\n");
                continue;
            }

            events.add(
                    new Event(date, title, color)
            );
        }

        return events;
    }
}