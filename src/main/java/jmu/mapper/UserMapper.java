package jmu.mapper;

import jmu.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    public List<User> queryUser();
    public User userLogin(HashMap<String,String> hashMap);
    public Integer insertUser(User user);
    public Integer updateUser(User user);
    public User selectByAccount(String uAccount);
    public User selectById(Integer uId);
    public User selectByCarnum(String uCarnum);
    public Integer deleteByUId(Integer uId);
    public List<User> selectByType(Integer type);
}
