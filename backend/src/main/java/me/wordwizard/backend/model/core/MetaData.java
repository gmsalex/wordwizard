package me.wordwizard.backend.model.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MetaData {
    private List<Translation> translations;
}
