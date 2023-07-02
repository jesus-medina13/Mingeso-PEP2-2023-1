package acopio.Controladores;

import acopio.Entidades.Acopio;
import acopio.Entidades.Acopio2;
import acopio.Servicios.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/acopio")
public class AcopioController {
    @Autowired
    AcopioService acopioService;

    @GetMapping("/getAcopio")
    public ResponseEntity<List<Acopio>> getAcopio() {
        List<Acopio> acopios = acopioService.getArchivos();
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/getAcopio2")
    public ResponseEntity<List<Acopio2>> getAcopio2() {
        List<Acopio2> acopios2 = acopioService.getArchivos2();
        return ResponseEntity.ok(acopios2);
    }

    @PostMapping()
    public void createFileUpload(@RequestParam("file") MultipartFile archivo) {
        acopioService.guardarArchivo(archivo);
        acopioService.leerCsv(archivo.getOriginalFilename());
    }

}
