package proveedorservice.Controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import proveedorservice.Entities.Proveedor;
import proveedorservice.Services.ProveedorService;

import java.util.List;


@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Proveedor>> getProveedores() {
        List<Proveedor> proveedores = proveedorService.getAllProveedores();
        if(proveedores.isEmpty()){
            return ResponseEntity.ok(proveedores);
        }
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public String crearProveedor(@RequestParam("codigo") int codigo,
                                                    @RequestParam("nombre") String nombre,
                                                    @RequestParam("categoria") String categoria,
                                                    @RequestParam("retencion") String retencion){
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/proveedor";
    }

}
