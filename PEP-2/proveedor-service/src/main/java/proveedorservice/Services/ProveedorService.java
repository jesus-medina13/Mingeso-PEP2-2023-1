package proveedorservice.Services;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import proveedorservice.Entities.Proveedor;
import proveedorservice.Repositories.ProveedorRepository;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public void crearProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }
    public void guardarProveedor(int codigo, String nombre, String categoria, String retencion){
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }

}
