package xyz.mynt.parceldeliverycost.exception.errordecoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import xyz.mynt.parceldeliverycost.constant.ErrorMessages;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;

public class VoucherErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException(ErrorMessages.VOUCHER_CODE_NOT_VALID);
            default:
                return new Exception(ErrorMessages.VOUCHER_SERVICE_CONNECTION_ERROR);
        }
    }

}
