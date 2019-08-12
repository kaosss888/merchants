package com.application.passbook.merchants.controller;

import com.alibaba.fastjson.JSON;
import com.application.passbook.merchants.service.IMerchantsService;
import com.application.passbook.merchants.vo.CreateMerchantsRequest;
import com.application.passbook.merchants.vo.PassTemplate;
import com.application.passbook.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>商户服务Controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {

    @Autowired
    private IMerchantsService iMerchantsService;

    @ResponseBody
    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {
        log.info("CreateMerchants: {}", JSON.toJSONString(request));
        return iMerchantsService.createMerchants(request);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Response buildMerchantsInfo(@PathVariable("id") Integer id) {
        log.info("BuildMerchantsInfo: {}", id);
        return iMerchantsService.buildMerchantsInfoById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public Response dropPassTemplate(@RequestBody PassTemplate passTemplate) {
        log.info("DropPassTemplate: {}", passTemplate);
        return iMerchantsService.dropPassTemplate(passTemplate);
    }
}
