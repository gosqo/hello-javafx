package org.gosqo.hellojavafx;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.core.RequestContext;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(config, request, response);

        String remoteAddress = ((RequestContext) request).getRemoteAddr();

        config.getUserProperties().put("remoteAddress", remoteAddress);
    }
}
