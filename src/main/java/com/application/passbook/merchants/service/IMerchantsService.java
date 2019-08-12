package com.application.passbook.merchants.service;

import com.application.passbook.merchants.vo.CreateMerchantsRequest;
import com.application.passbook.merchants.vo.PassTemplate;
import com.application.passbook.merchants.vo.Response;

/**
 * 商户服务接口定义
 */
public interface IMerchantsService {

    /**
     * <h2>创建商户请求</h2>
     * @param request {@link CreateMerchantsRequest} 创建商户请求
     * @return {@link Response}
     */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * <h2>根据id构造商户信息</h2>
     * @param id
     * @return {@link Response}
     */
    Response buildMerchantsInfoById(Integer id);

    /**
     * <h2>投放优惠券</h2>
     * @param template {@link PassTemplate}
     * @return {@link Response}
     */
    Response dropPassTemplate(PassTemplate template);
}

