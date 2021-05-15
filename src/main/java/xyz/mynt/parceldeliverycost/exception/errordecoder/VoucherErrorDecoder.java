package xyz.mynt.parceldeliverycost.exception.errordecoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;

public class VoucherErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException("Invalid voucher code.");
            default:
                return new Exception("Error connecting Voucher Service.");
        }
    }

}
