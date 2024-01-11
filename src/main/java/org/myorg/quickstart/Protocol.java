package org.myorg.quickstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Protocol {
    private final String version;
    private final String command;
    private final String topic;

    @JsonCreator
    public Protocol(@JsonProperty("version") String version,
                    @JsonProperty("command") String command,
                    @JsonProperty("topic") String topic) {
        this.version = version;
        this.command = command;
        this.topic = topic;
    }

    public String getVersion() {
        return version;
    }

    public String getCommand() {
        return command;
    }

    public String getTopic() {
        return topic;
    }
}
