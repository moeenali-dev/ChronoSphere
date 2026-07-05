

import java.util.ArrayList;
import java.util.List;

public class AlarmManager {
    private final List<String> activeAlarms = new ArrayList<>();

    public void addAlarm(String timeStr) {
        if (!activeAlarms.contains(timeStr)) {
            activeAlarms.add(timeStr);
        }
    }

    public List<String> getActiveAlarms() {
        return activeAlarms;
    }

    public void removeAlarm(int index) {
        if (index >= 0 && index < activeAlarms.size()) {
            activeAlarms.remove(index);
        }
    }
}