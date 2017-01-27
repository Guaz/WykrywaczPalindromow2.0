package guaz.wykrywaczpalindromow20;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Guaz on 2017-01-22.
 */
public class isPalindromeTest {
    @Test
    public void testTrue(){
        String kajak = "ka ja k";
        assertThat(MainActivity.isPalindrome(kajak), is(true));
    }

    @Test
    public void testFalse(){
        String kajakz = "ka ja kz";
        assertThat(MainActivity.isPalindrome(kajakz), is(false));
    }

}