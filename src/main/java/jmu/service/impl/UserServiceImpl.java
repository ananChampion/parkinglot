package jmu.service.impl;

import jmu.mapper.UserMapper;
import jmu.service.UserService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> queryUser() {
        return this.userMapper.queryUser();
    }

    public User userLogin(HashMap<String, String> hashMap) {
        return this.userMapper.userLogin(hashMap);
    }

    public User selectByAccount(String uAccount){
        return this.userMapper.selectByAccount(uAccount);
    }

    public User selectById(Integer uId){
        return this.userMapper.selectById(uId);
    }

    public User selectByCarnum(String uCarnum){
        return this.userMapper.selectByCarnum(uCarnum);
    }

    public List<User> selectByType(Integer type){
        return this.userMapper.selectByType(type);
    }

    public Integer insertUser(User user) {
        return this.userMapper.insertUser(user);
    }

    public Integer updateUser(User user){
        return this.userMapper.updateUser(user);
    }

    public Integer deleteByUId(Integer uId){
        return this.userMapper.deleteByUId(uId);
    }
}
