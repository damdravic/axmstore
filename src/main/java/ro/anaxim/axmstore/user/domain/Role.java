package ro.anaxim.axmstore.user.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Role {
    private Long roleId;
    private String roleName;
    private String permission;

}
