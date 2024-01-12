package ro.anaxim.axmstore.user.repository.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.anaxim.axmstore.exception.ApiException;
import ro.anaxim.axmstore.user.domain.Role;
import ro.anaxim.axmstore.user.mapper.RoleRowMapper;
import ro.anaxim.axmstore.user.repository.RoleRepository;

import java.util.Collection;

import static java.util.Map.of;
import static ro.anaxim.axmstore.user.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static ro.anaxim.axmstore.user.query.UserQuery.INSERT_USER_ROLE_QUERY;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository<Role> {



    private final  NamedParameterJdbcTemplate jdbc;



    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> listAll() {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Role get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {

        try{
            Role role= jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("roleName",roleName),new RoleRowMapper());
            jdbc.update(INSERT_USER_ROLE_QUERY,of("userId",userId,"roleId",role.getRoleId()));
        }catch(EmptyResultDataAccessException exception){
            throw new ApiException("   Role not exist");
        }catch ( DataAccessException exception){
            throw new ApiException(" DataAccessException");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
