package me.wordwizard.backend.model.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Example {
    private String value;
    private List<String> hiddenWords;
}
