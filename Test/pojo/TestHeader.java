public class TestHeader {
    
    @JsonProperty("test1_")
    public String test1;

    @JsonProperty("test2")
    public String test2;

    public TestHeader() {
        super();
    }

    public TestHeader(String test1, String test2) {
        super();
        this.test1_ = test1_;
        this.test2 = test2;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    @Override
    public String toString() {
        return "TestHeader [test1_=" + test1_ + ", test2=" + test2 + "]";
    }

}