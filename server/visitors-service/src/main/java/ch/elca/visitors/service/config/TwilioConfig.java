package ch.elca.visitors.service.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@Setter
@Getter
public class TwilioConfig {
    private String accountSid;
    private String authToken;
    private String trialNumber;
}
