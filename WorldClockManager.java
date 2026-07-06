

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WorldClockManager {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a dd-MM-yyyy");

    
    public Map<String, String> getAllWorldTimes() {
        Map<String, String> times = new HashMap<>();
        times.put("Pakistan (PKT)", getCurrentTimeInZone("Asia/Karachi"));
        times.put("London (GMT)", getCurrentTimeInZone("Europe/London"));
        times.put("New York (EST)", getCurrentTimeInZone("America/New_York"));
        times.put("Tokyo (JST)", getCurrentTimeInZone("Asia/Tokyo"));
        times.put("Sydney (AEST)", getCurrentTimeInZone("Australia/Sydney"));
        return times;
    }

    
    public String getCustomZoneTime(String zoneIdStr) {
        try {
            ZoneId zoneId = ZoneId.of(zoneIdStr);
            return ZonedDateTime.now(zoneId).format(formatter);
        } catch (Exception e) {
            return "Invalid Time Zone ID";
        }
    }

    private String getCurrentTimeInZone(String zoneId) {
        return ZonedDateTime.now(ZoneId.of(zoneId)).format(formatter);
    }
}