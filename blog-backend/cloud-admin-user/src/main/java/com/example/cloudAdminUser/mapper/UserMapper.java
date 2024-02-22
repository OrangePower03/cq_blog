package com.example.cloudAdminUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cloudCommon.domain.eitity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 18:38:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    String findRoleByUserId(Long userId);

    List<Long> findRoleIdByUserId(Long userId);

    int addUserWithRole(@Param("userId") Long userId,
                        @Param("roleIds") List<Long> roleIds);

    int updateStatus(@Param("userId") Long userId,
                     @Param("status") String status);

    int deleteUserWithRole(@Param("userId") Long userId,
                           @Param("roleIds") List<Long> roleIds);

}
