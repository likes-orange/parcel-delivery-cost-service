package xyz.mynt.parcedeliverycost.service;

import com.sun.javafx.binding.StringFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.mynt.parceldeliverycost.constant.ErrorMessages;
import xyz.mynt.parceldeliverycost.constant.RuleType;
import xyz.mynt.parceldeliverycost.dto.ParcelDeliveryCostDto;
import xyz.mynt.parceldeliverycost.dto.ParcelDto;
import xyz.mynt.parceldeliverycost.entity.DeliveryCostRule;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;
import xyz.mynt.parceldeliverycost.exception.NotFoundException;
import xyz.mynt.parceldeliverycost.repository.DeliveryCostRuleRepository;
import xyz.mynt.parceldeliverycost.service.ParcelDeliveryCostService;
import xyz.mynt.parceldeliverycost.service.V1ParcelDeliveryCostService;
import xyz.mynt.parceldeliverycost.service.integration.VoucherClientService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class V1ParcelDeliveryCostServiceTest {

    @Mock
    private DeliveryCostRuleRepository deliveryCostRuleRepository;

    @Mock
    private VoucherClientService voucherClientService;

    @InjectMocks
    private V1ParcelDeliveryCostService v1ParcelDeliveryCostService;

    @Test
    public void testCalculateDeliveryCostWithoutVoucherSuccess() {
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(12D)
                .build();

        List<DeliveryCostRule> deliveryCostRuleList = Collections.singletonList(
                DeliveryCostRule.builder()
                        .active(true)
                        .cost(10D)
                        .criteriaOperator(">")
                        .criteriaValue(10D)
                        .name("Heavy Parcel")
                        .id(1)
                        .sortOrder(10)
                        .type(RuleType.WEIGHT)
                        .build());

        when(deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc()).thenReturn(deliveryCostRuleList);

        ParcelDeliveryCostDto parcelDeliveryCostDto = v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto);

        assertNotNull(parcelDeliveryCostDto);
        assertNotNull(parcelDeliveryCostDto.getDeliveryCost());
        assertEquals(Double.valueOf(deliveryCostRuleList.get(0).getCost() * parcelDto.getWeight()),
                parcelDeliveryCostDto.getDeliveryCost());
    }

    @Test
    public void testCalculateDeliveryCostWithVoucherSuccess() {
        double discount = 10D;
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(12D)
                .voucherCode("VALID")
                .build();

        List<DeliveryCostRule> deliveryCostRuleList = Collections.singletonList(
                DeliveryCostRule.builder()
                        .active(true)
                        .cost(10D)
                        .criteriaOperator(">")
                        .criteriaValue(10D)
                        .name("Heavy Parcel")
                        .id(1)
                        .sortOrder(10)
                        .type(RuleType.WEIGHT)
                        .build());

        when(deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc()).thenReturn(deliveryCostRuleList);
        when(voucherClientService.getDiscount(any())).thenReturn(discount);

        ParcelDeliveryCostDto parcelDeliveryCostDto = v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto);

        assertNotNull(parcelDeliveryCostDto);
        assertNotNull(parcelDeliveryCostDto.getDeliveryCost());
        assertEquals(Double.valueOf((deliveryCostRuleList.get(0).getCost() * parcelDto.getWeight()) - discount),
                parcelDeliveryCostDto.getDeliveryCost());
    }

    @Test
    public void testCalculateDeliveryCostNoDeliveryCostRulesFoundError() {
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(12D)
                .build();

        when(deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc()).thenReturn(Collections.emptyList());

        try {
            v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto);
        } catch (NotFoundException e) {
            assertEquals(ErrorMessages.DELIVERY_COST_RULES_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testCalculateDeliveryCostNoValidDeliveryCostRulesFoundError() {
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(5D)
                .build();

        List<DeliveryCostRule> deliveryCostRuleList = Collections.singletonList(
                DeliveryCostRule.builder()
                        .active(true)
                        .cost(10D)
                        .criteriaOperator(">")
                        .criteriaValue(10D)
                        .name("Heavy Parcel")
                        .id(1)
                        .sortOrder(10)
                        .type(RuleType.WEIGHT)
                        .build());

        when(deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc()).thenReturn(deliveryCostRuleList);

        try {
            v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto);
        } catch (BadRequestException e) {
            assertEquals(ErrorMessages.NO_VALID_DELIVERY_COST, e.getMessage());
        }
    }

    @Test
    public void testCalculateDeliveryCostRejectError() {
        ParcelDto parcelDto = ParcelDto.builder()
                .height(12D)
                .length(12D)
                .width(12D)
                .weight(51D)
                .build();

        List<DeliveryCostRule> deliveryCostRuleList = Collections.singletonList(
                DeliveryCostRule.builder()
                        .active(true)
                        .criteriaOperator(">")
                        .criteriaValue(50D)
                        .name("Reject")
                        .id(1)
                        .sortOrder(10)
                        .type(RuleType.WEIGHT)
                        .build());

        when(deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc()).thenReturn(deliveryCostRuleList);

        try {
            v1ParcelDeliveryCostService.calculateDeliveryCost(parcelDto);
        } catch (BadRequestException e) {
            assertEquals(String.format(ErrorMessages.REQUEST_NOT_ALLOWED,
                    deliveryCostRuleList.get(0).getType(),
                    deliveryCostRuleList.get(0).getCriteriaOperator(),
                    deliveryCostRuleList.get(0).getCriteriaValue()),
                    e.getMessage());
        }
    }
}
