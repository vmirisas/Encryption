public class Encryption {
    public static void main(String[] args) {
        final String secretKey = "1234567891234567";

        String originalString = "Vasilis";
        String encryptedString = AESEncryptor.encrypt(originalString, secretKey) ;
        String decryptedString = AESEncryptor.decrypt(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
