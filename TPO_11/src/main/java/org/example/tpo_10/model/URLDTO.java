package org.example.tpo_10.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.tpo_10.constraint.*;

public class URLDTO {
    private String id;

    @NotNull
    @UrlNotHttps
    @NotUniqueURL
    @UrlNotValid
    private String targetUrl;
    private String redirectUrl;
    private int visits;

    @NotEnoughDigits
    @NotEnoughSpecial
    @NotEnoughUppercaseLetters
    @NotEnoughLowercaseLetters
    @PasswordTooShort
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    public URLDTO() {}

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
