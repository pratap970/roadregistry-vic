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
}