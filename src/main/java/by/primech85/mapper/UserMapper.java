package by.primech85.mapper;

import by.primech85.Dto.UserDto;
import by.primech85.entity.Gender;
import by.primech85.entity.Role;
import by.primech85.entity.User;
import by.primech85.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDto, User> {
    private static final UserMapper INSTANCE =  new UserMapper();
    public static UserMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .gender((user.getGender()))
                .role(user.getRole())
                .build();
    }
}
