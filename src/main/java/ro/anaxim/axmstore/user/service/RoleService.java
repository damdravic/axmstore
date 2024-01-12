package ro.anaxim.axmstore.user.service;


import ro.anaxim.axmstore.user.domain.Role;

public interface RoleService  {
   Role getRoleByUserId(Long userId);
}
