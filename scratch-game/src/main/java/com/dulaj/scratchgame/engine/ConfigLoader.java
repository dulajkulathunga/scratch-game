package com.dulaj.scratchgame.engine;

import com.dulaj.scratchgame.config.GameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigLoader {


   //Loads the game configuration from a JSON file.
    public static GameConfig load(String path) {
        if (Objects.isNull(path) || path.isBlank()) {
            throw new IllegalArgumentException("Config file path cannot be null or empty.");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), GameConfig.class);
        } catch (IOException e) {
            // Wrap checked exception in an unchecked one with meaningful message
            throw new RuntimeException("Failed to load config: " + path + " (" + e.getMessage() + ")", e);
        }
    }
}