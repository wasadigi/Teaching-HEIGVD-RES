package ch.heigvd.res.ldap;

import ch.heigvd.res.ldap.Person.Sex;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This program is used to generate a random file of user accounts.
 * 
 * @author oliechti
 */
public class DataGenerator {

    // How many user accounts do we want to generate?
    private static int numberOfRecords = 10000;

    // Where do we start with the person id number?
    private int uniqueIdCounter = 100000;
    
    // Dataset used for generating the user accounts
    private static String[] firstNamesFemale = {"Anne", "Amélie", "Aurore", "Bernadette", "Brigitte", "Carole", "Carine", "Christine", "Danièle", "Dorothé", "Emilie", "Evelyne", "Emma", "Fabienne", "Faustine", "Georgette", "Henriette", "Irène", "Julie"};
    private static String[] firstNamesMale = {"Albert", "Bernard", "Bryan", "Cédric", "Charles", "Didier", "Dylan", "Emil", "Edgar", "Fabrice", "Frédéric", "Fabien", "Gilles", "Guillaume", "Gontran", "Henri", "Julien", "Jimmy", "Luc", "Lucien", "Marc"};
    private static String[] lastNames = {"Bauer", "Belmondo", "Dupont", "Dupond", "Durant", "Delon", "De Villiers", "De Decker", "Jones", "Marchand", "Clément", "Simpson", "Smith"};
    private static String[] departments = {"TIC", "TIN", "COMEM", "DEPG", "EC", "HEG"};
    private static String[] functions = {"Student", "Professor", "Assistant", "Admin"};

    private Person generateRandomPerson() {
        Sex sex;
        String firstName;
        String lastName;
        String department;
        String function;
        String phone;
        String email;
        
        int rnd = (int) (Math.random() * 2);
        if (rnd == 0) {
            sex = Sex.MALE;
            rnd = (int) (Math.random() * firstNamesMale.length);
            firstName = firstNamesMale[rnd];
        } else {
            sex = Sex.FEMALE;
            rnd = (int) (Math.random() * firstNamesFemale.length);
            firstName = firstNamesFemale[rnd];
        }

        rnd = (int) (Math.random() * lastNames.length);
        lastName = lastNames[rnd];

        rnd = (int) (Math.random() * departments.length);
        department = departments[rnd];

        rnd = (int) (Math.random() * functions.length);
        function = functions[rnd];

        int rnd1 = (int) (100 + Math.random() * 800);
        int rnd2 = (int) (100 + Math.random() * 800);
        phone = "(024) 777 " + rnd1 + " " + rnd2;

        email = firstName + "." + lastName + "@" + "heig-vd.ch";
        email = email.toLowerCase();

        uniqueIdCounter++;

        return new Person("EID_" + uniqueIdCounter, firstName, lastName, sex, department, function, phone, email);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter bw = null;
        try {
            //bw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("users.csv"), "ISO-8859-1"));
            bw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("users.csv")));
            
            DataGenerator generator = new DataGenerator();
            for (int i = 0; i < numberOfRecords; i++) {
                Person p = generator.generateRandomPerson();
                System.out.println(p);
                bw.println(p);
            }            
            
        } catch (IOException ex) {
            Logger.getLogger(DataGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            bw.close();
        }
    }
}
