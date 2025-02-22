package application;

import com.sun.net.httpserver.HttpServer;
import di.annotation.Bean;
import di.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

@Configuration
public class AppConfiguration {
    public static final int PORT = 8000;
    public static final Executor DEFAULT_EXECUTOR = null;

    @Bean
    public HttpServer httpServer() throws Exception {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.setExecutor(DEFAULT_EXECUTOR);
        return httpServer;
    }
}
