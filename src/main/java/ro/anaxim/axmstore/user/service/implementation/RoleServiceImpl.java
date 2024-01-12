package ro.anaxim.axmstore.user.service.implementation;

import com.example.AxmCarService.domain.Role;
import com.example.AxmCarService.repository.RoleRepository;
import com.example.AxmCarService.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository<Role> roleRepository;
    @Override
    public Role getRoleByUserId(Long userId) {
        return roleRepository.getRoleByUserId(userId);
    }
}
