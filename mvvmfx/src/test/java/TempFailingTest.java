import org.junit.Test;

import static org.assertj.core.api.Assertions.fail;

public class TempFailingTest {


    @Test
    public void test() {
        fail("This test is failing to reproduce a misconfiguration of our travis ci");
    }

}
