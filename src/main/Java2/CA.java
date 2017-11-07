/**
 * Created by fqlive on 2017/10/23.
 */
public class CA {
    private String id;
    private String hash_id;
    private String encty_info;
    private String public_key;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash_id() {
        return hash_id;
    }

    public void setHash_id(String hash_id) {
        this.hash_id = hash_id;
    }

    public String getEncty_info() {
        return encty_info;
    }

    public void setEncty_info(String encty_info) {
        this.encty_info = encty_info;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public CA(String id, String hash_id, String encty_info, String public_key) {
        this.id = id;
        this.hash_id = hash_id;
        this.encty_info = encty_info;
        this.public_key = public_key;
    }

    @Override
    public String toString() {
        return "CA{" +
                "id='" + id + '\'' +
                ", hash_id='" + hash_id + '\'' +
                ", encty_info='" + encty_info + '\'' +
                ", public_key='" + public_key + '\'' +
                '}';
    }
}
