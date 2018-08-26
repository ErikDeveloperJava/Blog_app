package myblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2)
    @Column(name = "comment")
    private String commentText;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @ManyToOne
    private Post post;

    @JsonIgnore
    @ManyToOne
    private Comment parent;
}
