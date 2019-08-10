package com.application.passbook.merchants.vo;

import com.application.passbook.merchants.constant.ErrorCode;
import com.application.passbook.merchants.dao.MerchantsDAO;
import com.application.passbook.merchants.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建商户请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsRequest {

    private String name;

    private String logoUrl;

    private String businessLicenseUrl;

    private String phone;

    private String address;

    /**
     * 验证请求有效性
     * @param merchantsDAO
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDAO merchantsDAO) {
        if (merchantsDAO.findByName(this.name) != null) {
            return ErrorCode.DUPLICATE_NAME;
        }

        if (null == this.logoUrl) {
            return ErrorCode.EMPTY_LOGO;
        }

        if (null == this.businessLicenseUrl) {
            return ErrorCode.EMPTY_BUSINESS_LICENSE;
        }

        if (null == this.address) {
            return ErrorCode.EMPTY_ADDRESS;
        }

        if (null == this.phone) {
            return ErrorCode.ERROR_PHONE;
        }

        return ErrorCode.SUCCESS;
    }

    public Merchants toMerchants() {

        Merchants merchants = new Merchants();

        merchants.setName(this.name);
        merchants.setLogoUrl(this.logoUrl);
        merchants.setBusinessLicenseUrl(this.businessLicenseUrl);
        merchants.setAddress(this.address);
        merchants.setPhone(this.phone);

        return merchants;
    }
}
