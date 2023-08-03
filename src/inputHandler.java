import java.util.Scanner;

public class inputHandler {
    private final Scanner scanner = new Scanner(System.in);

    // Menampilkan pesan error dalam teks merah (untuk digunakan pada terminal yang mendukung ANSI escape code)
    public void errorMessage(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m\n");
    }

    // Mengecek apakah input adalah bilangan bulat (digit) dan mengembalikan nilai bilangan bulat
    public int getIntegerInputWithDigitValidation (String message) {
        System.out.print(message);
        String input = scanner.nextLine();
        while (!input.matches("\\d+")) {
            errorMessage("Maaf, harap masukkan input berupa bilangan bulat");
            System.out.print(message);
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }

    // Menerima input teks dari pengguna dan mengembalikan teks tersebut
    public String getUserInputText () {
        return scanner.nextLine();
    }

    // Menutup scanner setelah penggunaan selesai
    public void close () {
        scanner.close();
    }
}
