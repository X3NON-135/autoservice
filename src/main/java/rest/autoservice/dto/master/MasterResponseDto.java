package rest.autoservice.dto.master;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.dto.order.OrderResponseDto;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MasterResponseDto {
    private Long id;
    private String fullName;
    private List<OrderResponseDto> finishedOrders;
}

