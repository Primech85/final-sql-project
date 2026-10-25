package by.primech85.service;

import by.primech85.Dao.UserDao;
import by.primech85.Dto.CreateUserDto;
import by.primech85.Dto.UserDto;
import by.primech85.Exception.ValidationException;
import by.primech85.entity.User;
import by.primech85.mapper.CreateUserMapper;
import by.primech85.mapper.UserMapper;
import by.primech85.validator.CreateUserValidator;

import java.util.Optional;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private UserService(){

    }
    public static UserService getInstance(){
        return INSTANCE;
    }

    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<UserDto> login(String email, String password){
        return userDao.finByEmailAndPasword(email,password).map(userMapper::mapFrom);
    }

    public Integer create(CreateUserDto createUserDto){
        var validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw  new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        var result = userDao.save(user);
        return result.getId();
    }
}
