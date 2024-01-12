package ro.anaxim.axmstore.user.repository;


import ro.anaxim.axmstore.user.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User>{

    T create (T data);

    Collection<T> listAll();

    T update(T data);

    T get(int id);

    Boolean delete(int id);


    /* More complex operations */

    T getUserByEmail(String email);

    void sendVerificationCode(T user);

    T verifyCode(String email, String code);







}
