package es.neesis.banca.service;

import es.neesis.banca.entities.CuentaBancariaEntity;
import es.neesis.banca.entities.OperacionEntity;
import es.neesis.banca.model.CuentaBancaria;
import es.neesis.banca.model.Operacion;
import es.neesis.banca.repository.ICuentaBancariaRepository;
import es.neesis.banca.repository.IOperacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CuentaBancariaService implements ICuentaBancariaService{

    private ICuentaBancariaRepository cuentaBancariaRepository;
    private IOperacionRepository operacionRepository;
    private ModelMapper modelMapper;

    public CuentaBancariaService(ICuentaBancariaRepository cuentaBancariaRepository, IOperacionRepository operacionRepository) {
        this.cuentaBancariaRepository = cuentaBancariaRepository;
        this.operacionRepository = operacionRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String init() {
        CuentaBancaria cuentaBancaria = new CuentaBancaria();
        cuentaBancaria.setId(UUID.randomUUID().toString());
        cuentaBancaria.setSaldo(1000);
        cuentaBancaria.setIban("ES1234567890123456789012");
        cuentaBancaria.setTitular("Sergio");

        CuentaBancariaEntity cuentaBancariaEntity = this.modelMapper.map(cuentaBancaria, CuentaBancariaEntity.class);

        this.cuentaBancariaRepository.save(cuentaBancariaEntity);

        Operacion operacion = new Operacion();
        operacion.setId(UUID.randomUUID().toString());
        operacion.setDescripcion("Ingreso inicial");
        operacion.setImporte(1500);
        operacion.setCuentaOrigen(cuentaBancaria.getIban());
        operacion.setCuentaDestino(null);
        operacion.setFecha(Instant.now());

        this.operacionRepository.save(this.modelMapper.map(operacion, OperacionEntity.class));

        operacion.setId(UUID.randomUUID().toString());
        operacion.setDescripcion("Retirada inicial");
        operacion.setImporte(-500);
        operacion.setCuentaOrigen(cuentaBancaria.getIban());
        operacion.setCuentaDestino(null);
        operacion.setFecha(Instant.now());

        this.operacionRepository.save(this.modelMapper.map(operacion, OperacionEntity.class));

        return "BBDD Inicializada";
    }

    @Override
    public List<CuentaBancaria> list() {
        List<CuentaBancaria> cuentasBancarias = new ArrayList<>();

        List<CuentaBancariaEntity> entities = this.cuentaBancariaRepository.findAll();

        entities.forEach(entity -> {
            CuentaBancaria cuentaBancaria = this.modelMapper.map(entity, CuentaBancaria.class);
            cuentasBancarias.add(cuentaBancaria);
        });

        return cuentasBancarias;
    }

    @Override
    public CuentaBancaria add(CuentaBancaria cuentaBancaria) {
        if(cuentaBancaria.getId() == null)
            cuentaBancaria.setId(UUID.randomUUID().toString());

        CuentaBancariaEntity cuentaBancariaEntity = this.modelMapper.map(cuentaBancaria, CuentaBancariaEntity.class);

        this.cuentaBancariaRepository.save(cuentaBancariaEntity);

        return cuentaBancaria;
    }

    @Override
    public CuentaBancaria findByIban(String iban) {
        CuentaBancariaEntity cuentaBancariaEntity = this.cuentaBancariaRepository.findByIban(iban);
        if(cuentaBancariaEntity != null)
            return this.modelMapper.map(cuentaBancariaEntity, CuentaBancaria.class);
        else
            return null;
    }
}
