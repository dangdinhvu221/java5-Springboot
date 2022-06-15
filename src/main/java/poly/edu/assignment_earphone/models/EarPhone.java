package poly.edu.assignment_earphone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class EarPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nationalized
    private String name;
    @Nationalized
    private String title;
    @Nationalized
    private String warranty; // bảo hành
    private Integer frequency; // tần số
    @Nationalized
    private String color;
    private BigDecimal price;
    private String impedance; //trở kháng
    @Nationalized
    private String image;
    @Nationalized
    private String description;
    private Date created;
    private Integer quantity;
    @Enumerated(EnumType.ORDINAL)
    private TypeEarPhone typeEarPhone;
    @Enumerated(EnumType.ORDINAL)
    private TypeCondition typeCondition; // Tình trạng
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturerByManufacturerId;
}
