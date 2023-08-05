import java.util.Scanner;

public class inputHandler {
    private final Scanner scanner = new Scanner(System.in);

    // Menampilkan pesan error dalam teks merah (untuk digunakan pada terminal yang mendukung ANSI escape code)
    public void errorMessage(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m\n");
    }

    // Fungsi ini memeriksa apakah input merupakan bilangan bulat (digit) dan mengembalikan nilai bilangan bulat tersebut.
    public int getIntegerInputWithDigitValidation(String message) {
        System.out.print(message); // Menampilkan pesan kepada pengguna dan mendapatkan input
        String input = scanner.nextLine();

        // Melakukan validasi untuk memastikan input hanya berisi digit
        while (!input.matches("\\d+")) {
            // Jika input tidak valid, tampilkan pesan kesalahan dan minta input lagi
            errorMessage("Maaf, harap masukkan input berupa bilangan bulat");
            System.out.print(message);
            input = scanner.nextLine();
        }
        // Mengubah input menjadi bilangan bulat dan mengembalikannya
        return Integer.parseInt(input);
    }

    // Menerima input teks dari pengguna dan mengembalikan teks tersebut
    public String getUserInputText() {
        String input = scanner.nextLine().toLowerCase(); // Mendapatkan input dari pengguna dan mengubah semua huruf menjadi huruf kecil
        input = input.replaceAll("\\s+", " "); // Menghapus spasi berlebih
        String[] words = input.split(" "); // Memecah teks menjadi kata-kata
        StringBuilder capitalizedText = new StringBuilder();

        // Mengubah huruf pertama setiap kata menjadi huruf kapital
        for (String word : words) {
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            capitalizedText.append(capitalizedWord).append(" ");
        }

        // Menghapus spasi di akhir teks
        capitalizedText = new StringBuilder(capitalizedText.toString().trim());
        return capitalizedText.toString();
    }

    // Fungsi ini menerima masukan teks dari pengguna, mengembalikan teks tersebut, dan menampilkan isi pesan sesuai dengan parameter yang ditentukan.
    public String getUserInputTextWithMessage (String message) {
        System.out.print(message);
        return getUserInputText();
    }

    // Format untuk menata teks menjadi rata kanan berdasarkan panjang teks dan panjang maksimum yang diinginkan
    public String formatAutoSpacingLeft(String text, int maxLengthText) {
        if (text.length() < maxLengthText) {
            // Jika panjang teks lebih pendek dari panjang maksimum, tambahkan spasi di sebelah kanan
            int numSpace = maxLengthText - text.length();
            return text + " ".repeat(numSpace);
        } else {
            // Jika panjang teks sudah sama atau lebih panjang dari panjang maksimum, kembalikan teks asli
            return text;
        }
    }

    // Format untuk menata teks menjadi rata tengah berdasarkan panjang teks dan panjang maksimum yang diinginkan
    public String formatAutoSpacingCenter(String text, int maxLengthText) {
        if (text.length() < maxLengthText) {
            // Jika panjang teks lebih pendek dari panjang maksimum, tambahkan spasi di sebelah kiri dan kanan
            int numSpace = maxLengthText - text.length();
            StringBuilder spaceText = new StringBuilder();

            if (numSpace % 2 == 1) {
                // Jika jumlah spasi ganjil, tambahkan spasi tambahan di sebelah kanan
                numSpace /= 2;
                spaceText.append(" ".repeat(numSpace));
                spaceText.append(text);
                spaceText.append(" ".repeat(numSpace + 1));
            } else {
                // Jika jumlah spasi genap, tambahkan jumlah spasi yang sama di sebelah kiri dan kanan
                numSpace /= 2;
                spaceText.append(" ".repeat(numSpace));
                spaceText.append(text);
                spaceText.append(" ".repeat(numSpace));
            }
            return spaceText.toString();
        } else {
            return text; // Jika panjang teks sudah sama atau lebih panjang dari panjang maksimum, kembalikan teks asli
        }
    }

    // Menutup scanner setelah penggunaan selesai
    public void close () {
        scanner.close();
    }
}
