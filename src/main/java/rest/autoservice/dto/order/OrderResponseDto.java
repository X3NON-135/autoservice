package rest.autoservice.dto.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.model.Order;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderResponseDto {
    private Long id;
    private Long autoId;
    private String description;
    private LocalDate acceptanceDate;
    private LocalDate completeDate;
    private List<Long> dutiesIds;
    private List<Long> productsIds;
    private Order.Status status;
    private BigDecimal totalPrice;
}
