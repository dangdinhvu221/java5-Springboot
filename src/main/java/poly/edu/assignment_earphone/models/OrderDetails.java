package poly.edu.assignment_earphone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Orders ordersByOrdersId;
    @ManyToOne
    @JoinColumn(name = "earPhone_id", referencedColumnName = "id")
    private EarPhone earPhoneByEarPhoneId;
}
