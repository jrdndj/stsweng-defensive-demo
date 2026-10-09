// Andrea Bayos & Lanz Bulabos

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

        for (String line : lines) {

        try {
                String[] values = line.split(",");
                int arrayLength = 3
        } catch (Exception e){
                System.out.println(filename);
        }

            LocalDate date =
                    LocalDate.parse(values[0].trim(), FORMAT);

            String title = values[1].trim();
            String color = values[2].trim();

            /* 
            try ()
            */

            /* if date == invalid format || date == NULL  {
                  reason = "missing or invalid date"
               }
               if title == NULL {
                  reason = "blank title"
               }
               if color != blue, red, green {
                  reason = "invalid color"
               }
            */ 

            events.add(
                    new Event(date, title, color)
            );
        }

        return events;
    }
}