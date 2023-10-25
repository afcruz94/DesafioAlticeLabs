package org.example.service;

import org.example.enums.Services;

import java.util.Objects;

public class Service {
    private Character service;

    public Service(Character service) {
        this.service = service;
    }

    public Character getService() {
        return service;
    }

    public static Boolean isValidService(Character c) {
        for (Services s : Services.values()) {
            if (Objects.equals(s.getValue(), c.toString())) return true;
        }

        return false;
    }

}
