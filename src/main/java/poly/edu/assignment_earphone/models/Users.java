package poly.edu.assignment_earphone.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
