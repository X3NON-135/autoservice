package rest.autoservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
    private String description;
    private LocalDate acceptanceDate;
    private LocalDate completeDate;
    @OneToMany
    @JoinTable(name = "orders_services",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "duty_id"))
    private List<Duty> duties;
    @OneToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal totalPrice;

    public enum Status {
        ACCEPTED("accepted"),
        IN_PROCESS("process"),
        SUCCESS_DONE("successfully done"),
        NOT_SUCCESS_DONE("not successfully done"),
        PAID("paid");

        private final String value;

        Status(String value) {
            this.value = value;
        }
    }
}
