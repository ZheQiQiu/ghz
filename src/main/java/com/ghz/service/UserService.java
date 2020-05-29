package com.ghz.service;

import com.ghz.bean.DistUser;
import com.ghz.bean.mg.Member;
import com.ghz.except.GhzException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    List<DistUser> selectAllUserAndRole() throws GhzException;
    int userLogin(String username,String passwordSha512, HttpSession session) throws GhzException;
    void updateMember(Member member) throws GhzException;
}
