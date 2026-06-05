package com.example.gametournamentplanner.service;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void ShouldContinueWhenAuthorizationHeaderMissing()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        filter.doFilterInternal(
                request,
                response,
                filterChain);

        verify(filterChain)
                .doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());
    }

    @Test
    void ShouldContinueWhenTokenIsInvalid()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer invalidToken");

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(jwtService.isTokenValid("invalidToken"))
                .thenReturn(false);

        filter.doFilterInternal(
                request,
                response,
                filterChain);

        verify(filterChain)
                .doFilter(request, response);

        assertNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());
    }

    @Test
    void ShouldAuthenticateWhenTokenIsValid()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer validToken");

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(jwtService.isTokenValid("validToken"))
                .thenReturn(true);

        when(jwtService.extractEmail("validToken"))
                .thenReturn("test@gmail.com");

        filter.doFilterInternal(
                request,
                response,
                filterChain);

        verify(filterChain)
                .doFilter(request, response);

        assertNotNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication());

        assertEquals(
                "test@gmail.com",
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal());
    }
}