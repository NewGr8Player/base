package com.xavier.base.entity;

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
public abstract class BaseEntity<ID> implements Serializable {

    /* 主键Id */
    private ID id;

    /* 数据创建者Id */
    private ID createBy;

    /* 数据创建时间 */
    private Date createDateTime;

    /* 数据最后更新者Id */
    private ID updateBy;

    /* 数据最后更新时间 */
    private Date updateDateTime;

    /**
     * 插入前调用
     */
    protected void onInsert(ID currentId, Date currentDateTime) {
        this.createBy = this.updateBy = currentId;
        this.createDateTime = this.updateDateTime = currentDateTime;
    }

    /**
     * 更新前调用
     */
    protected void onUpdate(ID currentId, Date currentDateTime) {
        this.updateBy = currentId;
        this.updateDateTime = currentDateTime;
    }

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
