package com.github.stormwyrm.library.rx.rxbus;

import java.util.Objects;

public class TagMessage {
    private Object event;
    private String tag;

    public TagMessage() {
    }

    public TagMessage(Object event, String tag) {
        this.event = event;
        this.tag = tag;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    Class getEventType() {
        return Utils.getClassFromObject(event);
    }

    boolean isSameType(final Class eventType, final String tag) {
        return getEventType().equals(eventType)
                && Objects.equals(this.tag, tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagMessage that = (TagMessage) o;
        return Objects.equals(getEventType(), Utils.getClassFromObject(that)) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, tag);
    }

    @Override
    public String toString() {
        return "TagMessage{" +
                "event=" + event +
                ", tag='" + tag + '\'' +
                '}';
    }
}
