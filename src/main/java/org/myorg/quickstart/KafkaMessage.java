package org.myorg.quickstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaMessage {
    //    private final Protocol protocol;
//    private final Header header;
//    private final Body body;
    private final Employee employee;

    @JsonCreator
    public KafkaMessage(
//            @JsonProperty("protocol") Protocol protocol,
//                        @JsonProperty("header") Header header,
//                        @JsonProperty("body") Body body,
                        @JsonProperty("employee") Employee employee) {
//        this.protocol = protocol;
//        this.header = header;
//        this.body = body;
        this.employee = employee;
    }

    //    public Protocol getProtocol() {
//        return protocol;
//    }
//
//    public Header getHeader() {
//        return header;
//    }
//
//    public Body getBody() {
//        return body;
//    }
    public Employee getEmployee() {
        return employee;
    }
}
