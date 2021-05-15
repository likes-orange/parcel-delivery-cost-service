package xyz.mynt.parcedeliverycost.service.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import xyz.mynt.parceldeliverycost.constant.ErrorMessages;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;
import xyz.mynt.parceldeliverycost.integration.voucher.VoucherClient;
import xyz.mynt.parceldeliverycost.integration.voucher.dto.VoucherResponse;
import xyz.mynt.parceldeliverycost.service.integration.V1VoucherClientService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class V1VoucherClientServiceTest {

    @Mock
    private VoucherClient voucherClient;

    @InjectMocks
    private V1VoucherClientService v1VoucherClientService;

    @Test
    public void testGetDiscountSuccess() {
        ReflectionTestUtils.setField(v1VoucherClientService, "apikey", "apikey");
        String voucherCode = "MYNY";
        VoucherResponse voucherResponse = VoucherResponse.builder()
                .code(voucherCode)
                .discount(10D)
                .expiry(LocalDate.now().plusDays(1))
                .build();

        when(voucherClient.getVoucher(any(), anyString())).thenReturn(voucherResponse);
        Double discount = v1VoucherClientService.getDiscount(voucherCode);
        Assert.assertEquals(voucherResponse.getDiscount(), discount);
    }

    @Test
    public void testGetDiscountExpired() {
        ReflectionTestUtils.setField(v1VoucherClientService, "apikey", "apikey");
        String voucherCode = "MYNY";
        VoucherResponse voucherResponse = VoucherResponse.builder()
                .code(voucherCode)
                .discount(10D)
                .expiry(LocalDate.now().minusDays(1))
                .build();

        when(voucherClient.getVoucher(any(), anyString())).thenReturn(voucherResponse);
        try {
            v1VoucherClientService.getDiscount(voucherCode);
        } catch (BadRequestException e) {
            Assert.assertEquals(ErrorMessages.VOUCHER_CODE_ALREADY_EXPIRED, e.getMessage());
        }
    }
}
