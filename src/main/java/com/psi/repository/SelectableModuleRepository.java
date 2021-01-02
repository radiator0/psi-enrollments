package com.psi.repository;
import com.psi.domain.SelectableModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SelectableModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectableModuleRepository extends JpaRepository<SelectableModule, Long> {

}
