package planillaservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proveedor {
    private Long id;

    private String nombre;
    private int codigo;
    private String categoria;
    private String retencion;
}
