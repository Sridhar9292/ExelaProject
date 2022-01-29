package com.exelatech.ecxapi.model;

import java.util.Set;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;

/**
 * This class is used to represent available roles in the database.
 */
@Data
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 3690197650654049848L;
    private String rolename;
    private String description;
    private Set<Permission> permissions;
 

    @XmlTransient
    @JsonIgnore
    public String getAuthority() {
        return getRolename();
    }
}
