package proveedorservice.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proveedorservice.Entities.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{
}
