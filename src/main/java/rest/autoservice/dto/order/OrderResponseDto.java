package rest.autoservice.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rest.autoservice.dto.duties.DutyResponseDto;
import rest.autoservice.dto.product.ProductResponseDto;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderResponseDto {
    private Long id;
    private Long autoId;
    private String description;
    private LocalDateTime acceptanceDate;
    private LocalDateTime completeDate;
    private List<DutyResponseDto> duties;
    private List<ProductResponseDto> products;
    private String status;
    private BigDecimal totalPrice;
}
