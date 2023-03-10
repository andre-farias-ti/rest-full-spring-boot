package br.com.andre.services;

import br.com.andre.repository.UserRepository;
import br.com.andre.sercurity.jwt.JwtTokenProvider;
import br.com.andre.dto.AccountCredentialsDto;
import br.com.andre.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsDto data) {
        try{
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = userRepository.findByUsername(username);

            var tokenResponse = new TokenDto();

            if(user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            }else{
                throw new UsernameNotFoundException("User " + username + "not found!");
            }

            return ResponseEntity.ok(tokenResponse);
        }catch (Exception e){
            throw new BadCredentialsException("invalid username/password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);

        var tokenResponse = new TokenDto();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }

}
