package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.service.config.TwilioConfig;
import ch.elca.visitors.service.service.TwilioSmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioSmsServiceImpl implements TwilioSmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsServiceImpl.class);
    private final static String MU_PREFIX_CODE = "+230";
    private final TwilioConfig twilioConfig;

    @Override
    public void sendSms(Integer phoneNumber, String message) {

        PhoneNumber to = new PhoneNumber(MU_PREFIX_CODE + phoneNumber.toString());
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

        var creator = Message.creator(to, from, message);

        // Send the SMS
        creator.create();
        LOGGER.info("SMS sent to " + phoneNumber);
    }

}
