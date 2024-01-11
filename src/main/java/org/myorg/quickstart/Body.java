package org.myorg.quickstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Body {
    private final String accountName;
    private String accountId;

    @JsonCreator
    public Body(@JsonProperty("accountName") String accountName)
                 {
        this.accountName = accountName;

    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
