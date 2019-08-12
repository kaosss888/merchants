package com.application.passbook.merchants.service;

import com.alibaba.fastjson.JSON;
import com.application.passbook.merchants.vo.CreateMerchantsRequest;
import com.application.passbook.merchants.vo.PassTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * <h1>商户服务测试类</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServiceTest {

    @Autowired
    private IMerchantsService merchantsService;

    @Test
    public void testCreateMerchantsService() {
        CreateMerchantsRequest createMerchantsRequest = new CreateMerchantsRequest();

        createMerchantsRequest.setName("merchants");
        createMerchantsRequest.setLogoUrl("www.xxx.com");
        createMerchantsRequest.setBusinessLicenseUrl("www.xxx.com");
        createMerchantsRequest.setPhone("13888888888");
        createMerchantsRequest.setAddress("武汉市");

        System.out.println(JSON.toJSONString(merchantsService.createMerchants(createMerchantsRequest)));
    }

    @Test
    public void testBuildMerchantsInfoById() {

        System.out.println(JSON.toJSONString(merchantsService.buildMerchantsInfoById(7)));
    }

    @Test
    public void testDropPassTemplate() {

        PassTemplate passTemplate = new PassTemplate();

        passTemplate.setId(5);
        passTemplate.setTitle("title: test passTemplate");
        passTemplate.setSummary("summary: test summary");
        passTemplate.setDesc("description: test description");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(
                merchantsService.dropPassTemplate(passTemplate)
        ));
    }
}
