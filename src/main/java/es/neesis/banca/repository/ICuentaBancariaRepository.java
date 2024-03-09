package es.neesis.banca.repository;

import es.neesis.banca.entities.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICuentaBancariaRepository extends JpaRepository<CuentaBancariaEntity, String> {

    CuentaBancariaEntity findByIban(String iban);
}
