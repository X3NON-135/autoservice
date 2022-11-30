package rest.autoservice.dto.duties;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.model.Duty;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DutyRequestDto {
    private Long orderId;
    private Long masterId;
    private BigDecimal price;
    private Duty.PaymentStatus paymentStatus;
    private Duty.TypeOfDuty typeOfDuty;
}
