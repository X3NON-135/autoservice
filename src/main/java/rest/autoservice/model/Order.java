package rest.autoservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;
    private String description;
    @Column(name = "acceptance_date")
    private LocalDateTime acceptanceDate;
    @Column(name = "complete_date")
    private LocalDateTime finishedDate;
    @OneToMany(mappedBy = "order")
    private List<Duty> duties = new ArrayList<>();
    @OneToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "total_price")
    private double totalPrice;

    public enum Status {
        ACCEPTED("accepted"),
        IN_PROCESS("process"),
        SUCCESSFUL_DONE("completed"),
        UNSUCCESSFUL_DONE("failure"),
        PAID("paid");

        private final String value;

        Status(String value) {
            this.value = value;
        }
    }
}
