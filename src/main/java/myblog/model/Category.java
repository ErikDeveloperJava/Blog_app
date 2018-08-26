package myblog.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2,max = 255,message = "in field name wrong data")
    private String name;
}