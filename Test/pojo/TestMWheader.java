public class TestMWheader {
    
    @JsonProperty("test1_")
    public String test1;

    @JsonProperty("test2")
    public String test2;

    public TestMWheader() {
        super();
    }

    public TestMWheader(String test1, String test2) {
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
        return "TestMWheader [test1=" + test1 + ", test2=" + test2 + "]";
    }

}