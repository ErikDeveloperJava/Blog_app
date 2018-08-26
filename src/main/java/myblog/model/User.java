package myblog.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}