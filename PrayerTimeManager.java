

import java.util.HashMap;
import java.util.Map;

public class PrayerTimeManager {
    private final Map<String, String[]> cityData = new HashMap<>();

    public PrayerTimeManager() {
        // Populating the dataset matching your original core application logic
        cityData.put("ISLAMABAD", new String[]{"04:15 AM", "01:30 PM", "04:45 PM", "07:15 PM", "08:45 PM"});
        cityData.put("RAWALPINDI", new String[]{"04:17 AM", "01:30 PM", "04:46 PM", "07:16 PM", "08:47 PM"});
        cityData.put("LAHORE", new String[]{"04:10 AM", "01:25 PM", "04:40 PM", "07:08 PM", "08:38 PM"});
        cityData.put("KARACHI", new String[]{"04:45 AM", "01:40 PM", "05:05 PM", "07:25 PM", "08:55 PM"});
        cityData.put("PESHAWAR", new String[]{"04:20 AM", "01:35 PM", "04:52 PM", "07:22 PM", "08:55 PM"});
    }

    public String[] getPrayersForCity(String city) {
        return cityData.getOrDefault(city.toUpperCase(), null);
    }
    
    public String[] getPrayerNames() {
        return new String[]{"Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"};
    }
}