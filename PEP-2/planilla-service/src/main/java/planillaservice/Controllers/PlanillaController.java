package planillaservice.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import planillaservice.Entities.Planilla;
import planillaservice.Services.PlanillaService;

@RestController
@RequestMapping("/planilla")
public class PlanillaController {
    @Autowired
    PlanillaService planillaService;

    @GetMapping
    public ResponseEntity<List<Planilla>> getSummary(){
        planillaService.crearPlanillas();
        List<Planilla> summaries = planillaService.getSummaries();
        return ResponseEntity.ok(summaries);
    }
    
}
