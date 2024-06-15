package it.macgood.weldbootmvn.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String avatar;
    private String phone;

    public static User createNewUser(String firstname, String phone) {
        return User.builder()
                .firstname(firstname)
                .phone(phone)
                .role(Role.OTHER)
                .build();
    }

    public static User buildId(Long id) {return User.builder().id(id).build();}
}
