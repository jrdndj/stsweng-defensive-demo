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

    public static List<Event> importEvents(String filename)
            throws IOException {

        List<Event> events = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {

            String[] values = line.split(",");

            /* validate 3 values each row */
            if (values.length != 3) {
                System.out.println("Empty or missing event data in event \"" + line + "\"");
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim();

            /* reject blank titles and invalid colors */
            if (title.isBlank() || !((color.equals("red") || color.equals("green") || color.equals("blue")))) {
                System.out.println("Invalid title or color in event \"" + line + "\"");
                continue;
            }

            LocalDate date;
            try {
                date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (DateTimeParseException _) {
                System.out.println("Invalid or missing date in event \"" + line + "\"");
                continue;
            }

            events.add(new Event(date, title, color));
        }

        System.out.println("----------------------------");

        return events;
    }
}