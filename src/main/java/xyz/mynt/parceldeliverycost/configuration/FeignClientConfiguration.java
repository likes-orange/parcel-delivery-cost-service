package xyz.mynt.parceldeliverycost.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mynt.parceldeliverycost.exception.errordecoder.VoucherErrorDecoder;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder voucherErrorDecoder() {
        return new VoucherErrorDecoder();
    }

}
