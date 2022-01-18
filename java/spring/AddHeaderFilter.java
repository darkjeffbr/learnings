import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AddHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        var start = LocalDateTime.now();

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response) {
            @Override
            public void setStatus(int sc) {
                super.setStatus(sc);

                final HttpHeaders headers = ControllerHelper.buildHeaders(start); // Generate custom headers here

                headers.forEach((header, value) ->
                    addHeader(header, value.get(0))
                );
            }
        };

        filterChain.doFilter(request, wrapper);
    }
}