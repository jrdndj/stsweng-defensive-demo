import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeParseException;


public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Event> importEvents(String filename)
            throws IOException {

        List<Event> events = new ArrayList<>();
        int imported = 0;
        int rejected = 0;

        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {

            String[] values = line.split(",");

            if (values.length != 3) {
                System.out.println("Rejected (expected 3 values): " + line);
                rejected++;
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim();

                if (title.isEmpty()) {
                        System.out.println("Rejected (blank title): " + line);
                        rejected++;
                        continue;
                }

                if (!color.equals("red") && !color.equals("green") && !color.equals("blue")) {
                        System.out.println("Rejected (invalid color '" + color + "'): " + line);
                        rejected++;
                        continue;
                }
                try {
                        LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);
                        events.add(new Event(date, title, color));
                        imported++;
                } catch (DateTimeParseException e) {
                        System.out.println("Rejected (missing/invalid date): " + line);
                        rejected++;
                }
        }

        System.out.println("Imported: " + imported);
        System.out.println("Rejected: " + rejected);
        return events;
    }
}