package com.sise.blog.admin.service;

import com.sise.blog.admin.pojo.Admin;

public interface AdminService {

    public Admin findAdminByUserName(String username);

}
