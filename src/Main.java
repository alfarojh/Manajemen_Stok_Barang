public class Main {
    private static final WarehouseStock warehouseStock = new WarehouseStock();
    private static final inputHandler inputHandler = new inputHandler();

    public static void main(String[] args) {
        while (true){
            try {
                // Menampilkan menu dan mendapatkan pilihan dari pengguna
                int choice = inputHandler.getIntegerInputWithDigitValidation(displayMenu());
                System.out.println("===========================================================================================\n");
                switch (choice) {
                    case 0 -> throw new Exception(); // Jika pengguna memilih 0, lempar Exception untuk keluar dari program
                    case 1 -> warehouseStock.displayAndAddCategories(); // Memanggil metode untuk menambahkan kategori ke dalam gudang
                    case 2 -> warehouseStock.addItemByUserInput(); // Memanggil metode untuk menambah item ke dalam gudang
                    case 3 -> warehouseStock.updateItemQty(); // Memanggil metode untuk mengupdate barang yang sudah ada di dalam gudang
                    case 4 -> warehouseStock.displayAllItems(); // Memanggil metode untuk menampilkan daftar semua barang yang ada di dalam gudang
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
        warehouseStock.close();
    }

    // Menampilkan menu utama dalam bentuk string
    private static String displayMenu () {
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

}
