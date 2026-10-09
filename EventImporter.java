import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

        private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        public static List<Event> importEvents(String filename)
                        throws IOException {

                List<Event> events = new ArrayList<>();

                List<String> lines = Files.readAllLines(Path.of(filename));

                for (String line : lines) {

                        String[] values = line.split(",");
                        if (values.length != 3) {
                                System.err.println("Need 3 values per line: date, title, color");
                                continue;
                        }

                        LocalDate date;

                        try {
                                date = LocalDate.parse(values[0].trim(), FORMAT);

                        } catch (DateTimeParseException e) {
                                System.err.println("Invalid date format: " + values[0]);
                                continue;
                        }

                        String title = values[1].trim();
                        String color = values[2].trim();

                        if (title.isBlank()) {
                                System.err.println("Title cannot be empty.");
                                continue;
                        }

                        if (!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("green")
                                        && !color.equalsIgnoreCase("blue")) {
                                System.err.println("Color must be red, green, or blue.");
                                continue;
                        }

                        events.add(
                                        new Event(date, title, color));
                }

                return events;
        }
}