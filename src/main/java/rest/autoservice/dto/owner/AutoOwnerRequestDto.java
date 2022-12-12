package rest.autoservice.dto.owner;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AutoOwnerRequestDto {
    private String fullName;
    private List<Long> autoIds;
    private List<Long> orderIds;
}
