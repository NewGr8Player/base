package com.xavier.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据对象
 *
 * @param <ID> Id的泛型
 * @author NewGr8Player
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BaseEntity<ID> implements Serializable {

    /* 主键Id */
    @TableId(value = "id", type = IdType.UUID)
    protected ID id;

    /* 数据创建者Id */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected ID createBy;

    /* 数据创建时间 */
    @TableField(value = "create_date_time", fill = FieldFill.INSERT)
    protected Date createDateTime;

    /* 数据最后更新者Id */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected ID updateBy;

    /* 数据最后更新时间 */
    @TableField(value = "update_date_time", fill = FieldFill.INSERT_UPDATE)
    protected Date updateDateTime;

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    protected Integer deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(createBy, that.createBy) &&
                Objects.equal(createDateTime, that.createDateTime) &&
                Objects.equal(updateBy, that.updateBy) &&
                Objects.equal(updateDateTime, that.updateDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, createBy, createDateTime, updateBy, updateDateTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("createBy", createBy)
                .add("createDateTime", createDateTime)
                .add("updateBy", updateBy)
                .add("updateDateTime", updateDateTime)
                .toString();
    }
}
