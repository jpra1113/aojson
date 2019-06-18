public class TestMwHeaderTest {
    
    @JsonProperty("test1_TEST")
    public String test1Test;

    @JsonProperty("test2")
    public String test2;

    public TestMwHeaderTest() {
        super();
    }

    public TestMwHeaderTest(String test1Test, String test2) {
        super();
        this.test1_TEST = test1_TEST;
        this.test2 = test2;
    }

    public String getTest1Test() {
        return test1Test;
    }

    public void setTest1Test(String test1Test) {
        this.test1Test = test1Test;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    @Override
    public String toString() {
        return "TestMwHeaderTest [test1Test=" + test1Test + ", test2=" + test2 + "]";
    }

}