package com.ylt.service;

import com.ylt.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun
 * @date 2019/9/18 16:55
 * 自定义认证类
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private SellerService sellerService;
    public void setSellerService(SellerService sellerService){
        this.sellerService=sellerService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 权限集合
        List<GrantedAuthority> arrayList = new ArrayList<GrantedAuthority>();

        // 具有什么样的权限
        arrayList.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        // 1 判断用户名是否为空  如果为null 返回null
        if(username==null){
            return null;
        }

        //2 根据名到数据库查询对应的用户对象
        Seller seller = sellerService.findOne(username);
        //3 如果用户名找不到 返回null
        if(seller!=null){
            // 4 如果找到了  判断用户审核状态  如果未通过 fanhui null
            if("1".equals(seller.getStatus())){
//                new AuthenticationManagerBuilder().userDetailsService(sellerService).passwordEncoder(new BCryptPasswordEncoder());
                // 5 返回springSecurity的User对象   将这个用户 密码 所应该具有的访问权限集合返回
                return new User(username,seller.getPassword(),arrayList);
            }

        }

       return null;
    }
}
