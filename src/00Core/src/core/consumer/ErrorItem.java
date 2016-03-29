package core.consumer;

/**
 * @author phantom
 */
public class ErrorItem {

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getMod() {
        return Mod;
    }

    public void setMod(String mod) {
        Mod = mod;
    }

    public String Time;
    public String Mod;
    public String Rank;
    public String Pid;
    public String Type;
    public String Message;
}
