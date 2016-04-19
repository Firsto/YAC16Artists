package ru.firsto.yac16artists.api;

import junit.framework.Assert;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ru.firsto.yac16artists.api.handler.ApiRequestHandler;
import ru.firsto.yac16artists.api.handler.ArtistListHandler;
import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.request.ArtistListRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;

public class ApiHandlerFactory {
    private static final Map<Class<? extends ApiRequest>, Class<? extends ApiRequestHandler<? extends ApiRequest, ?
            extends ApiResponse>>> handlers = new HashMap<>();

    static {
        register(ArtistListRequest.class, ArtistListHandler.class);
    }

    @SuppressWarnings("unchecked")
    static <R extends ApiRequest, T extends ApiResponse> ApiRequestHandler<R, T> getHandler(R request) {
        Class<? extends ApiRequestHandler> cls = handlers.get(request.getClass());
        Assert.assertNotNull("No handler for such a request " + request.getClass(), cls);
        try {
            Constructor<? extends ApiRequestHandler> constructor = cls.getConstructor(request.getClass());
            return constructor.newInstance(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("couldn't construct request handler");
    }

    public static void register(Class<? extends ApiRequest> request, Class<?
            extends ApiRequestHandler<? extends ApiRequest, ? extends ApiResponse>> handler) {
        handlers.put(request, handler);
    }
}
