package jmu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Integer uId;//用户id
    private String uAccount;//用户账号
    private String uPwd;//用户密码
    private String uName;//用户姓名
    private String uCarnum;//用户车牌号
    private String uTel;//用户电话
    private Integer uType;//用户类型 1：职工 2：外来用户
}
