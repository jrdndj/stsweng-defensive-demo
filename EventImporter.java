import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventImporter {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Set<String> ALLOWED_COLORS = Set.of("red", "green", "blue");

    public static List<Event> importEvents(String filename) throws IOException {
        List<Event> events = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {
            String[] values = line.split(",");
            if (values.length != 3) {
                System.err.println("Skipped malformed line: " + line);
                continue;
            }

            try {
                LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);
                String title = values[1].trim();
                String color = values[2].trim();

                if (title.isEmpty() || !ALLOWED_COLORS.contains(color.toLowerCase())) {
                    System.err.println("Skipped invalid event: " + line);
                    continue;
                }

                events.add(new Event(date, title, color));
            } catch (Exception e) {
                System.err.println("Skipped line with invalid date: " + line);
            }
        }

        return events;
    }
}
