package jmu.service;

import jmu.vo.Admin;

import java.util.HashMap;
import java.util.List;

public interface AdminService {
    public List<Admin> queryAdmin();
    public Admin adminLogin(HashMap<String,String> hashMap);

}
