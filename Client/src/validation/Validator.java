/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mihajlo
 */
public class Validator {

    private final List<String> validationErrors;

    private Validator() {
        validationErrors = new ArrayList<>();
    }

    public static Validator startValidation() {
        return new Validator();
    }

    public Validator validateNotNullOrEmpty(String value, String errorMessage) {
        if (value == null || value.trim().isEmpty()) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateNotNull(Object value, String errorMessage) {
        if (value == null) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateValueIsNumber(String value, String errorMessage) {
        try {
            if (value != null) {
                new BigDecimal(value);
            } else {
                this.validationErrors.add(errorMessage);
            }
        } catch (NumberFormatException nfe) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateValueIsDate(String value, String pattern, String errorMessage) {
        try {
            if (value != null) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                LocalDate.parse(value, dtf);
            } else {
                this.validationErrors.add(errorMessage);
            }
        } catch (DateTimeParseException ex) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public Validator validateListIsNotEmpty(List<?> list, String errorMessage) {
        if (list == null || list.isEmpty()) {
            this.validationErrors.add(errorMessage);
        }
        return this;
    }

    public void throwIfInvalide() throws ValidationException {
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(this.validationErrors.stream().collect(Collectors.joining("\n")));
        }
    }
}
