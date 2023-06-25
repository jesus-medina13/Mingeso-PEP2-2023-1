package proveedor.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proveedor.Entidades.Proveedor;


@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}