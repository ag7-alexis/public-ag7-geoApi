package ag7.dev.ag7geoapi.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ag7.dev.ag7geoapi.db.UserRepository;
import ag7.dev.ag7geoapi.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Class to filter request
 */

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    /**
     * filter request
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // get token in header
        String header = request.getHeader(JwtProperties.HEADER);

        if (header == null || !header.startsWith(JwtProperties.PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // if token look great we try to authenticate him
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    /**
     * Method to authenticate a token
     *
     * @param request
     * @return Authentication
     */
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER).replace(JwtProperties.PREFIX, "");

        if (token != null) {
            // get username contains in the token
            String username = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token).getSubject();

            // try to find user in db
            if (username != null) {
                User user = userRepository.findByUsername(username);
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                        principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
