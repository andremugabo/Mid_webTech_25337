package util;

import java.util.Random;

public class ISBNGenerator {
    private static final Random random = new Random();

    public static String generateISBN() {
        StringBuilder isbn = new StringBuilder();

        // Generate first 12 digits randomly
        for (int i = 0; i < 12; i++) {
            isbn.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        // Calculate the 13th digit (check digit)
        int checkDigit = calculateCheckDigit(isbn.toString());
        isbn.append(checkDigit);

        return isbn.toString();
    }

    private static int calculateCheckDigit(String isbnWithoutCheckDigit) {
        int sum = 0;
        for (int i = 0; i < isbnWithoutCheckDigit.length(); i++) {
            int digit = Character.getNumericValue(isbnWithoutCheckDigit.charAt(i));
            if (i % 2 == 0) { // Even index (0-based)
                sum += digit;
            } else { // Odd index
                sum += digit * 3;
            }
        }
        // The check digit is the number that makes the sum a multiple of 10
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }

//    public static void main(String[] args) {
//        String isbn = generateISBN();
//        System.out.println("Generated ISBN: " + isbn);
//    }
}
