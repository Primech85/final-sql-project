package by.primech85.mapper;

import by.primech85.Dto.CreateUserDto;
import by.primech85.Dto.UserDto;
import by.primech85.entity.Gender;
import by.primech85.entity.Role;
import by.primech85.entity.User;
import by.primech85.utils.LocalDateFormatter;

public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    private CreateUserMapper(){

    }
    public static CreateUserMapper getInstance()
    {
        return INSTANCE;
    }
    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormatter.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .gender(Gender.valueOf(createUserDto.getGender()))
                .role(Role.valueOf(createUserDto.getRole()))
                .build();
    }
}
