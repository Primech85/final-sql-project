package by.primech85.validator;

public interface Validator<T>{
    ValidationResult isValid(T object);
}
