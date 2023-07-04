package planillaservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaModel {
    // Data from database
    private int codigoProveedor;
    private String categoriaProveedor;
    private String nombreProveedor;
    private Boolean retencionProveedor;
    private ArrayList<planillaservice.Models.Acopio> acopios;
    private ArrayList<planillaservice.Models.Acopio2> acopios2;

    // Summary data
    private float pagoLeche;
    private float pagoCategoria;
    private float pagoGrasa;
    private float pagoSolido_total;
    private float pagoTurno;
    private float dcto_pagoKls_leche;
    private float dcto_pagoGrasa;
    private float dcto_pagoSolido_total;
    private float impuestoRetencion;
    private float pagoTotal;
    private float pagoFinal;

    // Variations
    private float varLeche;
    private float varGrasa;
    private float varSolido_total;

}
