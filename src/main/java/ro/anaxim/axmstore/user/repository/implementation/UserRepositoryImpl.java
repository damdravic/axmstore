package ro.anaxim.axmstore.user.repository.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.anaxim.axmstore.exception.ApiException;
import ro.anaxim.axmstore.user.domain.Role;
import ro.anaxim.axmstore.user.domain.User;
import ro.anaxim.axmstore.user.domain.UserPrincipal;
import ro.anaxim.axmstore.user.mapper.UserRowMapper;
import ro.anaxim.axmstore.user.repository.RoleRepository;
import ro.anaxim.axmstore.user.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static ro.anaxim.axmstore.user.enumeration.RoleType.ROLE_USER;
import static ro.anaxim.axmstore.user.query.UserQuery.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository<User> , UserDetailsService {


    private final NamedParameterJdbcTemplate jdbc;

    private final RoleRepository<Role> roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;



    @Override
    public User create(User user) {
        //check if user exists
        //if not, create user
        if(checkUserByMail(user.getEmail().trim().toLowerCase()) > 0 ) throw new ApiException("User already exists");

       try{
           KeyHolder holder = new GeneratedKeyHolder();
           SqlParameterSource parameter = getSqlParameterSource(user);
           jdbc.update(INSERT_USER_QUERY,parameter,holder);
           user.setUserId(Objects.requireNonNull(holder.getKey()).longValue());
           roleRepository.addRoleToUser(user.getUserId(),ROLE_USER.name());
              return user;

       }catch (EmptyResultDataAccessException exception){
           throw new ApiException("EmptyResultDataAccessException");
       }catch (Exception exception){
           throw new ApiException("Exception");
       }
    }


    private Integer checkUserByMail(String email) {
        return jdbc.queryForObject(COUNT_EMAIL_USER, Map.of("email",email),Integer.class);
    }

    @Override
    public Collection<User> listAll() {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try{

            return jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY,Map.of("email",email),new UserRowMapper());
        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("No User found by email: " + email);

        }catch(DataAccessException exception){
            throw new ApiException("DataAccessException.");
        }catch(Exception exception){
            throw new ApiException("... exc ...");
        }
    }

    @Override
    public void sendVerificationCode(User user) {

    }

    @Override
    public User verifyCode(String email, String code) {
        return null;
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("fisrtName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
    }else {
            return new UserPrincipal(user,roleRepository.getRoleByUserId(user.getUserId()).getPermission());
        }
    }
}
