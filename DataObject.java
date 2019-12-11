public class DataObject {
    private String code;

    public DataObject(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "\"data\": {\n" +
                "\t \"code\":\"" + getCode().replace("\n", "")+ "\"\n" +
                "}";
    }
}
