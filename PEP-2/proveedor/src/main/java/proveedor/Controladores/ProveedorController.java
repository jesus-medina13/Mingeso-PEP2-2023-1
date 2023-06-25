package proveedor.Controladores;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

import proveedor.Entidades.Proveedor;
import proveedor.Servicios.ProveedorService;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Proveedor>> getProveedores() {
        List<Proveedor> proveedores = proveedorService.getAllProveedor();
        if(proveedores.isEmpty()){
            return ResponseEntity.ok(proveedores);
        }
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<Proveedor> createProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.createProveedor(proveedor);
        return ResponseEntity.ok(proveedor);
    }
}
