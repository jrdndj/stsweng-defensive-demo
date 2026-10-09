import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventImporter {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(
        "MM/dd/yyyy"
    );

    public static List<Event> importEvents(String filename) throws IOException {
        List<Event> events = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filename));

        Set<String> allowedColors = Set.of("red", "green", "blue");

        int successCtr = 0;
        int failedCtr = 0;

        for (String line : lines) {
            String[] values = line.split(",");

            if (values.length != 3) {
                System.err.println("Values must be 3. Row skipped.");
                failedCtr++;
                continue;
            }

            LocalDate date = null;

            try {
                date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (DateTimeParseException e) {
                System.err.println("Cannot parse date. Row skipped.");
                failedCtr++;
                continue;
            }

            if (date == null) {
                System.err.println("Date is null. Row skipped.");
                failedCtr++;
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim().toLowerCase();

            if (title.isEmpty()) {
                System.out.println("Title is empty. Row skipped.");
                failedCtr++;
                continue;
            }

            if (!allowedColors.contains(color)) {
                System.out.println("Color is not allowed. Row skipped.");
                failedCtr++;
                continue;
            }

            events.add(new Event(date, title, color));
            successCtr++;
        }

        System.out.println("Success Counter: " + successCtr);
        System.out.println("Error Counter: " + failedCtr);

        return events;
    }
}
