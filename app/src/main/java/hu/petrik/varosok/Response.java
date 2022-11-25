package hu.petrik.varosok;

public class Response {
    private int responseCode;
    private String content;

    public Response(int response, String content) {
        this.responseCode = response;
        this.content = content;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
