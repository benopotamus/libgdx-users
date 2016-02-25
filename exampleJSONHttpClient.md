# An example JSON http client #

Here is an example JSON client for a web-app / service which handles JSON data in http body and returns JSON as response. I hope someone find it useful.

# Introduction #

The input to the client is a POJO for the data to be sent, an uri on the server to send to, a callback which tells us what to do with the response object and exceptions, and which class that should be created from the response json. A use example is at the bottom.

# Details #

First the client, for sending the requests. I just have a post method now as an example, just add more if needed.

```
public class JsonClient {

    private String baseURL = "https://base.url.com";

    //Singleton pattern ------
    private static JsonClient instance;
    private JsonClient() {
    }
    public static synchronized JsonClient getInstance() {
        if(instance == null) {
            instance = new JsonClient();
        }
        return instance;
    }
    //------------------------


    public <T> void sendPost(Object requestObject, String uri, final ResponseCallback<T> callback, final Class<T> clazz) {
        sendRequest(requestObject, uri, POST, callback, clazz);
    }
    private <T> void sendRequest(Object requestObject, String uri, String method, final ResponseCallback<T> callback, final Class<T> clazz) {

        String requestJson = JsonUtil.getInstance().toJson(requestObject);

        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = getURL(uri);
        request.setUrl(url);

        request.setContent(requestJson);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");

        //If you want basic authentication, add this header
        String authHeader = "Basic " + Base64Coder.encodeString("user:pass");
        request.setHeader("Authorization", authHeader);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if(statusCode != HttpStatus.SC_OK) {
                    callback.onFail(new JsonClientException(url,"Received http status: " + statusCode));
                    return;
                }

                String responseJson = httpResponse.getResultAsString();
                try {
                    T responseObject = JsonUtil.getInstance().fromJson(clazz, responseJson);
                    callback.onResponse(responseObject);
                }
                catch(Exception exception) {
                    callback.onFail(new JsonClientException(url, "Exception parsing response as JSON.", exception));
                }
            }

            public void failed(Throwable t) {
                callback.onFail(new JsonClientException(url, t));
            }
        });

    }

    private String getURL(String uri) {
        return baseURL + uri;
    }

}


```


Then the response callback:
```
public interface ResponseCallback<T> {

    void onResponse(T returnObject);
    void onFail(JsonClientException t);

}
```


Then an exception which wraps other exceptions on request etc:
```
public class JsonClientException extends Exception {

    private Throwable originalException;

    public JsonClientException(String url, String msg) {
        super("Exception when requesting URL " + url + "\nMessage: " + msg);
    }

    public JsonClientException(String url, Throwable originalException) {
        super("Exception when requesting URL " + url + "\nOriginal message: " + originalException.getMessage());
        this.originalException = originalException;
    }

    public JsonClientException(String url, String msg, Throwable originalException) {
        super("Exception when requesting URL " + url + "\nMessage: " + msg + "\nOriginal message: " + originalException.getMessage());
        this.originalException = originalException;
    }

    public Throwable getOriginalException() {
        return originalException;
    }

}
```

And then a JSON util class which wraps an instace of com.badlogic.gdx.utils.Json:
```
public class JsonUtil {

    private final Json json;

    //Singleton pattern ------
    private static JsonUtil instance;
    private JsonUtil() {
        json = new Json(JsonWriter.OutputType.json);
    }
    public static synchronized JsonUtil getInstance() {
        if(instance == null) {
            instance = new JsonUtil();
        }
        return instance;
    }
    //------------------------

    public <T> T readFromJsonFile(Class<T> clazz, String filename) {
        FileHandle file = Gdx.files.local(filename);
        return json.fromJson(clazz, file);
    }

    public void writeToJsonFile(Object obj, String filename) {
        FileHandle file = Gdx.files.local(filename);
        json.toJson(obj, file);
    }

    public String toJson(Object obj) {
        return json.toJson(obj);
    }

    public <T> T fromJson(Class<T> clazz, String jsonString) {
        return json.fromJson(clazz, jsonString);
    }
}
```



Example use:
```
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setUsername(username);
        signUpRequestDTO.setProfileImageBase64(imageBase64);

        ResponseCallback<SignUpResponseDTO> callback = new ResponseCallback<SignUpResponseDTO>() {
            public void onResponse(SignUpResponseDTO returnObject) {
                Log.debug("Json request OK.");
            }
            public void onFail(JsonClientException exception) {
                Log.debug("Json request failed: " + exception.getMessage());
            }
        };
        JsonClient.getInstance().sendPost(signUpRequestDTO, "/app/user/signUp.json", callback, SignUpResponseDTO.class);

```