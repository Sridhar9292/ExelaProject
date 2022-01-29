package com.exelatech.ecxapi.model;

import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;

/**
 * This class is used to represent available permissions in the database.
 */
@Data
public class Permission implements GrantedAuthority {
    private static final long serialVersionUID = 3690197650654049849L;
    private String permissionName;
    private String description;

    @JsonIgnore
    @XmlTransient
    public String getAuthority() {
        return getPermissionName();
    }
}
