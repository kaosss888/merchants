package com.application.passbook.merchants.vo;

import com.application.passbook.merchants.constant.ErrorCode;
import com.application.passbook.merchants.dao.MerchantsDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 投放的优惠券对象定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplate {

    private Integer id;

    private String title;

    private String summary;

    private String desc;

    private Long limit;

    /**
     * 优惠券是否有token，用于商户核销，token存储于Redis Set中，每次领取从Redis中获取
     */
    private Boolean hasToken;

    private Integer background;

    private Date start;

    private Date end;

    public ErrorCode validate(MerchantsDAO merchantsDao) {
        if (null == merchantsDao.findById(id)) {
            return ErrorCode.MERCHANTS_NOT_EXIST;
        }

        return ErrorCode.SUCCESS;
    }
}
