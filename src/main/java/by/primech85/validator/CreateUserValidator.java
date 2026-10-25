package by.primech85.validator;

import by.primech85.Dto.CreateUserDto;
import by.primech85.entity.Gender;
import by.primech85.entity.Role;
import by.primech85.utils.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto>{
    private static final CreateUserValidator INSTANCE =new CreateUserValidator();
    private CreateUserValidator() {}
    public static CreateUserValidator getInstance()
    {
        return INSTANCE;
    }

    public ValidationResult isValid(CreateUserDto userDto)
    {
        var validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(userDto.getBirthday())){
            validationResult.add(Error.of("invalid.birthday","Birthday is invalid"));
        }
        if(Gender.find(userDto.getGender()).isEmpty()){
            validationResult.add(Error.of("invalid.gender","gender is invalid"));
        }
        if(Role.find(userDto.getRole()).isEmpty()){
            validationResult.add(Error.of("invalid.role","Role is invalid"));
        }
        if(userDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name","name is invalid"));
        }
        if(userDto.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password","password is invalid"));
        }
        if(userDto.getEmail().isEmpty()){
            validationResult.add(Error.of("invalid.email","email is invalid"));
        }
        return validationResult;
    }
}
