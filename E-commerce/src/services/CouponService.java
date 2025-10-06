package services;

import entities.Coupon;
import repositories.CouponRepository;

import java.time.LocalDate;
import java.util.*;


public class CouponService {

    private final CouponRepository repository;

    public CouponService(CouponRepository repository) {
        this.repository = repository;
    }

    public Coupon save(String code, double value, boolean percentage, LocalDate validity, boolean used) {

        Coupon coupon =  new Coupon(code, value, percentage, validity, used);
        repository.save(coupon);
        return coupon;
    }

    public void findAll() {

        List<Coupon> list = repository.findAll();
        if(list.isEmpty()) {
            System.out.println("Nenhum cupom cadastrado.");
        }
            for(Coupon coupon : list){
                    coupon.displayDetails();
                }
            }

    public Optional<Coupon> findByCode(String code) {

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Código do cupom não pode ser nulo ou vazio.");
        }
        try {
            return repository.findAll()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(c -> c.getCode().equalsIgnoreCase(code))
                    .findFirst();
        } catch (Exception e) {
            System.out.println("Erro ao buscar cupom pelo código: " + code + ". " + e.getMessage());
            return Optional.empty();
        }
    }

    public void expire(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do cupom não pode ser nulo.");
        }
        Optional<Coupon> obj = repository.findById(id);
        if (obj.isPresent()){
            Coupon coupon = obj.get();
            coupon.markAsUsed();
            repository.save(coupon);
            System.out.println("Cupom com ID " + id + " marcado como usado.");
            System.out.println("Cupom expirado ID: " + coupon.getId());
        } else {
            System.out.println("Cupom com ID " + id + " não encontrado.");

        }
    }
}
