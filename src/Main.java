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
            }scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int rows = dataValues.size() / dataFields.size();
        System.out.println(rows);
        int columns = dataFields.size();
        System.out.println(columns);
        int counter = 0;

        String[][] dataValuesMatrix = new String[rows][columns];

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                dataValuesMatrix[i][j] = dataValues.get(counter);
                counter++;
            }
        }

        System.out.println("Choose the configuration file");
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
        String[][] dataEncryptedValuesMatrix = new String[rows][columns];
        final String secretKey = "1234567891234567";

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                for (int k = 0; k < sameFieldIndexArray.size(); k++){
                    if(sameFieldIndexArray.get(k) == j){
                        dataEncryptedValuesMatrix[i][j] =  AESEncryptor.encrypt(dataValuesMatrix[i][j], secretKey) ;
                        break;
                    } else {
                        dataEncryptedValuesMatrix[i][j] = dataValuesMatrix[i][j];
                    }

                }
            }
        }



//        for(int i = 0; i < rows; i++){
//            for (int j = 0; j < columns; j++){
//                for (int k = 0; k < sameFieldIndexArray.size(); k++){
//                    Integer checkfield = Integer.parseInt(configuredFields.get(k));
//                    if(checkfield == j){
//                        dataEncryptedValuesMatrix[i][j] =  AESEncryptor.encrypt(dataValuesMatrix[i][j], secretKey) ;
//                    } else {
//                        dataEncryptedValuesMatrix[i][j] = dataValuesMatrix[i][j];
//                    }
//                }
//            }
//        }

        //Write the encrypted values to a .txt file
        try (BufferedWriter cypheredFile = new BufferedWriter(new FileWriter("cyphered.txt")))
        {
            cypheredFile.write("ID" + "\t" + "NAME" + "\t" + "SURNAME" + "\t" + "PHONE_NUMBER"+ "\n");
            for (int i = 0; i < rows; i++){
                String encryptedRow;
                for (int j = 0; j < columns; j++) {
                    if (j < columns - 1) {
                        encryptedRow = dataEncryptedValuesMatrix[i][j] + "\t";
                    } else {
                        encryptedRow = dataEncryptedValuesMatrix[i][j] + "\n";
                    }
                    cypheredFile.write(encryptedRow);
                }
            }

//            for (int i = 0; i < rows; i++){
//                String encryptedRow = dataEncryptedValuesMatrix[i][0] + "\t" + dataEncryptedValuesMatrix[i][1] + "\t" + dataEncryptedValuesMatrix[i][2] + "\t" + dataEncryptedValuesMatrix[i][3] + "\n";
//
//                cypheredFile.write(encryptedRow);
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n ============== Plain data ============= ");
        for(int i = 0; i < rows; i++){
            for (int j = 0; j <columns; j++){
                System.out.println("arr[" + i + "][" + j + "] = "+ dataValuesMatrix[i][j]);
            }
        }

        System.out.println("\n ============== Encrypted data ============= ");
        for(int i = 0; i < rows; i++){
            for (int j = 0; j <columns; j++){
                System.out.println("arr[" + i + "][" + j + "] = "+ dataEncryptedValuesMatrix[i][j]);
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