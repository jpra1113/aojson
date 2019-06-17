public class TestBody {
    
    public String test3;

    public String test4;

    public TestBody() {
        super();
    }

    public TestBody(String test3, String test4) {
        super();
        this.test3 = test3;
        this.test4 = test4;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

    @Override
    public String toString() {
        return "TestBody [test3=" + test3 + ", test4=" + test4 + "]";
    }

}