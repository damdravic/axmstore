package ro.anaxim.axmstore.user.service;


import ro.anaxim.axmstore.user.domain.User;
import ro.anaxim.axmstore.user.dto.domainDTO.UserDTO;

public interface UserService {

    public UserDTO createUser(User user);
      public UserDTO getUserByEmail(String email);

    void sendVerificationCode(UserDTO user);



    UserDTO verifyCode(String email, String code);
}
