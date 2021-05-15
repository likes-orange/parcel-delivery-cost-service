package xyz.mynt.parceldeliverycost.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;
import xyz.mynt.parceldeliverycost.integration.voucher.VoucherClient;
import xyz.mynt.parceldeliverycost.integration.voucher.dto.VoucherResponse;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class V1VoucherClientService implements VoucherClientService {

    private final VoucherClient voucherClient;

    @Value("${voucher-service.apikey}")
    private String apikey;
    
    @Override
    public Double getDiscount(String voucherCode) {
        VoucherResponse voucher = voucherClient.getVoucher(voucherCode, apikey);
        if (LocalDate.now().isAfter(voucher.getExpiry())) {
            throw new BadRequestException("Voucher code already expired.");
        }
        return voucher.getDiscount();
    }
}
