package ru.firsto.yac16artists.api.loader.event;

public class OnLoaderEvent {
    public enum EventType { START, FINISH }

    public EventType eventType;

    public OnLoaderEvent(EventType eventType) {
        this.eventType = eventType;
    }
}
