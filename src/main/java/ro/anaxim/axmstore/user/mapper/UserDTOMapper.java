package ro.anaxim.axmstore.user.mapper;


import lombok.Data;
import org.springframework.beans.BeanUtils;
import ro.anaxim.axmstore.user.domain.Role;
import ro.anaxim.axmstore.user.domain.User;
import ro.anaxim.axmstore.user.dto.domainDTO.UserDTO;

@Data
public class UserDTOMapper {

    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
    public static UserDTO fromUser(User user, Role role){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setRoleName(role.getRoleName());
        userDTO.setPermission(role.getPermission());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }





}
