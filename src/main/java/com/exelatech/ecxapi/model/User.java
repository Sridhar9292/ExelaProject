package com.exelatech.ecxapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class User implements UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;
    @Id
    private String username; // required
    @JsonIgnore
    private String password;                    // required
    @JsonIgnore
    private String confirmPassword;
    private String firstName;                   // required
    private String lastName;                    // required
    private String email;                       // required; unique
    private Integer version;
    private Set<Role> roles;
    @JsonIgnore
    private Set<Permission> permissions;
    private boolean enabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean deleted;
    private Date entryDate;
    private Date modifyDate;
    private String userAbbrev;
    private String landingPage;
    private String notes;
//    private ArrayListMultimap<String, String> dataFilters = ArrayListMultimap.create();

    /**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @XmlTransient
    @JsonIgnore
    public String getFullName() {
        return firstName + ' ' + lastName;
    }
    @XmlTransient
    @JsonIgnore
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getRolename(), role.getRolename()));
            }
        }

        return userRoles;
    }
    @XmlTransient
    @JsonIgnore
    public List<LabelValue> getPermissionList() {
        List<LabelValue> userPermissions = new ArrayList<LabelValue>();

        if (this.permissions != null) {
            for (Permission permission : permissions) {
                // convert the user's roles to LabelValue Objects
                userPermissions.add(new LabelValue(permission.getPermissionName(), permission.getPermissionName()));
            }
        }

        return userPermissions;
    }
    
    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    @XmlTransient
    @JsonIgnore
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see UserDetails#getAuthorities()
     */
    @XmlTransient
    @JsonIgnore
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        //authorities.addAll(roles);
        authorities.addAll(permissions);
        return authorities;
    }
    @XmlTransient
    @JsonIgnore
    public String getRoleNameList() {
        String roleName="";
        for(Role r : getRoles()) {
            roleName = roleName + ", " + r.getRolename();
        }
        return roleName.length()>0 ?roleName.substring(1):"";
    }
    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }
    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }
    @XmlTransient
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
}