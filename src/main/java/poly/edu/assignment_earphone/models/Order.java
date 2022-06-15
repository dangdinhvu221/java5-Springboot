package poly.edu.assignment_earphone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
