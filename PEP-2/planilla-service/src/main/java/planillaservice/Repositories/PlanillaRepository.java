package planillaservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import planillaservice.Entities.Planilla;

@Repository
public interface PlanillaRepository extends JpaRepository<Planilla, Long>{
    
}

