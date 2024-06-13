package com.triana.salesianos.edu.skillshare.user.model;

import com.triana.salesianos.edu.skillshare.order.model.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Table(name = "user_entity")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;


    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Enumerated
    @Column(name = "user_role")
    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<UserRole> userRole;



    @Builder.Default
    protected LocalDateTime lastPasswordChangedAt = LocalDateTime.now();

    @CreatedDate
    protected LocalDateTime createdAt;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    protected boolean accountNonLocked = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany
    @JoinTable(
            name = "favorite_orders",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> favoriteOrders = new ArrayList<>();

    @Column(name = "profile_picture")
    private String profilePicture;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean getEnabled(){return isEnabled();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
