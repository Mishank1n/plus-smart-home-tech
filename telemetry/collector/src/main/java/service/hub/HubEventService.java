package service.hub;

import model.hub.HubEvent;

public interface HubEventService {
    void processEvent(HubEvent event);
}
