package xyz.mynt.parceldeliverycost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.mynt.parceldeliverycost.constant.ErrorMessages;
import xyz.mynt.parceldeliverycost.constant.RuleType;
import xyz.mynt.parceldeliverycost.dto.ParcelDeliveryCostDto;
import xyz.mynt.parceldeliverycost.dto.ParcelDto;
import xyz.mynt.parceldeliverycost.entity.DeliveryCostRule;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;
import xyz.mynt.parceldeliverycost.exception.NotFoundException;
import xyz.mynt.parceldeliverycost.repository.DeliveryCostRuleRepository;
import xyz.mynt.parceldeliverycost.service.integration.VoucherClientService;
import xyz.mynt.parceldeliverycost.util.CriteriaUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class V1ParcelDeliveryCostService implements ParcelDeliveryCostService {

    private final DeliveryCostRuleRepository deliveryCostRuleRepository;

    private final VoucherClientService v1VoucherClientService;

    @Override
    public ParcelDeliveryCostDto calculateDeliveryCost(ParcelDto parcelDto) {
        List<DeliveryCostRule> deliveryCostRuleList = deliveryCostRuleRepository.findAllByActiveTrueOrderBySortOrderAsc();
        if (CollectionUtils.isEmpty(deliveryCostRuleList)) {
            throw new NotFoundException(ErrorMessages.DELIVERY_COST_RULES_NOT_FOUND);
        }

        ParcelDeliveryCostDto parcelDeliveryCostDto = new ParcelDeliveryCostDto();
        deliveryCostRuleList.forEach(deliveryCostRule -> {
            if (Objects.nonNull(parcelDeliveryCostDto.getDeliveryCost())) {
                return;
            }
            switch(deliveryCostRule.getType()) {
                case WEIGHT:
                    buildParcelDeliveryCost(parcelDeliveryCostDto, deliveryCostRule, parcelDto.getWeight());
                    break;
                case VOLUME:
                    buildParcelDeliveryCost(parcelDeliveryCostDto, deliveryCostRule, parcelDto.getVolume());
                    break;
                default:
                    break;
            }
        });
        if (Objects.isNull(parcelDeliveryCostDto.getDeliveryCost())) {
            throw new BadRequestException(ErrorMessages.NO_VALID_DELIVERY_COST);
        }

        return applyVoucherDiscount(parcelDeliveryCostDto, parcelDto.getVoucherCode());
    }

    private void buildParcelDeliveryCost(ParcelDeliveryCostDto parcelDeliveryCostDto, DeliveryCostRule deliveryCostRule, Double parcelVariable) {
        if (CriteriaUtil.compareToCriteria(parcelVariable, deliveryCostRule.getCriteriaOperator(), deliveryCostRule.getCriteriaValue())) {
            if (Objects.isNull(deliveryCostRule.getCost())) {
                throw new BadRequestException(String.format(ErrorMessages.REQUEST_NOT_ALLOWED,
                        deliveryCostRule.getType().toString(), deliveryCostRule.getCriteriaOperator(),
                        deliveryCostRule.getCriteriaValue()));
            }
            Double deliveryCost = parcelVariable * deliveryCostRule.getCost();
            parcelDeliveryCostDto.setDeliveryCost(deliveryCost);
        }
    }

    private ParcelDeliveryCostDto applyVoucherDiscount(ParcelDeliveryCostDto parcelDeliveryCostDto, String voucherCode) {
        if(!StringUtils.isEmpty(voucherCode)) {
            Double discount = v1VoucherClientService.getDiscount(voucherCode);
            Double discountedDeliveryCost = parcelDeliveryCostDto.getDeliveryCost() - discount;
            parcelDeliveryCostDto.setDeliveryCost(discountedDeliveryCost);
        }

        return parcelDeliveryCostDto;
    }
}

