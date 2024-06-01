package org.yaroslaavl.webappstarter.dto;

import org.yaroslaavl.webappstarter.database.entity.Gender;
import org.yaroslaavl.webappstarter.database.entity.Species;

public record PetFilter(Species species,
                        Integer age,
                        Gender gender,
                        Boolean isAvailable) {
}
