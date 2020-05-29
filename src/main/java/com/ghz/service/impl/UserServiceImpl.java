package com.ghz.service.impl;

import com.ghz.bean.mg.Member;
import com.ghz.bean.mg.User;
import com.ghz.bean.mg.UserExample;
import com.ghz.bean.repository.DistUserMapper;
import com.ghz.bean.DistUser;
import com.ghz.bean.repository.mg.MemberMapper;
import com.ghz.bean.repository.mg.UserMapper;
import com.ghz.enumeration.StatusCodeEnum;
import com.ghz.except.GhzException;
import com.ghz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DistUserMapper distUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<DistUser> selectAllUserAndRole() throws GhzException {
        try {
            return distUserMapper.selectAll();
        } catch (Exception e) {
            throw new GhzException(StatusCodeEnum.DATABASE_EXCEPTION);
        }
    }

    @Override
    public int userLogin(String username, String passwordSha512, HttpSession session) throws GhzException {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        User user;
        try {
            user = userMapper.selectByExample(example).get(0);
        } catch (IndexOutOfBoundsException ioe) {
            throw new GhzException(StatusCodeEnum.USERNAME_PASSWORD_ERROR);
        }

        if(!user.getPasswordSha512().equals(passwordSha512)){
            throw new GhzException(StatusCodeEnum.USERNAME_PASSWORD_ERROR);
        }

        session.setAttribute("user",user);

        return 0;
    }

    @Override
    public void updateMember(Member member) throws GhzException {
        try {
            memberMapper.updateByPrimaryKey(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GhzException(StatusCodeEnum.DATABASE_UPDATE_FAIL);
        }
    }
}
