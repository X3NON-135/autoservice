package rest.autoservice.model;

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
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_duty")
    private TypeOfDuty typeOfDuty;

    public enum PaymentStatus {
        PAID("paid"),
        UNPAID("unpaid");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }

    public enum TypeOfDuty {
        DIAGNOSTICS("diagnostics"),
        CHANGE_OIL("change oil in gearbox"),
        CHANGE_GRM("change belt of timing (GRM)"),
        AUTO_OVERHAUL("car overhaul"),
        REPAIR_ENGINE("repair engine");

        private String value;

        TypeOfDuty(String value) {
            this.value = value;
        }
    }

    public Duty(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
