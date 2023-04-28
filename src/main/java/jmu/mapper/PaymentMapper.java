package jmu.mapper;

import jmu.vo.DayPayment;
import jmu.vo.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaymentMapper {
    public List<Payment> queryPayment();
    public List<Payment> queryPaymentByRid(List<Integer> rIdList);
    public Payment selectByRId(Integer rId);
    public Integer insert(Payment payment);
    public Integer delete(Integer rId);
    public Integer update(Payment payment);
    public List<DayPayment> selectSumByDay();
}
