package es.neesis.banca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "cuenta_bancaria")
@Entity
public class CuentaBancariaEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITULAR")
    private String titular;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "SALDO")
    private double saldo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
