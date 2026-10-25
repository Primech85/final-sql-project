package by.primech85.Dto;

import by.primech85.entity.Gender;
import by.primech85.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Long id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;
}
