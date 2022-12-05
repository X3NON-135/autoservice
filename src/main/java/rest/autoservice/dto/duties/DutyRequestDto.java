package rest.autoservice.dto.duties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DutyRequestDto {
    private Long orderId;
    private Long masterId;
    private double price;
    private String typeOfDuty;
}
