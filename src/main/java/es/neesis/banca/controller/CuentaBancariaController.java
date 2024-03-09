package es.neesis.banca.controller;

import es.neesis.banca.model.CuentaBancaria;
import es.neesis.banca.service.ICuentaBancariaService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cuentaBancaria")
public class CuentaBancariaController {

    private ICuentaBancariaService cuentaBancariaService;

    public CuentaBancariaController(ICuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    @GetMapping("/init")
    public String init() {
        String response = this.cuentaBancariaService.init();
        return response;
    }

    @GetMapping("/list")
    public List<CuentaBancaria> list() {
        List<CuentaBancaria> response = this.cuentaBancariaService.list();
        return response;
    }

    @PostMapping("/add")
    public CuentaBancaria add(@RequestBody CuentaBancaria cuentaBancaria){
        CuentaBancaria response = this.cuentaBancariaService.add(cuentaBancaria);
        return response;
    }
}
