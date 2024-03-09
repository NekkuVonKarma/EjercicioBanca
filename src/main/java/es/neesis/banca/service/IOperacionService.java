package es.neesis.banca.service;

import es.neesis.banca.model.Operacion;

import java.util.List;

public interface IOperacionService {

    List<Operacion> list(String idCuentaBancaria);
    String add(Operacion operacion);
    String transfer(Operacion operacion);
}
