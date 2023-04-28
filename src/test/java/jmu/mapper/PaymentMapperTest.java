package jmu.mapper;

import jmu.vo.DayPayment;
import jmu.vo.Payment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class PaymentMapperTest {
    @Autowired
    private PaymentMapper paymentMapper;

    @Test
    void queryPayment() {
        List<Payment> payments = this.paymentMapper.queryPayment();
    System.out.println(payments);
    }

    @Test
    void selectByRId() {
        Payment payment = this.paymentMapper.selectByRId(1);
    System.out.println(payment);
    }

    @Test
    void insert() {
        Payment payment = new Payment();
        payment.setPMethod("微信");
        payment.setPSum(0);
        payment.setPTime(new Date());
        payment.setRId(4);
        this.paymentMapper.insert(payment);
    }

    @Test
    void delete() {
        this.paymentMapper.delete(4);
    }

    @Test
    void queryByRIdList(){
        List<Integer> rIdList = new ArrayList<>();
        rIdList.add(24);
        rIdList.add(3);
        rIdList.add(2);
        List<Payment> payments = this.paymentMapper.queryPaymentByRid(rIdList);
    System.out.println(payments);
    }

    @Test
    void chart(){
        List<DayPayment> list = this.paymentMapper.selectSumByDay();
    System.out.println(list);
    }
}