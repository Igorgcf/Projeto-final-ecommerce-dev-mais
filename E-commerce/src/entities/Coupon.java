package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Coupon implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String code;
    private double value;
    private boolean percentage;
    private LocalDate validity;
    private boolean used;

    private static transient final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Coupon(String code, double value, boolean percentage, LocalDate validity, boolean used) {
        this.code = code;
        this.value = value;
        this.percentage = percentage;
        this.validity = validity;
        this.used = used;
    }

    public boolean isValid(){
        return !used && (validity == null || validity.isAfter(LocalDate.now()));
    }

    public void markAsUsed(){
        this.used = true;
    }

    public void displayDetails(){
        System.out.println("*****-------------------------------------------******");
        System.out.println("ID: " + id);
        System.out.println("Código: " + code);
        System.out.println("Valor do desconto: " + value + (percentage ? "%" : ""));
        System.out.println("Validade: " + (validity != null ? validity.format(format) : "Sem validade"));
        System.out.println("Usado: " + (used ? "Sim" : "Não"));
        System.out.println("Válido: " + (isValid() ? "Sim" : "Não"));
        System.out.println("*****-------------------------------------------******");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coupon coupon)) return false;
        return Objects.equals(code, coupon.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
