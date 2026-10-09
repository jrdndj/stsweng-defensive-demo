import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                                System.err.println("Invalid line: " + line);
                                continue;
                        }

                        // if date is in wrong date format
                        try {
                                LocalDate.parse(values[0].trim(), FORMAT);
                        } catch (Exception e) {
                                System.err.println("Invalid date: " + line);
                                continue;
                        }

                        LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);

                        String title = values[1].trim();

                        if (title.isEmpty()) {
                                System.err.println("Invalid title: blank titles not allowed: " + line);
                                continue;
                        }

                        String color = values[2].trim();

                        if (!color.equals("red") && !color.equals("green") && !color.equals("blue")) {
                                System.err.println("Invalid color: only red, green, or blue are allowed: " + line);
                                continue;
                        }

                        events.add(
                                        new Event(date, title, color));
                }

                // print out number of inavlid lines and number of valid lines
                int invalidLines = lines.size() - events.size();
                int validLines = events.size();

                System.out.println();
                System.out.println("Number of invalid lines: " + invalidLines);
                System.out.println("Number of valid lines: " + validLines);
                System.out.println();

                return events;
        }
}