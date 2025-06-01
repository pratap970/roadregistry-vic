import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private Map<String, Integer> demeritPoints;
    private boolean isSuspended;

    public Person(String personID, String firstName, String lastName, String address, String birthDate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
    }

    // ✅ ADD PERSON METHOD
    public boolean addPerson() {
        try {
            if (!personID.matches("^[2-9][0-9].{2,6}[!@#$%^&*()].*[A-Z]{2}$")) return false;

            String[] addrParts = address.split("\\|");
            if (addrParts.length != 5 || !addrParts[3].equalsIgnoreCase("Victoria")) return false;

            if (!isValidDate(birthDate)) return false;

            BufferedWriter writer = new BufferedWriter(new FileWriter("person_data.txt", true));
            writer.write(personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthDate);
            writer.newLine();
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // ✅ UPDATE PERSONAL DETAILS METHOD
    public boolean updatePersonalDetails(String newPersonID, String newFirstName, String newLastName, String newAddress, String newBirthDate) {
        try {
            if (!newPersonID.matches("^[2-9][0-9].{2,6}[!@#$%^&*()].*[A-Z]{2}$")) return false;
            if (!isValidDate(newBirthDate)) return false;

            File inputFile = new File("person_data.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equals(this.personID)) {
                    int age = calculateAge(this.birthDate);

                    // ✅ Block if birthdate changed AND any other field also changed
                    if (!birthDate.equals(newBirthDate) &&
                        (!newPersonID.equals(this.personID) ||
                         !newFirstName.equals(this.firstName) ||
                         !newLastName.equals(this.lastName) ||
                         !newAddress.equals(this.address))) {
                        return false;
                    }

                    // ✅ Block address change if under 18
                    if (age < 18 && !newAddress.equals(this.address)) return false;

                    // ✅ Block ID change if original ID starts with even number
                    if (Character.getNumericValue(this.personID.charAt(0)) % 2 == 0 &&
                        !newPersonID.equals(this.personID)) return false;

                    // ✅ Write updated line
                    writer.write(newPersonID + "|" + newFirstName + "|" + newLastName + "|" + newAddress + "|" + newBirthDate);
                    updated = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            reader.close();
            writer.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);

            return updated;

        } catch (Exception e) {
            return false;
        }
    }

    // ✅ ADD DEMERIT POINTS METHOD
    public String addDemeritPoints(String date, int points) {
        try {
            if (!isValidDate(date)) return "Failed";
            if (points < 1 || points > 6) return "Failed";

            demeritPoints.put(date, points);

            int totalPoints = 0;
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            for (String d : demeritPoints.keySet()) {
                Date offenseDate = sdf.parse(d);
                long diff = now.getTime() - offenseDate.getTime();
                long days = diff / (1000 * 60 * 60 * 24);

                if (days <= 730) {
                    totalPoints += demeritPoints.get(d);
                }
            }

            int age = calculateAge(this.birthDate);
            if ((age < 21 && totalPoints > 6) || (age >= 21 && totalPoints > 12)) {
                isSuspended = true;
            }

            return "Success";
        } catch (Exception e) {
            return "Failed";
        }
    }

    // ✅ DATE VALIDATION
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // ✅ AGE CALCULATION
    private int calculateAge(String birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dob = sdf.parse(birthDate);
        Calendar birth = Calendar.getInstance();
        birth.setTime(dob);
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }
}
