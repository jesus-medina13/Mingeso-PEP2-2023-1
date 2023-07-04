package planillaservice.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import planillaservice.Entities.Planilla;
import planillaservice.Models.Acopio;
import planillaservice.Models.Acopio2;
import planillaservice.Models.PlanillaModel;
import planillaservice.Models.Proveedor;
import planillaservice.Repositories.PlanillaRepository;


@Service
public class PlanillaService {
    @Autowired
    private PlanillaRepository planillaRepository;

    @Autowired
    RestTemplate restTemplate;
    
    public Proveedor[] getProveedores(){
        Proveedor[] proveedores = restTemplate.getForObject("http://proveedor-service/proveedor", Proveedor[].class);
        return proveedores;
    }

    public Acopio[] getAcopios(){
        Acopio[] acopios = restTemplate.getForObject("http://acopio-service/acopio/getAcopio", Acopio[].class);
        return acopios;
    }

    public Acopio2[] getAcopios2(){
        Acopio2[] acopios2 = restTemplate.getForObject("http://acopio-service/acopio/acopio2", Acopio2[].class);
        return acopios2;
    }

    private float retencion = (float) 0.13; // 13%

    public ArrayList<PlanillaModel> crearPlanillaModel() {
        Proveedor[] proveedoresDataArray;
        Acopio[] acopioDataArray;
        Acopio2[] acopio2DataArray;
        

        proveedoresDataArray = getProveedores();
        acopioDataArray = getAcopios();
        acopio2DataArray = getAcopios2();

        List<Proveedor> proveedoresData;
        List<Acopio>acopiosData;
        List<Acopio2> acopios2Data;

        proveedoresData = Arrays.asList(proveedoresDataArray);
        acopiosData = Arrays.asList(acopioDataArray);
        acopios2Data = Arrays.asList(acopio2DataArray);

        ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>(proveedoresData);
        ArrayList<Acopio> acopios = new ArrayList<Acopio>(acopiosData);
        ArrayList<Acopio2> acopios2 = new ArrayList<Acopio2>(acopios2Data);

        ArrayList<PlanillaModel> planillas = new ArrayList<PlanillaModel>();
        PlanillaModel planilla;

        for (int i = 0; i < proveedores.size(); i++){
            planilla = new PlanillaModel();
            planilla.setCodigoProveedor(proveedores.get(i).getCodigo());
            planilla.setCategoriaProveedor(proveedores.get(i).getCategoria());
            planilla.setNombreProveedor(proveedores.get(i).getNombre());
            if(proveedores.get(i).getRetencion().equals("Sí")){
                planilla.setRetencionProveedor(true);
            }
            else{
                planilla.setRetencionProveedor(false);
            }
            planilla.setAcopios(new ArrayList<Acopio>());
            planilla.setAcopios2(new ArrayList<Acopio2>());

            for (int j = 0; j < acopios.size(); j++){
                if(acopios.get(j).getCodigo() == proveedores.get(i).getCodigo()){
                    planilla.getAcopios().add(acopios.get(j));
                }
            }

            for (int j = 0; j < acopios2.size(); j++){
                if(acopios2.get(j).getCodigo() == proveedores.get(i).getCodigo()){
                    planilla.getAcopios2().add(acopios2.get(j));
                }
            }
            planillas.add(planilla);
        }

        return planillas;
    }

    public PlanillaModel calcularPlanillaModel(PlanillaModel planilla) {

        int ultimoArchivo = planilla.getAcopios().size() - 1;
        int ultimoArchivo2 = planilla.getAcopios2().size() - 1;
        
        if(planilla.getAcopios().size() == 0 || planilla.getAcopios2().size() == 0){
            return null;
        }
        Acopio archivo = planilla.getAcopios().get(ultimoArchivo);
        Acopio2 archivo2 = planilla.getAcopios2().get(ultimoArchivo2);

        int kls_leche = archivo.getKls_leche();

        float pago = 0;

        planilla.setPagoCategoria(pagoCategoria(kls_leche, planilla.getCategoriaProveedor()));
        planilla.setPagoFinal(pagoGrasa(kls_leche, archivo2.getGrasa()));
        planilla.setPagoSolido_total(pagoSolido_Total(kls_leche, archivo2.getSolido_total()));
        pago = acumDePagos(planilla.getPagoCategoria(), planilla.getPagoGrasa(), planilla.getPagoSolido_total());
        
        planilla.setPagoTurno(shiftPayment(planilla, pago));
        planilla.setDcto_pagoKls_leche(discountKgsPayment(planilla, pago));
        planilla.setDcto_pagoGrasa(discountFatPayment(planilla, pago));
        planilla.setDcto_pagoSolido_total(discountTotalSolidsPayment(planilla, pago));
        pago = pago - sumVariations(planilla.getPagoTurno(), planilla.getDcto_pagoKls_leche(),
                planilla.getDcto_pagoGrasa(), planilla.getDcto_pagoSolido_total());
        planilla.setPagoTotal(pago);
        planilla.setImpuestoRetencion(pago + taxRetention(planilla, pago));
        planilla.setPagoFinal(pago + planilla.getImpuestoRetencion());

        return planilla;
    }

    public ArrayList<PlanillaModel> calcularPlanillas(ArrayList<PlanillaModel> planillas) {
        for (int i = 0; i < planillas.size(); i++){
            planillas.set(i, calcularPlanillaModel(planillas.get(i)));
        }
        return planillas;
    }

    public void crearPlanillas(){
        ArrayList<PlanillaModel> planillas = crearPlanillaModel();
        planillas = calcularPlanillas(planillas);
        Planilla planilla;
        int ultimoArchivo, ultimoArchivo2;
        if(planillas.size() > 0){
            planillaRepository.deleteAll();
            for (int i = 0; i < planillas.size(); i++){
                planilla = new Planilla();
                ultimoArchivo = planillas.get(i).getAcopios().size() - 1;
                ultimoArchivo2 = planillas.get(i).getAcopios2().size() - 1;
                if(planillas.get(i) != null){
                    planilla.setFecha(planillas.get(i).getAcopios().get(ultimoArchivo).getFecha());
                    planilla.setCodigo(planillas.get(i).getCodigoProveedor());
                    planilla.setNombre(planillas.get(i).getNombreProveedor());
                    planilla.setKls_leche(planillas.get(i).getAcopios().get(ultimoArchivo).getKls_leche());
                    planilla.setDias(calculateDays(planillas.get(i)));
                    planilla.setPromKls_Leche(calculateAvgDailyMilk(planillas.get(i)));
                    planilla.setVarLeche(planillas.get(i).getVarLeche());
                    planilla.setGrasa(planillas.get(i).getAcopios2().get(ultimoArchivo2).getGrasa());
                    planilla.setVarGrasa(planillas.get(i).getVarGrasa());
                    planilla.setSolido_total(planillas.get(i).getAcopios2().get(ultimoArchivo2).getSolido_total());
                    planilla.setVarSolido_total(planillas.get(i).getVarSolido_total());
                    planilla.setPagoLeche(planillas.get(i).getPagoCategoria());
                    planilla.setPagoGrasa(planillas.get(i).getPagoGrasa());
                    planilla.setPagoSolido_total(planillas.get(i).getPagoSolido_total());
                    planilla.setBonificacion_frecuencia(planillas.get(i).getPagoTurno());
                    planilla.setDcto_varLeche(planillas.get(i).getDcto_pagoKls_leche());
                    planilla.setDcto_varGrasa(planillas.get(i).getDcto_pagoGrasa());
                    planilla.setDcto_varSolido_total(planillas.get(i).getDcto_pagoSolido_total());
                    planilla.setPagoTotal(planillas.get(i).getPagoTotal());
                    planilla.setMontoRetencion(planillas.get(i).getImpuestoRetencion());
                    planilla.setPagoFinal(planillas.get(i).getPagoFinal());
                    planillaRepository.save(planilla);
                }
            }
        }
    }

    public ArrayList<Planilla> getSummaries(){
        return (ArrayList<Planilla>) planillaRepository.findAll();
    }

    public float pagoCategoria(int kls_leche, String category) {
        float categoryPayment;
        if (category.equals("A")){
            categoryPayment = (float) kls_leche * 700;
        }
        else if (category.equals("B")){
            categoryPayment = (float) kls_leche * 550;
        }
        else if (category.equals("C")){
            categoryPayment = (float) kls_leche * 400;
        }
        else if (category.equals("D")){
            categoryPayment = (float) kls_leche * 250;
        }        
        else {      // Error Case
            categoryPayment = 0;
        }
        return categoryPayment;
    }

    public float pagoGrasa(int kls_leche, float fat) {
        float fatPayment;
        if (fat >= 0 && fat <= 20){
            fatPayment = (float) kls_leche * 30;
        }
        else if (fat >= 21 && fat <= 45){
            fatPayment = (float) kls_leche * 80;
        }
        else if (fat >= 46) {
            fatPayment = (float) kls_leche * 120;
        }
        else {      // Error Case
            fatPayment = 0;
        }
        return fatPayment;
    }

    public float pagoSolido_Total(int kls_leche, float total_solids) {
        float totalSolidsPayment;
        if (total_solids >= 0 && total_solids <= 7){
            totalSolidsPayment = (float) kls_leche * -130;
        }
        else if (total_solids >= 8 && total_solids <= 18){
            totalSolidsPayment = (float) kls_leche * -90;
        }
        else if (total_solids >= 19 && total_solids <= 35) {
            totalSolidsPayment = (float) kls_leche * 95;
        }
        else if (total_solids >= 36) {
            totalSolidsPayment = (float) kls_leche * 150;
        }
        else {
            totalSolidsPayment = 0;
        }
        return totalSolidsPayment;
    }

    public float acumDePagos(float categoryPayment, float fatPayment, float totalSolidsPayment) {
        float sumPayments = categoryPayment + fatPayment + totalSolidsPayment;

        return sumPayments;
    }

    public float shiftPayment(PlanillaModel planilla, float payment) {
        float shiftBonus = 1.0f;
        // Checking if there is more than one file uploaded
        int last_file = planilla.getAcopios().size() - 1;
        int penultimate_file = planilla.getAcopios().size() - 2;

        if (last_file == 0){
            if (planilla.getAcopios().get(last_file).getTurno().equals("M")){
                shiftBonus = 0.12f;
            }
            else if(planilla.getAcopios().get(last_file).getTurno().equals("T")){
                shiftBonus = 0.08f;
            }
            else {
                shiftBonus = 0.0f;
            }
        }
        else{
            if (planilla.getAcopios().get(last_file).getFecha().equals(planilla.getAcopios().get(penultimate_file).getFecha())){
                if (!planilla.getAcopios().get(last_file).getTurno().equals(planilla.getAcopios().get(penultimate_file).getTurno())){
                    if (planilla.getAcopios().get(last_file).getTurno().equals("T") && planilla.getAcopios().get(penultimate_file).getTurno().equals("M")){
                        shiftBonus = 0.2f;
                    }
                    else if (planilla.getAcopios().get(last_file).getTurno().equals("M") && planilla.getAcopios().get(penultimate_file).getTurno().equals("T")){
                        shiftBonus = 0.2f;
                    }
                }
            }
            else{
                if (planilla.getAcopios().get(last_file).getTurno().equals("M")){
                    shiftBonus = 0.12f;
                }
                else if (planilla.getAcopios().get(last_file).getTurno().equals("T")){
                    shiftBonus = 0.08f;
                }
                else {  // Error Case
                    shiftBonus = 0.0f;
                }
            }
        }
        return payment * shiftBonus;
    }

    public float discountKgsPayment(PlanillaModel planilla, float payment) {
        float variationKgsPayment = 0;

        int last_file = planilla.getAcopios().size() - 1;
        int penultimate_file = planilla.getAcopios().size() - 2;

        if (planilla.getAcopios().size() <= 1){
            return variationKgsPayment;
        }
        else {
            if (planilla.getAcopios().get(last_file).getFecha().equals(planilla.getAcopios().get(penultimate_file).getFecha()) && planilla.getAcopios().size() > 2){
                penultimate_file--;
            }
            float last_kls_leche = planilla.getAcopios().get(last_file).getKls_leche();
            float penultimate_kls_leche = planilla.getAcopios().get(penultimate_file).getKls_leche();
            planilla.setVarLeche((last_kls_leche/penultimate_kls_leche - 1) * 100);
            if (last_kls_leche > penultimate_kls_leche){
                if (last_kls_leche/penultimate_kls_leche <= 1.08){
                    variationKgsPayment = 0.0f;
                }
                else if (last_kls_leche/penultimate_kls_leche >= 1.09 && last_kls_leche/penultimate_kls_leche <= 1.25){
                    variationKgsPayment = 0.07f;
                }
                else if (last_kls_leche/penultimate_kls_leche >= 1.26 && last_kls_leche/penultimate_kls_leche <= 1.45){
                    variationKgsPayment = 0.15f;
                }
                else if (last_kls_leche/penultimate_kls_leche >= 1.46){
                    variationKgsPayment = 0.3f;
                }
            }
        }
        return variationKgsPayment * payment;
    }

    public float discountFatPayment(PlanillaModel planilla, float payment) {
        float variationFatPayment = 0;

        int last_file = planilla.getAcopios2().size() - 1;
        int penultimate_file = planilla.getAcopios2().size() - 2;

        if (last_file < 1){
            return variationFatPayment;
        }
        else{
            float last_fat_data = planilla.getAcopios2().get(last_file).getGrasa();
            float penultimate_fat_data = planilla.getAcopios2().get(penultimate_file).getGrasa();
            planilla.setVarGrasa((penultimate_fat_data/last_fat_data - 1) * 100);
            
            if (penultimate_fat_data/last_fat_data <= 1.15){
                variationFatPayment = 0.0f;
            }
            else if (penultimate_fat_data/last_fat_data >= 1.16 && penultimate_fat_data/last_fat_data <= 1.25){
                variationFatPayment = 0.12f;
            }
            else if (penultimate_fat_data/last_fat_data >= 1.26 && penultimate_fat_data/last_fat_data <= 1.40){
                variationFatPayment = 0.2f;
            }
            else if (penultimate_fat_data/last_fat_data >= 1.41){
                variationFatPayment = 0.3f;
            }
        }
        return variationFatPayment * payment;
    }

    public float discountTotalSolidsPayment(PlanillaModel planilla, float payment) {
        float variationTotalSolidsPayment = 0;

        int last_file = planilla.getAcopios2().size() - 1;
        int penultimate_file = planilla.getAcopios2().size() - 2;

        if (last_file < 1){
            return variationTotalSolidsPayment;
        }
        else{
            float last_total_solids_data = planilla.getAcopios2().get(last_file).getSolido_total();
            float penultimate_total_solids_data = planilla.getAcopios2().get(penultimate_file).getSolido_total();
            planilla.setVarSolido_total((last_total_solids_data/penultimate_total_solids_data - 1) * 100);
            if (last_total_solids_data > penultimate_total_solids_data){
                if (last_total_solids_data/penultimate_total_solids_data <= 1.06){
                    variationTotalSolidsPayment = 0.0f;
                }
                else if (last_total_solids_data/penultimate_total_solids_data >= 1.07 && last_total_solids_data/penultimate_total_solids_data <= 1.12){
                    variationTotalSolidsPayment = 0.18f;
                }
                else if (last_total_solids_data/penultimate_total_solids_data >= 1.13 && last_total_solids_data/penultimate_total_solids_data <= 1.35){
                    variationTotalSolidsPayment = 0.27f;
                }
                else if (last_total_solids_data/penultimate_total_solids_data >= 1.36){
                    variationTotalSolidsPayment = 0.45f;
                }
            }
        }
        return variationTotalSolidsPayment * payment;
    }

    public int calculateDays(PlanillaModel planilla){
        int days = 0;
        ArrayList<String> dates = new ArrayList<String>();
        
        if (planilla.getAcopios().size() == 0 || planilla.getAcopios() == null){
            return days;
        }
        else{
            dates.add(planilla.getAcopios().get(0).getFecha());
            days++;
            for(int i = 1; i < planilla.getAcopios().size(); i++){
                for(int j = 0; j < dates.size(); j++){
                    if (planilla.getAcopios().get(i).getFecha().equals(dates.get(j))){
                        break;
                    }
                    else if (j == dates.size() - 1){
                        dates.add(planilla.getAcopios().get(i).getFecha());
                        days++;
                    }
                }
            }
        }
        return days;
    }

    public float calculateAvgDailyMilk(PlanillaModel planilla){
        float avgDailyMilk = 0;
        if (planilla.getAcopios().size() == 0){
            return avgDailyMilk;
        }
        else{
            for (int i = 0; i < planilla.getAcopios().size(); i++){
                avgDailyMilk += planilla.getAcopios().get(i).getKls_leche();
            }
            avgDailyMilk = avgDailyMilk/planilla.getAcopios().size();
        }
        return avgDailyMilk;
    }

    public float sumVariations(float shiftPayment, float discountKgsPayment, float discountFatPayment, float discountTotalSolidsPayment) {
        float sumVariations = shiftPayment + discountKgsPayment + discountFatPayment + discountTotalSolidsPayment;

        return sumVariations;
    }

    public float taxRetention(PlanillaModel planilla, float payment) {
        if (payment >= 950000 && planilla.getRetencionProveedor()){
            return payment * retencion;
        }
        return 0.0f;
    }

}

