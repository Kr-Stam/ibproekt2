package com.example.ibproekt2.email;
import com.example.ibproekt2.entity.User;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

@ToString
public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;


    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User user = (User) context; // we pass the customer informati
        put("firstName", user.get_username());
        setTemplateLocation("email-verification");
        setSubject("Complete your registration");
        setFrom("no-reply@javadevjournal.com");
        setTo(user.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}