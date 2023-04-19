package jmu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Fee {
    private Integer fid;//收费规则id
    private Integer fCost;//停车单价
    private Integer fMax;//最高费用
}
