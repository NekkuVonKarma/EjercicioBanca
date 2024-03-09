package es.neesis.banca.controller;

import es.neesis.banca.model.Operacion;
import es.neesis.banca.service.IOperacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operacion")
public class OperacionController {

    private IOperacionService operacionService;

    public OperacionController(IOperacionService operacionService) {
        this.operacionService = operacionService;
    }

    @GetMapping("/list/{cuenta}")
    public List<Operacion> list(@PathVariable("cuenta") String idCuentaBancaria) {
        List<Operacion> response = this.operacionService.list(idCuentaBancaria);
        return response;
    }

    @PostMapping("/add")
    public String add(@RequestBody Operacion operacion) {
        String response = this.operacionService.add(operacion);
        return response;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody Operacion operacion) {
        String response = this.operacionService.transfer(operacion);
        return response;
    }
}
