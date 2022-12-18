package rest.autoservice.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "duties")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Master master;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    @Column(name = "type_of_duty")
    private String typeOfDuty;

    public enum PaymentStatus {
        PAID("paid"),
        UNPAID("unpaid");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }

    public Duty(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
