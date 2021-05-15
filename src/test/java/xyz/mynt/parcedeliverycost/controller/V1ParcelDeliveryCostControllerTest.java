package xyz.mynt.parcedeliverycost.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import xyz.mynt.parceldeliverycost.constant.ResponseCode;
import xyz.mynt.parceldeliverycost.controller.V1ParcelDeliveryCostController;
import xyz.mynt.parceldeliverycost.dto.BaseResponse;
import xyz.mynt.parceldeliverycost.dto.ParcelDeliveryCostDto;
import xyz.mynt.parceldeliverycost.dto.ParcelDto;
import xyz.mynt.parceldeliverycost.service.ParcelDeliveryCostService;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class V1ParcelDeliveryCostControllerTest {

    @Mock
    ParcelDeliveryCostService parcelDeliveryCostService;

    @InjectMocks
    V1ParcelDeliveryCostController v1ParcelDeliveryCostController;

    @Test
    public void testCalculateDeliveryCostSuccess() throws Exception {
        Double deliveryCost = 10D;
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(51D)
                .build();

        when(parcelDeliveryCostService.calculateDeliveryCost(any())).thenReturn(ParcelDeliveryCostDto.builder()
                .deliveryCost(deliveryCost)
                .build());

        BaseResponse<ParcelDeliveryCostDto> baseResponse = v1ParcelDeliveryCostController.calculateDeliveryCost(parcelDto);

        assertEquals(ResponseCode.PDC20001, baseResponse.getResponseCode());
        assertNotNull(baseResponse.getData());
        assertEquals(deliveryCost, baseResponse.getData().getDeliveryCost());
    }
}
