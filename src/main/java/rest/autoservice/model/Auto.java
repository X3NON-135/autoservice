package rest.autoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "autos")
public class Auto {
    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String model;
    private String manufactureDate;
    private String number;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AutoOwner owner;
}
