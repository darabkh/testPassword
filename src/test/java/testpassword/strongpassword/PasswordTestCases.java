package testpassword.strongpassword;

import static org.testng.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PasswordTestCases
{
	final static Logger log = Logger.getLogger(PasswordTestCases.class);
    @DataProvider
    public static Object[][] testPasswordData() {
        return new Object[][] {
            { "TC001", "Old password is null", false, null, "newBie12345678912345678@!@!" },
            { "TC002", "Old password is empty", false, "", "newBie12345678912345678@!@!" },         
            { "TC003", "New password is null", false, "oldPasswordsecure345678@!@!", null },
            { "TC004", "New password is empty", false, "oldPasswordsecure345678@!@!", "" },
            { "TC005", "Old password doesnt match with system", false, "dcdv", "newBie12345678912345678@!@!" },
            { "TC006", "new password contains invalid character", false, "oldPasswordsecure345678@!@!", "()newBie1234password12345678@!@!" },
            { "TC007", "new password size is less than 18", false, "oldPasswordsecure345678@!@!", "newBie145678@!@!"},
            { "TC008", "new password doesnt contain special character", false, "oldPasswordsecure345678@!@!", "newBie123password12345678" },
            { "TC009", "new password doesnt contain uppercase character", false, "oldPasswordsecure345678@!@!", "newbie123password12345678!@#!" },
            { "TC010", "new password doesnt contain lowercase character", false, "oldPasswordsecure345678@!@!", "NEWBIE123PASSWORD12345678!@#!" },
            { "TC011", "new password doesnt contain numeric", false, "oldPasswordsecure345678@!@!", "newbieAGFHVHBHVghbhvahdvgd!@#!" },
            { "TC012", "new password contain more than 4 duplicates characters", false, "oldPasswordsecure345678@!@!", "neeewBiee123password12345678!@#!" },
            { "TC013", "new password contain more than 4 special characters", false, "oldPasswordsecure345678@!@!", "newBie!@#@!8912345678ahdvgd!@#!" },
            { "TC014", "new password contain more than 50% number", false, "oldPasswordsecure345678@!@!", "newBie12345678912345678ahdvgd!@#!" },
            { "TC015", "new password is same as old", false, "oldPasswordsecure345678@!@!", "oldPasswordsecure345678@!@!" },
            { "TC016", "new password contain is SIMILAR to old more than 80%", false, "oldPasswordsecure345678@!@!", "newPasswordsecure345678@!@!" },
            { "TC017", "new password is valid and old password match with system", true, "oldPasswordsecure345678@!@!", "newBie123password12345678@!@!" },
            
        };
    }
    
    @Test(dataProvider = "testPasswordData")
    public void testApp(String testCaseId, String testDescription, boolean expectedResult, String oldPassword, String newPassword)
    {
    	BasicConfigurator.configure();
    	log.info("Executing testId: " + testCaseId);
    	boolean actualResult = Password.changePassword(oldPassword, newPassword);
        assertEquals( actualResult, expectedResult, testDescription );
    }
}
