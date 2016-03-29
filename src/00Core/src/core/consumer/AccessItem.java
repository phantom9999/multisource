package core.consumer;

/**
 * @author phantom
 */
public class AccessItem extends LogItem{

    // 时间, ip, url, 状态码, referer, length, ua
    // %h  %t  %r  %>s  %b  %{Referer}i  %{User-Agent}i  %T
    // Remote host
    // Time the request was received
    // First line of request
    // Status.
    // Size of response in bytes
    // referer
    // userAgent
    // The time taken to serve the request

    public String remoteHost;
    public String datetime;
    public String url;
    public String status;
    public String length;
    public String referer;
    public String userAgent;
    public String timeTaken;

    public AccessItem() {
        remoteHost = "-";
        datetime = "-";
        url = "-";
        status = "-";
        length = "-";
        referer = "-";
        userAgent = "-";
        timeTaken = "-";
    }


    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
}
