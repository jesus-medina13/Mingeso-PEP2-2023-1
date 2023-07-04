package planillaservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acopio {
    private Long id;
    private String fecha;
    private String turno;
    private int codigo;
    private int kls_leche;

}
