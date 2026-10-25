package by.primech85.Dao;

import by.primech85.entity.Gender;
import by.primech85.entity.Role;
import by.primech85.entity.User;
import by.primech85.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User,Long>{
    private static final UserDao INSTANCE = new UserDao();
    private UserDao(){

    }

    private static final String SAVE_SQL =
            "INSERT INTO users(name,birthday,email,password,role,gender) VALUES(?,?,?,?,?,?)";
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL =
            "SELECT * FROM users WHERE email = ? AND password = ?";

    public static UserDao getInstance(){
        return INSTANCE;
    }

    public Optional<User> finByEmailAndPasword(String email, String password){
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1,email);
            statement.setString(2,password);
            var resultSet = statement.executeQuery();
            User user = null;
            if(resultSet.next()){
                user= build(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public User save(User o) {
        try(var connection = ConnectionManager.get();
        var preparedStatment = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatment.setObject(1,o.getName());
            preparedStatment.setObject(2,o.getBirthday());
            preparedStatment.setObject(3,o.getEmail());
            preparedStatment.setObject(4,o.getPassword());
            preparedStatment.setObject(5,o.getRole().name());
            preparedStatment.setObject(6,o.getGender().name());

            preparedStatment.executeUpdate();

            var generatedKeys = preparedStatment.getGeneratedKeys();
            generatedKeys.next();
            o.setId(generatedKeys.getObject("id",Integer.class));
            return o;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public User build(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getObject("id", Integer.class))
                .name(result.getObject("name", String.class))
                .birthday(result.getObject("birthday", Date.class).toLocalDate())
                .email(result.getObject("email", String.class))
                .password(result.getObject("password",String.class))
                .role(Role.find(result.getObject("role",String.class)).orElse(null))
                .gender(Gender.valueOf(result.getObject("gender", String.class)))
                .build();
    }


}
