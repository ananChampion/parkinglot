package jmu.mapper;

import jmu.vo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void userLogin(){
        String account = "201921122029";
        String pwd = "123456";
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uAccount",account);
        hashMap.put("uPwd",pwd);
        User user = this.userMapper.userLogin(hashMap);
        System.out.println(user);
    }

    @Test
    void userInsert(){
//        String account = "201921122009";
//        String pwd = "123456";
//        String carnum = "闽D67890";
//        String tel = "13539260002";
//        Integer type = 1;
//        String name = "叶锦程";
//        User user = new User();
//        user.setUAccount(account);
//        user.setUCarnum(carnum);
//        user.setUPwd(pwd);
//        user.setUTel(tel);
//        user.setUName(name);
//        user.setUType(type);
//        Integer num = this.userMapper.insertUser(user);
//        System.out.println(num);
    }

    @Test
    void queryUser(){
        List<User> list = this.userMapper.queryUser();
        System.out.println(list);
    }

    @Test
    void selectUser(){
        User user1 = new User();
        User user2 = new User();
        String uAccount = "201921122029";
        String uCarnum = "鲁B907QU";
        user1 = this.userMapper.selectByAccount(uAccount);
        user2 = this.userMapper.selectByCarnum(uCarnum);
    System.out.println(user1);
    System.out.println(user2);
    }

    @Test
    void update()
    {
//        String uAccount = "201921122009";
//        User user = this.userMapper.selectByAccount(uAccount);
//        user.setUTel("15359200002");
//        Integer num = this.userMapper.updateUser(user);
//
//    System.out.println(num);
//        user = this.userMapper.selectByAccount(uAccount);
//    System.out.println(user);
    }

    @Test
    void selectByType(){
        Integer type = 1;
        List<User> users = this.userMapper.selectByType(type);
        System.out.println(users);
        users = this.userMapper.selectByType(2);
        System.out.println(users);
    }

    @Test
    void selectById(){
        User user = this.userMapper.selectById(1);
    System.out.println(user);
    }

}