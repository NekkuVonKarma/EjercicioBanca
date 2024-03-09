package es.neesis.banca.model;

public class CuentaBancaria {

    private String id;
    private String titular;
    private String iban;
    private double saldo;

    public CuentaBancaria() {
    }

    public CuentaBancaria(String id, String titular, String iban, double saldo) {
        this.id = id;
        this.titular = titular;
        this.iban = iban;
        this.saldo = saldo;
    }

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
