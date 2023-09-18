package com.example.ibproekt2.entity;

import com.example.ibproekt2.security.Role;
import com.example.ibproekt2.entity.Product;
import com.example.ibproekt2.entity.SecureToken;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String _username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean accountVerified;
//    private boolean mfaEnabled;
//    private String secret;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "shopping_cart",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    List<Product> productList;

    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //logiranjeto preku mail se pravi
    @Override
    public String getUsername() {
        return email;
    }
    //password getter e avtomatski generiran preku Lombok

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountVerified();
    }
}
