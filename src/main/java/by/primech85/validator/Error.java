package by.primech85.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String massage;
}
