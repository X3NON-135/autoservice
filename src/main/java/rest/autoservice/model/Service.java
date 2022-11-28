package rest.autoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue
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
    private TypeOfService typeOfService;

    public enum PaymentStatus {
        PAID("paid"),
        UNPAID("unpaid");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }
    }

    public enum TypeOfService {
        DIAGNOSTICS("diagnostics"),
        CHANGE_OIL("change oil in gearbox"),
        CHANGE_GRM("change GRM"),
        AUTO_OVERHAUL("car overhaul"),
        REPAIR_ENGINE("repair engine");

        private String value;

        TypeOfService(String value) {
            this.value = value;
        }
    }
}
