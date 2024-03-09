package es.neesis.banca.repository;

import es.neesis.banca.entities.OperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOperacionRepository extends JpaRepository<OperacionEntity, String> {

    List<OperacionEntity> findAllByCuentaOrigen(String cuentaOrigen);

    List<OperacionEntity> findAllByCuentaDestino(String cuentaDestino);
}
