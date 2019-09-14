package com.xavier.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xavier.bean.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色Dao
 *
 * @author NewGr8Player
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

	/**
	 * 根据用户Id查找用户角色
	 *
	 * @param userId
	 * @return
	 */
	@Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
	List<UserRole> findByUserId(@Param("userId") String userId);
}
