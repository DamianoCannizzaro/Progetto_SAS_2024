package catering.businesslogic.event;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

public class EventManager {
    public ObservableList<EventInfo> getEventInfo() {
        return EventInfo.loadAllEventInfo();
    }

    public EventInfo getEventInfo(String name) {
        return EventInfo.EventInfoFromName(name);
    }
}
