package jmu.service;

import jmu.vo.User;

import java.util.HashMap;
import java.util.List;

public interface UserService  {
    public List<User> queryUser();
    public User userLogin(HashMap<String,String> hashMap);
    public User selectByAccount(String uAccount);
    public User selectById(Integer uId);
    public User selectByCarnum(String uCarnum);
    public List<User> selectByType(Integer type);
    public Integer insertUser(User user);
    public Integer updateUser(User user);
    public Integer deleteByUId(Integer uId);
}
