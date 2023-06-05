package com.losung360.assignment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Collection;
import java.util.InputMismatchException;

@Component
public class Filter extends OncePerRequestFilter {

    private static final String SECERT = "MY_SECRET_KEY";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        return (new AntPathMatcher().match("/v3/**", request.getServletPath()) ||
                new AntPathMatcher().match("/user/**", request.getServletPath())
        );
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
        String jwt = request.getHeader("Authorization");
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECERT))
                .parseClaimsJws(jwt).getBody();

        String id = claims.getId();
        if(!id.equalsIgnoreCase("1234"))
            throw new InputMismatchException("Wrong Auth");

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return id;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
        catch (Exception e){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
        }
        filterChain.doFilter(request, response);

    }
}
