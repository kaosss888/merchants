package com.application.passbook.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import com.application.passbook.merchants.constant.Constants;
import com.application.passbook.merchants.constant.ErrorCode;
import com.application.passbook.merchants.dao.MerchantsDAO;
import com.application.passbook.merchants.entity.Merchants;
import com.application.passbook.merchants.service.IMerchantsService;
import com.application.passbook.merchants.vo.CreateMerchantsRequest;
import com.application.passbook.merchants.vo.CreateMerchantsResponse;
import com.application.passbook.merchants.vo.PassTemplate;
import com.application.passbook.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * <h1>商户服务接口实现</h1>
 */
@Slf4j
@Service
public class MerchantsServiceImpl implements IMerchantsService {

    @Autowired
    private MerchantsDAO merchantsDAO;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        ErrorCode errorCode = request.validate(merchantsDAO);
        if (errorCode != ErrorCode.SUCCESS) {
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        } else {
            merchantsResponse.setId(merchantsDAO.save(request.toMerchants()).getId());
        }

        response.setData(merchantsResponse);

        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        Response response = new Response();
        Merchants merchants = null;
        Optional<Merchants> optionalMerchants = merchantsDAO.findById(id);

        if (!optionalMerchants.isPresent()) {
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXIST.getCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXIST.getDesc());
        } else {
            merchants = optionalMerchants.get();
        }

        response.setData(merchants);

        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {

        Response response = new Response();

        ErrorCode errorCode = template.validate(merchantsDAO);

        if (errorCode != ErrorCode.SUCCESS) {
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        } else {
            String passTemplate = JSON.toJSONString(template);
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplate: {}", passTemplate);
        }

        return response;
    }
}
