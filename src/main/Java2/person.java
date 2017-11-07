/**
 * Created by fqlive on 2017/10/23.
 */
public class person {
    private String name;
    private String id;
    private String address;
    private String password;
    private  String public_key;
    private String secure_key;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getSecure_key() {
        return secure_key;
    }

    public void setSecure_key(String secure_key) {
        this.secure_key = secure_key;
    }

    public person(String name, String id, String address, String password, String public_key, String secure_key) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.password = password;
        this.public_key = public_key;
        this.secure_key = secure_key;
    }

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", public_key='" + public_key + '\'' +
                ", secure_key='" + secure_key + '\'' +
                '}';
    }
}
