package application;

import com.sun.net.httpserver.HttpHandler;

public interface RestHandler extends HttpHandler {
    String getPath();

    // TODO (jdb): method, return type(s) etc.
}
