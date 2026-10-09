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

        int acceptCounter  = 0;
        int j = 0;
        for (String line : lines) {
            j ++;
            String[] values = line.split(",");

            //invalidate 3 vals
            if (values.length != 3) {
                printError("Invalid count of values", j);
                continue;
            }

            // reject missing or invalid dates
            LocalDate date;
            try {
                date = LocalDate.parse(values[0].trim(), FORMAT);
            } catch (DateTimeParseException e)
            {
                printError("Invalid Date", j);
                continue;
            }

            String title = values[1].trim();
            String color = values[2].trim();


            // blank titles
            if (title.isEmpty()){
                printError("Title is empty", j);
                continue;
            }

            // color
            if (!color.equals("red") && !color.equals("green") && !color.equals("blue"))
            {
                printError("Color must be red, green, or blue", j);
                continue;
            }

            events.add(
                    new Event(date, title, color)
            );

            acceptCounter ++;
        }

        System.out.println("\nFINAL REPORT");
        System.out.println("Imported count: " + acceptCounter);
        System.out.println("Rejected count: " + (j - acceptCounter));
        System.out.println();

        return events;
    }

    private static void printError(String msg, int row)
    {
        System.out.println("Error: " + msg + " at row: " + row);
    }
}