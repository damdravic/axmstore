package ro.anaxim.axmstore.user.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.anaxim.axmstore.user.domain.Role;
import ro.anaxim.axmstore.user.domain.User;
import ro.anaxim.axmstore.user.dto.domainDTO.UserDTO;
import ro.anaxim.axmstore.user.repository.RoleRepository;
import ro.anaxim.axmstore.user.repository.UserRepository;
import ro.anaxim.axmstore.user.service.UserService;

import static ro.anaxim.axmstore.user.mapper.UserDTOMapper.fromUser;
import static ro.anaxim.axmstore.user.mapper.UserDTOMapper.toUser;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository<User> userRepository;
   private final RoleRepository<Role> roleRepository;


    @Override
    public UserDTO createUser(User user) {

        return mapToUserDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {

        return mapToUserDTO(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {

        userRepository.sendVerificationCode(toUser(user));

    }

    @Override
    public UserDTO verifyCode(String email, String code) {
        return mapToUserDTO(userRepository.verifyCode(email,code));
    }

    private UserDTO mapToUserDTO(User user){
        return fromUser(user,roleRepository.getRoleByUserId(user.getUserId()));
    }



}
