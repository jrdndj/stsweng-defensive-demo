import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
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

        int accepted = 0;
        int rejected = 0;

        for (String line : lines) {

            String[] values = line.split(",");

            if (values.length != 3) {
                    rejected++;
                    System.out.println("Missing values");
                    continue;
                }
                
                try {
                        LocalDate date =
                                LocalDate.parse(values[0].trim(), FORMAT);

                        if (date == null) {
                        rejected++;
                        System.out.println("Missing date");
                        continue;
                }

                        date.format(FORMAT);
                } catch (Exception e) {
                        rejected++;
                        System.out.println("Incorrect date");
                        continue;
                }

            String title = values[1].trim();
            
            String color = values[2].trim();

            events.add(
                    new Event(date, title, color)
            );
        }

        return events;
    }

    public boolean isValidDate(LocalDate date) {
        String text = date.format(FORMAT);

        
        return false;
    }
}