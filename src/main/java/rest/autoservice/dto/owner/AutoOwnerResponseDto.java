package rest.autoservice.dto.owner;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AutoOwnerResponseDto {
    private Long id;
    private String fullName;
    private List<Long> autoIds;
    private List<Long> orderIds;
}
