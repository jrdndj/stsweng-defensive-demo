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

        int importedCount = 0;
        int rejectedCount = 0;

        for (String line : lines) {

            String[] values = line.split(",");
        
            System.out.printf("\nCurrent record: %s\n", line);

            if (values.length != 3) {
                System.out.println("Less or more than 3 values read. Skipping record...");
                rejectedCount++;
                continue;
            }
        
            LocalDate date;

            try {
                date =
                    LocalDate.parse(values[0].trim(), FORMAT);
            } catch (Exception e) {
                System.out.println("Invalid date. Skipping record...");
                rejectedCount++;
                continue;
            }
            
            String title = values[1].trim();

            if (title == "") {
                System.out.println("Blank title. Skipping record...");
                rejectedCount++;
                continue;
            }

            String color = values[2].trim();
            if (!color.equals("red") && !color.equals("green") && !color.equals("blue")) {
                System.out.println("Invalid color. Skipping record...");
                rejectedCount++;
                continue;
            }

            events.add(
                    new Event(date, title, color)
            );

            System.out.print("Record successfully imported.\n");
            importedCount++;
        }

        System.out.printf("\nSuccesfully imported %d records, rejected %d records.\n\n", importedCount, rejectedCount);

        return events;
    }
}