package com.lhy.web.model;


import lombok.Getter;
import lombok.Setter;

/**
 * 客户实体
 * @author liuhaiyan
 * @date 2019-12-25 14:07
 */
@Setter
@Getter
public class Customer {
    /**
     * id
     * */
    private long id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 联系人
     * */
    private String contact;

    /**
     * 电话号码
     * */
    private String telephone;

    /**
     * 邮箱地址
     * */
    private String email;

    /**
     * 备注
     * */
    private String remark;

}
