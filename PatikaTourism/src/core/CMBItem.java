package core;

public class CMBItem {

    private int key;
    private String value;

    public CMBItem(int key, String value) {
        this.key = key;
        this.value = value;

    }

    @Override
    public String toString() {
        return this.value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
