package acopioservice.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acopio2")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acopio2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int codigo;
    private float grasa;
    private float solido_total;

}