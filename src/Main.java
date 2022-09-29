import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> dataFields = new ArrayList<>();
        ArrayList<String> dataValues = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the data file for encryption");
        String fileLocation = scanner.nextLine();

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(fileLocation)));
            String fields = scanner.nextLine();
            String[] fieldsToSeparate = fields.split("\t");
            for (String field : fieldsToSeparate) {
                dataFields.add(field);
            }

            while(scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] dataValuesString = input.split("\t");
                for(String dataValue : dataValuesString){
                    dataValues.add(dataValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rows = dataValues.size() / dataFields.size();
        int columns = dataFields.size();
        int counter = 0;

        String[][] dataValuesMatrix = new String[columns][rows];

        for(int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                dataValuesMatrix[i][j] = dataValues.get(counter);
                counter++;
            }
        }


        System.out.println("Choose the configuration file");
//        String configureFileLocation = scanner.nextLine();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String configureFileLocation = null;
        try {
            configureFileLocation = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> configuredFields = new ArrayList<>();
        try {
            Scanner configurationReader = new Scanner(new BufferedReader(new FileReader(configureFileLocation)));
            String fieldsToConfigureString = configurationReader.nextLine();
            String[] fieldsToSeparateFromConfigureFile = fieldsToConfigureString.split("\t");
            for (String field : fieldsToSeparateFromConfigureFile) {
                configuredFields.add(field);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> sameFieldIndexArray = new ArrayList<>();
        sameFieldIndexArray = SameFieldIndex(dataFields,configuredFields);
        System.out.println(sameFieldIndexArray);

        //encrypt the data
//        String[][] dataEncryptedValuesMatrix = new String[columns][rows];
        final String secretKey = "1234567891234567";
//        int counter2 = 0;
//        for(int i = 0; i < columns; i++){
//            for (int j = 0; j < rows; j++){
//                if(sameFieldIndexArray.get(counter2) == i){
//                    dataEncryptedValuesMatrix[i][j] =  AESEncryptor.encrypt(dataValuesMatrix[i][j], secretKey) ;
//                } else {
//                    dataEncryptedValuesMatrix[i][j] = dataValuesMatrix[i][j];
//                }
//                counter2++;
//            }
//        }

        String smth = "Vasilis";
        //String enc = AESEncryptor.encrypt(smth,secretKey);
        //System.out.println(enc);

        //Write the encrypted values to a .txt file
        try (BufferedWriter cypheredFile = new BufferedWriter(new FileWriter("cyphered.txt")))
        {
            cypheredFile.write("ID" + "\t" + "NAME" + "\t" + "SURNAME" + "\t" + "PHONE_NUMBER"+ "\n");
            for (int i = 0; i < dataValues.size()/ dataFields.size(); i++){
                //cypheredFile.write(idColumn.get("id") + "\t" + Arrays.toString(nameColumn.get(i)) + "\t" + Arrays.toString(surnameColumn.get(i)) + "\t" + Arrays.toString(phoneNumberColumn.get(i)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < columns; i++){
            for (int j = 0; j <rows; j++){
                System.out.println("arr[" + i + "][" + j + "] = "+ dataValuesMatrix[i][j]);
            }
        }
    }

    public static ArrayList<Integer> SameFieldIndex(ArrayList dataFields, ArrayList configuredFields){
        ArrayList<Integer> sameFieldIndex = new ArrayList<>();
        for(int i = 0; i < dataFields.size(); i++){
            for (int j = 0; j < configuredFields.size(); j++){
                if(dataFields.get(i).equals(configuredFields.get(j))){
                    sameFieldIndex.add(dataFields.indexOf(dataFields.get(i)));
                }
            }
        }
        return sameFieldIndex;
    }

}