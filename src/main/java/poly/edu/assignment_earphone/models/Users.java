package poly.edu.assignment_earphone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import poly.edu.assignment_earphone.models.typeEnum.TypeGender;
import poly.edu.assignment_earphone.models.typeEnum.TypeRole;
import poly.edu.assignment_earphone.models.typeEnum.TypeStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String image;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
    private Date created;
    @Enumerated(EnumType.ORDINAL)
    private TypeRole typeRole;
    @Enumerated(EnumType.ORDINAL)
    private TypeGender typeGender;
    @Enumerated(EnumType.ORDINAL)
    private TypeStatus typeStatus;

}
