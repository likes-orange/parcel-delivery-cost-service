package xyz.mynt.parceldeliverycost.integration.voucher;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.mynt.parceldeliverycost.configuration.FeignClientConfiguration;
import xyz.mynt.parceldeliverycost.integration.voucher.dto.VoucherResponse;

@FeignClient(value = "voucher-service", url = "${voucher-service.url}", configuration = FeignClientConfiguration.class)
public interface VoucherClient {

    @GetMapping("voucher/{voucherCode}")
    VoucherResponse getVoucher(@PathVariable(name = "voucherCode") String voucherCode, @RequestParam(name = "key") String key);

}
