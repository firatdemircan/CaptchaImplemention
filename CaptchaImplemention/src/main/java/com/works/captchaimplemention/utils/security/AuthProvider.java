package com.works.captchaimplemention.utils.security;

import com.google.common.collect.Lists;
import com.works.captchaimplemention.model.Usertmo;
import com.works.captchaimplemention.model.dto.LoggedUserModel;
import com.works.captchaimplemention.repostory.UserRepostories;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserRepostories userRepostories;
    private final HttpServletResponse response;

    @Transactional
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        //firma girişimi tmo girişimi... burdaki temel fark firmalar tc veya vergino ile giriş yapacak
        LoggedUserModel loggedUserModel;

            Usertmo usertmo;
                usertmo = userRepostories.findByUserName(username).orElseThrow(() -> new BadCredentialsException("bad"));

            if (!passwordEncoder.matches(password, usertmo.getPassword()))
                throw new BadCredentialsException("bad");
            loggedUserModel = new LoggedUserModel(username);

            return new UsernamePasswordAuthenticationToken(loggedUserModel, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
