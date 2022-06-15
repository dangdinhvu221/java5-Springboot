package poly.edu.assignment_earphone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date created;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "orderStates_id", referencedColumnName = "id")
    private OrderStates oderStatesByOderStatesId;
}
