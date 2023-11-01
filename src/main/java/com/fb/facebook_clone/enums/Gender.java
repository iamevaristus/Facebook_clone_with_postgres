package com.fb.facebook_clone.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    PREFER_NOT_TO_SAY("Prefer not to say"),
    OTHERS("Others");

    private final String type;
    Gender(String type) {
        this.type = type;
    }
}
