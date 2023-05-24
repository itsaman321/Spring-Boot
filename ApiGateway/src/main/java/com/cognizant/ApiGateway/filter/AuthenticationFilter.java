package com.cognizant.ApiGateway.filter;

import com.cognizant.ApiGateway.exceptions.MessageException;
import com.cognizant.ApiGateway.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    @Autowired
    private RouteValidator routeValidator ;

//    private WebClient webClient ;
    @Autowired
    private JwtService jwtService ;
    public AuthenticationFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                //header contain auth token or not
                try{
                    if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                        throw new MessageException("Missing Authorization Header..");
                    }
                }catch (MessageException e){
                    throw new MessageException(e.getMessage());
                }
                String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeaders!=null && authHeaders.startsWith("Bearer ")){
                    authHeaders = authHeaders.substring(7);
                }
                try{
                    jwtService.validateToken(authHeaders);
                }catch (WebClientException e){
                    throw new MessageException(e.getMessage());
                }
                catch (MessageException e){
                    throw new MessageException(e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
