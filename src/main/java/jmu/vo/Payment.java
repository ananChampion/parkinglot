package jmu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class Payment {
    private Integer pId;//支付记录id
    private Integer rId;//停车记录id
    private Integer pSum;//收费金额
    private String pMethod;//支付方式：支付宝、微信、现金

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pTime;//收费时间

}
