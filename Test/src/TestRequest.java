import java.util.List;

public class TestRequest {
    
    public TestHeader header;

    public TestBody body;

    public String test5;

    public List<TestArray> array;

    public TestRequest() {
        super();
    }

    public TestRequest(TestHeader header, TestBody body, String test5, List<TestArray> array) {
        super();
        this.header = header;
        this.body = body;
        this.test5 = test5;
        this.array = array;
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

    public List<TestArray> getArray() {
        return array;
    }

    public void setArray(List<TestArray> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "TestRequest [header=" + header + ", body=" + body + ", test5=" + test5 + ", array=" + array + "]";
    }

}