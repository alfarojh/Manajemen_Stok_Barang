public class Main {
    // Membuat instansi dari kelas StokGudang dan ErrorHandling
    private static final StokGudang stokGudang = new StokGudang();
    private static final inputHandler inputHandler = new inputHandler();

    public static void main(String[] args) {
        while (true){
            try {
                // Menampilkan menu dan mendapatkan pilihan dari pengguna
                int pilih = inputHandler.isDigit(tampilMenu());
                System.out.println("===========================\n");
                switch (pilih) {
                    case 0 ->
                        // Jika pengguna memilih 0, lempar Exception untuk keluar dari program
                            throw new Exception();
                    case 1 ->
                        // Memanggil metode untuk menambahkan kategori ke dalam gudang
                            stokGudang.tambahKategori();
                    case 2 -> {
                        // Menampilkan daftar kategori dan mendapatkan pilihan kategori dari pengguna
                        pilih = inputHandler.isDigit(tampilItem());
                        if (pilih == 0) {
                            continue;
                        } // Jika pengguna memilih 0, kembali ke menu utama

                        // Menampilkan daftar barang dalam kategori yang dipilih dan memanggil metode untuk menambahkan barang baru pada kategori tersebut
                        System.out.println(stokGudang.tampilkanListBarangKategori(pilih - 1));
                        stokGudang.tambahBarang(pilih - 1);
                    }
                    case 3 ->
                        // Memanggil metode untuk mengupdate barang yang sudah ada di dalam gudang
                            stokGudang.updateBarang();
                    case 4 ->
                        // Memanggil metode untuk menampilkan daftar semua barang yang ada di dalam gudang
                            stokGudang.tampilkanListBarang();
                }
            } catch (Exception e) {
                // Menangkap dan menampilkan pesan jika terjadi exception (kesalahan)
                System.out.println("Keluar dari program");
                break; // Keluar dari perulangan untuk mengakhiri program
            }
            System.out.println("\n\n");
        }
        inputHandler.closeScanner();
    }

    // Menampilkan menu utama dalam bentuk string
    private static String tampilMenu () {
        return """
                        Menu
                ====================
                1. Tambah Kategori
                2. Tambah Item
                3. Update Item
                4. Tampilkan Stok Gudang
                0. Keluar
                Silahkan masukkan pilihan:\s""";
    }

    // Menampilkan daftar kategori dan meminta pengguna untuk memilih kategori dalam bentuk string
    private static String tampilItem () {
        return stokGudang.tampilkanKategori() +
                "0. Keluar\n" +
                "Silakan pilih kategori: ";
    }
}
