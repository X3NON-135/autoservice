package rest.autoservice.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "services")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne
    @JoinColumn(name = "master_id")
    private Master master;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
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
        CHANGE_GRM("change GRM"),
        AUTO_OVERHAUL("car overhaul"),
        REPAIR_ENGINE("repair engine");

        private String value;

        TypeOfDuty(String value) {
            this.value = value;
        }
    }
}
