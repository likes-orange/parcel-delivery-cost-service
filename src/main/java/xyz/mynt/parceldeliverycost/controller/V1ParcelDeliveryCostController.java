package xyz.mynt.parceldeliverycost.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mynt.parceldeliverycost.constant.ResponseCode;
import xyz.mynt.parceldeliverycost.dto.BaseResponse;
import xyz.mynt.parceldeliverycost.dto.ParcelDeliveryCostDto;
import xyz.mynt.parceldeliverycost.dto.ParcelDto;
import xyz.mynt.parceldeliverycost.service.ParcelDeliveryCostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/parcel-delivery-cost")
public class V1ParcelDeliveryCostController {

    private final ParcelDeliveryCostService v1ParcelDeliveryCostService;

    @PostMapping("/calculate")
    @ApiOperation(value = "Calculate Delivery Cost of a Parcel", nickname = "/calculate")
    public BaseResponse<ParcelDeliveryCostDto> calculateDeliveryCost(@RequestBody @Validated ParcelDto parcelDto) {
        return BaseResponse.<ParcelDeliveryCostDto>builder()
                .responseCode(ResponseCode.PDC20001)
                .data(v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto))
                .build();
    }

}
