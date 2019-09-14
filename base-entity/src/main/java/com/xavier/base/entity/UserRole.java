package com.xavier.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xavier.bean.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户角色对应关系Bean
 *
 * @author NewGr8Player
 */
@TableName("sys_user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseEntity {

	/**
	 * 用户Id
	 */
	@TableField("user_id")
	private String userId;

	/**
	 * 角色Id
	 */
	@TableField("role_id")
	private String roleId;
}
