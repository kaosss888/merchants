package com.application.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {

    /**
     * 商户id; 创建失败为-1
     */
    private Integer id;
}
