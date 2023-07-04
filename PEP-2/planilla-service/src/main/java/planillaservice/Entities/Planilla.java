package planillaservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "planilla")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Planilla {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String fecha;
    private int codigo;
    private String nombre;
    private int kls_leche;
    private int dias;
    private float promKls_Leche;
    private float varLeche;
    private float grasa;
    private float varGrasa;
    private float solido_total;
    private float varSolido_total;
    private float pagoLeche;
    private float pagoGrasa;
    private float pagoSolido_total;
    private float bonificacion_frecuencia;
    private float dcto_varLeche;
    private float dcto_varGrasa;
    private float dcto_varSolido_total;
    private float pagoTotal;
    private float montoRetencion;
    private float pagoFinal;
}
