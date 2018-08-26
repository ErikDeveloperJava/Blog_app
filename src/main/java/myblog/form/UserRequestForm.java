package myblog.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestForm {

    @Length(min = 2,max = 255,message = "in field name wrong data")
    private String name;

    @Length(min = 4,max = 255,message = "in field surname wrong data")
    private String surname;

    @Length(min = 2,max = 255,message = "in field username wrong data")
    private String username;

    @Length(min = 4,max = 255,message = "in field password wrong data")
    private String password;

    @Length(min = 4,max = 255,message = "in field repeat password wrong data")
    private String repeatPassword;

    private MultipartFile image;
}
