package planilla.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "summary")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String fecha;
    private int codigo;
    private String nombre;
    private int kls_leche;
    private int dias;
    private float avgDailyMilk;
    private float milkVariation;
    private float fat;
    private float fatVariation;
    private float totalSolids;
    private float totalSolidsVariation;
    private float milkPayment;
    private float fatPayment;
    private float totalSolidsPayment;
    private float frenquencyBonus;
    private float milkVarDiscount;
    private float fatVarDiscount;
    private float stVarDiscount;
    private float totalPayment;
    private float retentionAmmount;
    private float finalPayment;
}
