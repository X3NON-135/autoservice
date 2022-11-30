package rest.autoservice.dto.auto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoResponseDto {
    private Long id;
    private String brand;
    private String model;
    private String manufactureDate;
    private String number;
    private Long ownerId;
}
