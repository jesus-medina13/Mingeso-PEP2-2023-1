package proveedorservice.Controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    @CrossOrigin(origins = "*")
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.crearProveedor(proveedor);
        return ResponseEntity.ok(proveedor);
    }

}
