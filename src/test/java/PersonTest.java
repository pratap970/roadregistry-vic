import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testAddPersonValid() {
        Person p = new Person("56!a$%gAB", "John", "Doe", "12|King St|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
    }

    @Test
    public void testAddPersonInvalidID() {
        Person p = new Person("12abcdXY", "Amy", "Lee", "10|Main|Melbourne|Victoria|Australia", "12-01-1999");
        assertFalse(p.addPerson());
    }

    @Test
    public void testAddPersonInvalidAddressFormat() {
        Person p = new Person("67@#a%gXY", "Sara", "Smith", "High Street|Melbourne|Victoria", "10-10-1998");
        assertFalse(p.addPerson());
    }

    @Test
    public void testAddPersonInvalidState() {
        Person p = new Person("78$@a%gYZ", "Liam", "Wong", "15|Lane|Sydney|NSW|Australia", "05-07-1995");
        assertFalse(p.addPerson());
    }

    @Test
    public void testAddPersonInvalidBirthdateFormat() {
        Person p = new Person("98$@g%XY", "Ella", "Brown", "33|Hill St|Melbourne|Victoria|Australia", "1999-01-01");
        assertFalse(p.addPerson());
    }

    @Test
    public void testUpdateDetailsValid() {
        Person p = new Person("56!a$%gAB", "John", "Doe", "12|King St|Melbourne|Victoria|Australia", "15-11-1990");
        p.addPerson();
        assertTrue(p.updatePersonalDetails("56!a$%gAB", "Johnny", "Dough", "12|King St|Melbourne|Victoria|Australia", "15-11-1990"));
    }

    @Test
    public void testUpdateInvalidBirthdateChangeWithOtherFields() {
        Person p = new Person("67@#a%gXY", "Amy", "Lee", "10|Main|Melbourne|Victoria|Australia", "12-01-2001");
        p.addPerson();
        assertFalse(p.updatePersonalDetails("67@#a%gXY", "Anna", "Lee", "10|Main|Melbourne|Victoria|Australia", "13-02-2002"));
    }

    @Test
    public void testUpdateIDChangeWhenFirstDigitIsEven() {
        Person p = new Person("24@a$%fYZ", "Lara", "Smith", "25|Main St|Melbourne|Victoria|Australia", "20-11-1995");
        p.addPerson();
        assertFalse(p.updatePersonalDetails("88!a#%gXY", "Lara", "Smith", "25|Main St|Melbourne|Victoria|Australia", "20-11-1995"));
    }

    @Test
    public void testUpdateAddressWhenUnder18() {
        Person p = new Person("39#@d$gPQ", "Tom", "Hanks", "8|Oak St|Melbourne|Victoria|Australia", "10-10-2010");
        p.addPerson();
        assertFalse(p.updatePersonalDetails("39#@d$gPQ", "Tom", "Hanks", "99|Palm Rd|Melbourne|Victoria|Australia", "10-10-2010"));
    }

    @Test
    public void testUpdateInvalidNewPersonID() {
        Person p = new Person("75@#x$gZX", "Nina", "Tay", "45|Hill Rd|Melbourne|Victoria|Australia", "25-12-1997");
        p.addPerson();
        assertFalse(p.updatePersonalDetails("invalidID", "Nina", "Tay", "45|Hill Rd|Melbourne|Victoria|Australia", "25-12-1997"));
    }

    @Test
    public void testAddValidDemeritPoints() {
        Person p = new Person("56!a$%gAB", "John", "Doe", "12|King St|Melbourne|Victoria|Australia", "15-11-1990");
        assertEquals("Success", p.addDemeritPoints("10-01-2024", 3));
    }

    @Test
    public void testAddInvalidDateFormat() {
        Person p = new Person("67@#a%gXY", "Amy", "Lee", "10|Main|Melbourne|Victoria|Australia", "12-01-1999");
        assertEquals("Failed", p.addDemeritPoints("2024-01-10", 2));
    }

    @Test
    public void testAddInvalidPointsRange() {
        Person p = new Person("67@#a%gXY", "Amy", "Lee", "10|Main|Melbourne|Victoria|Australia", "12-01-1999");
        assertEquals("Failed", p.addDemeritPoints("10-01-2024", 9));
    }

    @Test
    public void testSuspensionForUnder21() {
        Person p = new Person("39#@d$gPQ", "Tom", "Hanks", "8|Oak St|Melbourne|Victoria|Australia", "10-10-2007");
        p.addDemeritPoints("01-01-2024", 3);
        p.addDemeritPoints("02-01-2024", 4);
        assertEquals("Success", p.addDemeritPoints("03-01-2024", 2));
    }

    @Test
    public void testSuspensionForAbove21() {
        Person p = new Person("88!a#%gXY", "Sam", "Chen", "20|Hill Rd|Melbourne|Victoria|Australia", "15-11-1990");
        p.addDemeritPoints("01-01-2023", 5);
        p.addDemeritPoints("01-01-2024", 6);
        assertEquals("Success", p.addDemeritPoints("15-05-2024", 3));
    }
}
