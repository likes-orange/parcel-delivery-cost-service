package xyz.mynt.parceldeliverycost.service;

import xyz.mynt.parceldeliverycost.dto.ParcelDeliveryCostDto;
import xyz.mynt.parceldeliverycost.dto.ParcelDto;

public interface ParcelDeliveryCostService {

    ParcelDeliveryCostDto calculateDeliveryCost(ParcelDto parcelDto);

}
