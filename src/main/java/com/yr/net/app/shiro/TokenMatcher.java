package com.yr.net.app.shiro;

import com.yr.net.app.base.dto.RestResult;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenMatcher implements CredentialsMatcher {
    @Autowired
    private AuthDataSource authDataSource;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token instanceof TokenAuthenticationToken) {
            TokenAuthenticationToken tt = (TokenAuthenticationToken)token;
            RestResult.RestCode restCode = authDataSource.checkPcSession(tt.getToken());
            if (restCode == RestResult.RestCode.SUCCESS) {
                return true;
            }
        }
        return false;
    }
}
