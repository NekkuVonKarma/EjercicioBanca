package es.neesis.banca.service;

import es.neesis.banca.model.CuentaBancaria;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICuentaBancariaService {

    String init();
    List<CuentaBancaria> list();
    CuentaBancaria add(CuentaBancaria cuentaBancaria);
    CuentaBancaria findByIban(String iban);
}
