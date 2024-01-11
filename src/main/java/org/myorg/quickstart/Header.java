package org.myorg.quickstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {
    private final String version;
    private final String timestamp;
    private final String communication;
    private final String tmfSpec;
    private final String baseApiVersion;
    private final String schemaVersion;
    private final String orgService;
    private final String transaction;

    @JsonCreator
    public Header(@JsonProperty("version") String version,
                  @JsonProperty("timestamp") String timestamp,
                  @JsonProperty("communication") String communication,
                  @JsonProperty("tmfSpec") String tmfSpec,
                  @JsonProperty("baseApiVersion") String baseApiVersion,
                  @JsonProperty("schemaVersion") String schemaVersion,
                  @JsonProperty("orgService") String orgService,
                  @JsonProperty("transaction") String transaction) {
        this.version = version;
        this.timestamp = timestamp;
        this.communication = communication;
        this.tmfSpec = tmfSpec;
        this.baseApiVersion = baseApiVersion;
        this.schemaVersion = schemaVersion;
        this.orgService = orgService;
        this.transaction = transaction;
    }

    public String getVersion() {
        return version;
    }
    public String getTimestamp() {
        return timestamp;
    }

}
