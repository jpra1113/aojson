public class TestRequest {
    
    @JsonProperty("MW_header")
    public TestMWheader mWheader;

    @JsonProperty("body")
    public TestBody body;

    @JsonProperty("test5")
    public String test5;

    @JsonProperty("array")
    public List<TestArray> array;

    public TestRequest() {
        super();
    }

    public TestRequest(TestMWheader mWheader, TestBody body, String test5, List<TestArray> array) {
        super();
        this.MW_header = MW_header;
        this.body = body;
        this.test5 = test5;
        this.array = array;
    }

    public TestMWheader getMWheader() {
        return mWheader;
    }

    public void setMWheader(TestMWheader mWheader) {
        this.mWheader = mWheader;
    }

    public TestBody getBody() {
        return body;
    }

    public void setBody(TestBody body) {
        this.body = body;
    }

    public String getTest5() {
        return test5;
    }

    public void setTest5(String test5) {
        this.test5 = test5;
    }

    public List<TestArray> getArray() {
        return array;
    }

    public void setArray(List<TestArray> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "TestRequest [mWheader=" + mWheader + ", body=" + body + ", test5=" + test5 + ", array=" + array + "]";
    }

}