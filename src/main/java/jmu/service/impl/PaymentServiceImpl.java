package jmu.service.impl;

import jmu.mapper.PaymentMapper;
import jmu.service.PaymentService;
import jmu.vo.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public List<Payment> queryPayment() {
        return this.paymentMapper.queryPayment();
    }

    @Override
    public List<Payment> queryPaymentByRid(List<Integer> rIdList) {
        return this.paymentMapper.queryPaymentByRid(rIdList);
    }

    @Override
    public Payment selectByRId(Integer rId) {
        return this.paymentMapper.selectByRId(rId);
    }

    @Override
    public Integer insert(Payment payment) {
        return this.paymentMapper.insert(payment);
    }

    @Override
    public Integer delete(Integer rId) {
        return this.paymentMapper.delete(rId);
    }
}
