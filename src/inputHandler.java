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
        String input = scanner.nextLine().toLowerCase(); // Menerima input dari pengguna dan mengubahnya menjadi huruf kecil
        input = input.replaceAll("\\s+", " "); // Menghilangkan spasi berlebihan
        String[] words = input.split(" "); // Memecah input menjadi array kata-kata
        StringBuilder capitalizedText = new StringBuilder(); // Inisialisasi variabel capitalizedText
        for (String word : words) { // Melakukan perulangan untuk setiap kata dalam array words
            // Membuat huruf pertama dari setiap kata menjadi huruf besar
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
            capitalizedText.append(capitalizedWord).append(" "); // Menambahkan kata yang sudah diubah ke variabel capitalizedText
        }
        capitalizedText = new StringBuilder(capitalizedText.toString().trim()); // Menghilangkan spasi berlebihan di akhir teks
        return capitalizedText.toString(); // Mengembalikan nilai variabel capitalizedText
    }


    // Format untuk menata teks menjadi rata kanan berdasarkan panjang teks dan panjang maksimum yang diinginkan
    public String formatAutoSpacingLeft (String text, int maxLengthText) {
        if (text.length() < maxLengthText) { // Jika panjang teks kurang dari panjang maksimum yang diinginkan
            int numSpace = maxLengthText - text.length(); // Menghitung jumlah spasi yang diperlukan
            // Melakukan perulangan sebanyak jumlah spasi yang diperlukan
            return // Inisialisasi variabel spaceText dengan nilai teks awal
                    text + " ".repeat(numSpace) // Menambahkan karakter spasi ke variabel spaceText
                    ; // Mengembalikan nilai variabel spaceText
        } else { // Jika panjang teks sudah mencapai atau melebihi panjang maksimum yang diinginkan
            return text; // Mengembalikan nilai teks awal
        }
    }

    // Format untuk menata teks menjadi rata tengah berdasarkan panjang teks dan panjang maksimum yang diinginkan
    public String formatAutoSpacingCenter (String text, int maxLengthText) {
        if (text.length() < maxLengthText) { // Jika panjang teks kurang dari panjang maksimum yang diinginkan
            int numSpace = maxLengthText - text.length(); // Menghitung jumlah spasi yang diperlukan
            StringBuilder spaceText = new StringBuilder(); // Inisialisasi variabel spaceText
            if (numSpace % 2 == 1) { // Jika jumlah spasi ganjil
                numSpace /= 2; // Membagi jumlah spasi dengan 2
                // Melakukan perulangan sebanyak setengah jumlah spasi
                spaceText.append(" ".repeat(numSpace)); // Menambahkan karakter spasi ke variabel spaceText
                spaceText.append(text); // Menambahkan teks ke variabel spaceText
                // Melakukan perulangan sebanyak setengah jumlah spasi ditambah 1
                spaceText.append(" ".repeat(numSpace + 1)); // Menambahkan karakter spasi ke variabel spaceText
            } else { // Jika jumlah spasi genap
                numSpace /= 2; // Membagi jumlah spasi dengan 2
                // Melakukan perulangan sebanyak setengah jumlah spasi
                spaceText.append(" ".repeat(numSpace)); // Menambahkan karakter spasi ke variabel spaceText
                spaceText.append(text); // Menambahkan teks ke variabel spaceText
                // Melakukan perulangan sebanyak setengah jumlah spasi
                spaceText.append(" ".repeat(numSpace)); // Menambahkan karakter spasi ke variabel spaceText
            }

            return spaceText.toString(); // Mengembalikan nilai variabel spaceText
        } else { // Jika panjang teks sudah mencapai atau melebihi panjang maksimum yang diinginkan
            return text; // Mengembalikan nilai teks awal
        }
    }

    // Menutup scanner setelah penggunaan selesai
    public void close () {
        scanner.close();
    }
}
