package proveedor.Servicios;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import proveedor.Entidades.Proveedor;
import proveedor.Repositorios.ProveedorRepository;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> getAllProveedor() {
        return proveedorRepository.findAll();
    }

    public Proveedor getProveedorById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public void createProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
    }

}
