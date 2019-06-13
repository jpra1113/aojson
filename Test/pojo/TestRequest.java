public class TestRequest {
    
    public TestHeader header;

    public TestBody body;

    public String test5;

    public TestRequest(TestHeader header, TestBody body, String test5) {
        super();
        this.header = header;
        this.body = body;
        this.test5 = test5;
    }

    public TestHeader getHeader() {
        return header;
    }

    public void setHeader(TestHeader header) {
        this.header = header;
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

    @Override
    public String toString() {
        return "TestRequest [header=" + header + ", body=" + body + ", test5=" + test5 + "]";
    }

}