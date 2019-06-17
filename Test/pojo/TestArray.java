public class TestArray {
    
    @JsonProperty("test6")
    public String test6;

    @JsonProperty("test7")
    public String test7;

    public TestArray() {
        super();
    }

    public TestArray(String test6, String test7) {
        super();
        this.test6 = test6;
        this.test7 = test7;
    }

    public String getTest6() {
        return test6;
    }

    public void setTest6(String test6) {
        this.test6 = test6;
    }

    public String getTest7() {
        return test7;
    }

    public void setTest7(String test7) {
        this.test7 = test7;
    }

    @Override
    public String toString() {
        return "TestArray [test6=" + test6 + ", test7=" + test7 + "]";
    }

}