public class TestRequest {
    
    @JsonProperty("MW_header")
    public TestMwHeader mwHeader;

    @JsonProperty("MW_HEADER_TEST")
    public TestMwHeaderTest mwHeaderTest;

    @JsonProperty("body")
    public TestBody body;

    @JsonProperty("test5")
    public String test5;

    @JsonProperty("array")
    public List<TestArray> array;

    public TestRequest() {
        super();
    }

    public TestRequest(TestMwHeader mwHeader, TestMwHeaderTest mwHeaderTest, TestBody body, String test5, List<TestArray> array) {
        super();
        this.MW_header = MW_header;
        this.MW_HEADER_TEST = MW_HEADER_TEST;
        this.body = body;
        this.test5 = test5;
        this.array = array;
    }

    public TestMwHeader getMwHeader() {
        return mwHeader;
    }

    public void setMwHeader(TestMwHeader mwHeader) {
        this.mwHeader = mwHeader;
    }

    public TestMwHeaderTest getMwHeaderTest() {
        return mwHeaderTest;
    }

    public void setMwHeaderTest(TestMwHeaderTest mwHeaderTest) {
        this.mwHeaderTest = mwHeaderTest;
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
        return "TestRequest [mwHeader=" + mwHeader + ", mwHeaderTest=" + mwHeaderTest + ", body=" + body + ", test5=" + test5 + ", array=" + array + "]";
    }

}