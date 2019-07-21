package me.wordwizard.backend.model.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Translation {
    private String translation;
    private List<String> examples;
}
