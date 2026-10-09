import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Event> importEvents(String filename)
            throws IOException {

        List<Event> events = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filename));

        int count = 0;
        int imported = 0;
        int rejected = 0;

        for (String line : lines) {
            count++;

            String[] values = line.split(",");

            if (values.length != 3) {
                System.out.println("Error on line " + count + ": length must be 3 arguments");
                rejected++;
                continue;
            }

            try {
                LocalDate date =
                        LocalDate.parse(values[0].trim(), FORMAT);

                String title = values[1].trim();

                if (title.isEmpty()) {
                    System.out.println("Error on line " + count + ": title is blank");
                    rejected++;
                    continue;
                }

                String color = values[2].trim();
                String[] colors = {"red", "green", "blue"};

                if (!Arrays.asList(colors).contains(color)) {
                    System.out.println("Error on line " + count + ": invalid color");
                    rejected++;
                    continue;
                }

                events.add(
                        new Event(date, title, color)
                );

                imported++;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Error on line " + count + ": invalid date");
                rejected++;
                continue;
            }
        }

        System.out.println("Imported: " + imported + "; Rejected: " + rejected);

        return events;
    }
}