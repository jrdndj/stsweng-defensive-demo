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
        int rejectCount = 0;
        int importCount = 0;

        for (String line : lines) {

            String[] values = line.split(",");
            if (values.length != 3) {
                System.out.println("Invalid Value Length in line: " + line);
                rejectCount++;
                continue;
            }

            LocalDate date;
            try {
                date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Date in line: " + line);
                rejectCount++;
                continue;
            }


            String title = values[1].trim();
            if (title.isEmpty()) {
                System.out.println("Invalid Title in line: " + line);
                rejectCount++;
                continue;
            }

            String color = values[2].trim();
            switch (color) {
                case "red":
                case "blue":
                case "green":
                    break;
                default:
                    System.out.println("Invalid Color in line: " + line);
                    rejectCount++;
                    continue;
            }

            events.add(
                    new Event(date, title, color)
            );
            importCount++;
        }

        System.out.println("Rejected: " + rejectCount);
        System.out.println("Imported: " + importCount);

        return events;
    }
}