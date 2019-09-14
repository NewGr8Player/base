package com.xavier.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xavier.bean.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 权限角色对应关系Bean
 *
 * @author NewGr8Player
 */
@TableName("sys_role_permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseEntity {

	/**
	 * 角色Id
	 */
	@TableField("role_id")
	private String roleId;
	/**
	 * 权限Id
	 */
	@TableField("permission_id")
	private String permissionId;
}
