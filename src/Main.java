public class Main {
    private static final StokGudang stokGudang = new StokGudang();
    private static final inputHandler inputHandler = new inputHandler();

    public static void main(String[] args) {
        while (true){
            try {
                // Menampilkan menu dan mendapatkan pilihan dari pengguna
                int choice = inputHandler.getIntegerInputWithDigitValidation(tampilMenu());
                System.out.println("===========================================================================================\n");
                switch (choice) {
                    case 0 -> throw new Exception(); // Jika pengguna memilih 0, lempar Exception untuk keluar dari program
                    case 1 -> stokGudang.displayAndAddCategories(); // Memanggil metode untuk menambahkan kategori ke dalam gudang
                    case 2 -> {
                        // Menampilkan daftar kategori dan mendapatkan pilihan kategori dari pengguna
                        String choiceCategory = inputHandler.getUserInputTextWithMessage(tampilItem());

                        if (choiceCategory.equals("0")) { continue; } // Jika pengguna memilih 0, kembali ke menu utama
                        choice = stokGudang.convertCategoryNameToIndex(choiceCategory);

                        // Menampilkan daftar barang dalam kategori yang dipilih dan memanggil metode untuk menambahkan barang baru pada kategori tersebut
                        System.out.print(stokGudang.displayItemsInCategory(choice - 1));
                        stokGudang.addItem(choice - 1);
                    }
                    case 3 -> stokGudang.updateItem(); // Memanggil metode untuk mengupdate barang yang sudah ada di dalam gudang
                    case 4 -> stokGudang.displayAllItems(); // Memanggil metode untuk menampilkan daftar semua barang yang ada di dalam gudang
                    default -> inputHandler.errorMessage("Maaf, input diluar batas pilihan");
                }
            } catch (Exception e) {
                // Menangkap dan menampilkan pesan jika terjadi exception (kesalahan)
                System.out.println("Keluar dari program");
                break; // Keluar dari perulangan untuk mengakhiri program
            }
            System.out.println("\n\n");
        }
        inputHandler.close();
    }

    // Menampilkan menu utama dalam bentuk string
    private static String tampilMenu () {
        return """
                |================================|
                |              Menu              |
                |================================|
                |  1. Lihat dan Tambah Kategori  |
                |  2. Tambah Item                |
                |  3. Update Jumlah Item         |
                |  4. Tampilkan Stok Gudang      |
                |  0. Keluar                     |
                |================================|
                Silahkan masukkan pilihan:\s""";
    }

    // Menampilkan daftar kategori dan meminta pengguna untuk memilih kategori dalam bentuk string
    private static String tampilItem () {
        return stokGudang.displayAllCategories() +
                "0. Keluar\n" +
                "Silakan pilih kategori: ";
    }
}
