package ru.firsto.yac16artists.api.net;

import android.util.Log;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.MediaType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.RestAdapter;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import ru.firsto.yac16artists.BuildConfig;
import ru.firsto.yac16artists.api.response.RestResponse;

public class NetClient {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    public ServerMethods serverMethods;

    public NetClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.ENDPOINT)
                .setConverter(new GsonToResponseConverter())
                .setLog(new HttpLog("YAC http"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        serverMethods = restAdapter.create(ServerMethods.class);
    }

    public class HttpLog implements RestAdapter.Log {
        private static final int LOG_CHUNK_SIZE = 2000;

        private final String tag;

        public HttpLog(String tag) {
            this.tag = tag;
        }

        @Override
        public final void log(String message) {
//            for (int i = 0, len = message.length(); i < len; i += LOG_CHUNK_SIZE) {
                int end = Math.min(message.length(), LOG_CHUNK_SIZE);
                logChunk(message.substring(0, end));
//                break;
//            }
        }

        /**
         * Called one or more times for each call to {@link #log(String)}. The length of {@code chunk}
         * will be no more than LOG_CHUNK_SIZE characters to support Android's {@link Log} class.
         */
        public void logChunk(String chunk) {
            Log.d(getTag(), chunk);
        }

        public String getTag() {
            return tag;
        }
    }


    public static class GsonToResponseConverter implements Converter {
        @Override
        public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
            RestResponse response = new RestResponse();
            try {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(typedInput.in()));
                response.body = new JsonParser().parse(jsonReader).getAsJsonArray();
            } catch (IOException ignored) {/*NOP*/ }
            return response;
        }

        @Override
        public TypedOutput toBody(Object o) {
            return null;
        }
    }
}
