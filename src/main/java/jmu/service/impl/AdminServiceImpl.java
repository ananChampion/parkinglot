package jmu.service.impl;

import jmu.mapper.AdminMapper;
import jmu.service.AdminService;
import jmu.vo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> queryAdmin(){
        return this.adminMapper.queryAdmin();
    }

    @Override
    public Admin adminLogin(HashMap<String, String> hashMap) {
        return this.adminMapper.adminLogin(hashMap);
    }
}
