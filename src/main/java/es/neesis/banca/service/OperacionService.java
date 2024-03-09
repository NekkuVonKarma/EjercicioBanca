package es.neesis.banca.service;

import es.neesis.banca.entities.OperacionEntity;
import es.neesis.banca.model.CuentaBancaria;
import es.neesis.banca.model.Operacion;
import es.neesis.banca.repository.IOperacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OperacionService implements IOperacionService{

    private IOperacionRepository operacionRepository;
    private ICuentaBancariaService cuentaBancariaService;
    private ModelMapper modelMapper;

    public OperacionService(IOperacionRepository operacionRepository, ICuentaBancariaService cuentaBancariaService) {
        this.operacionRepository = operacionRepository;
        this.cuentaBancariaService = cuentaBancariaService;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<Operacion> list(String idCuentaBancaria) {
        List<Operacion> operaciones = new ArrayList<>();
        List<OperacionEntity> outputEntities = this.operacionRepository.findAllByCuentaOrigen(idCuentaBancaria);
        List<OperacionEntity> inputEntities = this.operacionRepository.findAllByCuentaDestino(idCuentaBancaria);

        outputEntities.forEach(entity -> {
            Operacion operacion = this.modelMapper.map(entity, Operacion.class);
            operaciones.add(operacion);
        });
        inputEntities.forEach(entity -> {
            Operacion operacion = this.modelMapper.map(entity, Operacion.class);
            operaciones.add(operacion);
        });
        return operaciones;
    }

    @Override
    public String add(Operacion operacion) {
        if(operacion.getId() == null)
            operacion.setId(UUID.randomUUID().toString());

        if(!StringUtils.hasLength(operacion.getCuentaDestino()))
            operacion.setImporte(operacion.getImporte() * -1);

        operacion.setFecha(Instant.now());
        OperacionEntity operacionEntity = this.modelMapper.map(operacion, OperacionEntity.class);
        this.operacionRepository.save(operacionEntity);

        CuentaBancaria cuentaBancaria = this.existeCuenta(operacion.getCuentaDestino());
        if(cuentaBancaria != null){
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() + operacion.getImporte());
            this.cuentaBancariaService.add(cuentaBancaria);
        }
        return "Operación realizada con éxito";
    }

    @Override
    public String transfer(Operacion operacion) {
        if(operacion.getId() == null)
            operacion.setId(UUID.randomUUID().toString());

        operacion.setFecha(Instant.now());
        double saldoAModificar = 0.00;
        OperacionEntity operacionEntity = this.modelMapper.map(operacion, OperacionEntity.class);
        this.operacionRepository.save(operacionEntity);
        saldoAModificar = operacion.getImporte();

        if(!operacion.getCuentaOrigen().trim().substring(4, 8).equals(operacion.getCuentaDestino().trim().substring(4, 8))){
            Operacion intereses = this.crearOperacionIntereses(operacion.getCuentaOrigen());
            saldoAModificar += intereses.getImporte();
            this.operacionRepository.save(this.modelMapper.map(intereses, OperacionEntity.class));
        }

        CuentaBancaria cuentaBancaria = this.existeCuenta(operacion.getCuentaDestino());
        if(cuentaBancaria != null){
            cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() + operacion.getImporte());
            this.cuentaBancariaService.add(cuentaBancaria);
        }

        CuentaBancaria cuentaBancariaOrigen = this.existeCuenta(operacion.getCuentaOrigen());
        if(cuentaBancariaOrigen != null){
            cuentaBancariaOrigen.setSaldo(cuentaBancariaOrigen.getSaldo() - saldoAModificar);
            this.cuentaBancariaService.add(cuentaBancariaOrigen);
        }
        return "Operación realizada con éxito";
    }

    private CuentaBancaria existeCuenta(String iban) {
        return this.cuentaBancariaService.findByIban(iban);
    }

    private Operacion crearOperacionIntereses(String cuentaOrigen) {
        Operacion operacion = new Operacion();
        operacion.setId(UUID.randomUUID().toString());
        operacion.setDescripcion("Intereses");
        operacion.setImporte(3.99);
        operacion.setFecha(Instant.now());
        operacion.setCuentaOrigen(cuentaOrigen);
        return operacion;
    }
}
