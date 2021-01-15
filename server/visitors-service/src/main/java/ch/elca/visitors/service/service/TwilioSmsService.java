package ch.elca.visitors.service.service;

public interface TwilioSmsService {

    void sendSms(Integer phoneNumber, String message);

}
