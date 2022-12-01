package rest.autoservice.dto.auto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoResponseDto {
    private Long id;
    private String brand;
    private String model;
    private LocalDate manufactureDate;
    private String number;
    private Long ownerId;
}
