package jmu.mapper;

import jmu.vo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    public List<Admin> queryAdmin();
    public Admin adminLogin(HashMap<String,String> hashMap);
}
