package jmu.service;

import jmu.vo.DayPayment;
import jmu.vo.Payment;

import java.util.List;

public interface PaymentService {
    public List<Payment> queryPayment();
    public List<Payment> queryPaymentByRid(List<Integer> rIdList);
    public Payment selectByRId(Integer rId);
    public Integer insert(Payment payment);
    public Integer delete(Integer rId);
    public Integer update(Payment payment);
    public List<DayPayment> selectSumByDay();


}
