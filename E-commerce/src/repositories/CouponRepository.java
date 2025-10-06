package repositories;

import database.InMemory;
import entities.Coupon;

import java.io.File;

public class CouponRepository extends InMemory<Coupon> {


    public CouponRepository(File file) {
        super(file, Coupon.class);
    }
}
