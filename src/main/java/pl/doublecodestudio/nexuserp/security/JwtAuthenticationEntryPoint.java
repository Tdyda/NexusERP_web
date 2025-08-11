package pl.doublecodestudio.nexuserp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pl.doublecodestudio.nexuserp.exception.ErrorResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    private List<String> knownPaths;

    @PostConstruct
    public void init() {
        knownPaths = requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
                .flatMap(info -> {
                    // Obsługa starego sposobu (Spring Boot 2.x i część 3.x)
                    if (info.getPatternsCondition() != null) {
                        return info.getPatternsCondition().getPatterns().stream();
                    }
                    // Obsługa nowego sposobu (Spring Boot 3.x+)
                    if (info.getPathPatternsCondition() != null) {
                        return info.getPathPatternsCondition().getPatternValues().stream();
                    }
                    return Stream.empty();
                })
                .toList();
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        if (isUnknownPath(request)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(
                    Map.of(
                            "timestamp", Instant.now(),
                            "status", 404,
                            "error", "Not Found",
                            "path", request.getRequestURI(),
                            "errors", Map.of("error", "No endpoint matches this path")
                    )
            ));
            return;
        }

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(401)
                .error("Unauthorized")
                .path(request.getRequestURI())
                .errors(Map.of("error", authException.getMessage()))
                .build();

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(error));
    }

    private boolean isUnknownPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        return knownPaths.stream().noneMatch(path::equals);
    }
}

