package rest.autoservice.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductResponseDto {
    private Long id;
    private String title;
    private double price;
}
