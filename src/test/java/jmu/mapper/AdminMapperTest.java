package jmu.mapper;

import jmu.vo.Admin;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;
    @Test
    void queryAdmin() {
        List<Admin> adminList = adminMapper.queryAdmin();
        assertEquals(2,adminList.size());
    }
}