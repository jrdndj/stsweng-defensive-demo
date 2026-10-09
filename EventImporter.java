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
        try{
                String[] values = line.split(",");

            if(values.length != 3){
                throw new IllegalArgumentException ("Error missing values");
            };
            LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);

            String title = values[1].trim();
            String color = values[2].trim();

            if(title.isBlank()){
                throw new IllegalArgumentException ("Error missing title");
            };

            if(!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("blue") && !color.equalsIgnoreCase("green")){
                throw new IllegalArgumentException ("Error colors are wrong");
            }
            events.add(
                    new Event(date, title, color)
            );

        } catch (RuntimeException e){

                System.out.println("REJECTED: " + line + " | Reason: " + e.getMessage());
        };
            
        }

        return events;
    }
}