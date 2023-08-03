public class StokGudang {
    private final inputHandler inputHandler = new inputHandler();
    private String[][][] stokBarang = {{{"Baju", "5"}}}; // Database untuk menyimpan stok barang yang berisi informasi nama barang dan jumlahnya
    private String[] kategori = {"Pakaian"}; // Database untuk menyimpan daftar kategori barang

    // Metode untuk menampilkan menu kategori
    private String tampilanMenuKategori() {
        return tampilkanKategori() +
                "0. Tambah Kategori\n" +
                "Silahkan masukkan pilihan: ";
    }

    public void tambahKategori() {
        // Mendapatkan pilihan dari pengguna
        int pilih = inputHandler.getIntegerInputWithDigitValidation(tampilanMenuKategori());

        // Jika pilihan 0, tambah kategori baru
        if (pilih == 0) {
            String[] tmpKategori = new String[kategori.length + 1];
            String namaKategori = "";
            boolean error = true;

            // Meminta pengguna untuk memasukkan nama kategori baru yang belum ada sebelumnya
            while (error) {
                error = false;
                System.out.print("Berikan nama kategori: ");
                namaKategori = inputHandler.getUserInputText();

                // Memeriksa apakah nama kategori sudah ada dalam daftar kategori yang ada
                for (String s : kategori) {
                    if (s.equals(namaKategori)) {
                        inputHandler.errorMessage("Kategori sudah ada!");
                        error = true;
                        break;
                    }
                }
            }

            // Meminta pengguna untuk memasukkan nama barang baru dan jumlah itemnya
            System.out.print("Masukkan nama barang: ");
            String namaBarang = inputHandler.getUserInputText();
            String jumlahItem = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));

            // Menyalin isi array kategori ke array sementara (tmpKategori) dengan ukuran yang lebih besar
            System.arraycopy(kategori, 0, tmpKategori, 0, kategori.length);
            tmpKategori[kategori.length] = namaKategori;
            kategori = tmpKategori;

            // Menyalin isi array stokBarang ke array sementara (tmpArray) dengan ukuran yang lebih besar
            String[][][] tmpArray = new String[stokBarang.length + 1][stokBarang[0].length][2];
            for (int i = 0; i < stokBarang.length; i++) {
                for (int j = 0; j < stokBarang[0].length; j++) {
                    tmpArray[i][j][0] = stokBarang[i][j][0];
                    tmpArray[i][j][1] = stokBarang[i][j][1];
                }
            }

            // Menambahkan barang baru ke dalam array sementara (tmpArray) dengan jumlah item yang dimasukkan
            tmpArray[stokBarang.length][0][0] = namaBarang;
            tmpArray[stokBarang.length][0][1] = jumlahItem;
            stokBarang = tmpArray;
        } else {
            // Jika pilihan bukan 0, tampilkan daftar barang pada kategori yang dipilih
            System.out.println();
            System.out.println(tampilkanListBarangKategori(pilih - 1));
        }
    }

    public void tambahBarang(int indexKategori) {
        // Memastikan indexKategori tidak melebihi jumlah kategori yang ada (validasi)
        if (indexKategori < stokBarang.length) {
            int indexItemKosong = -1;

            // Mencari indeks item kosong (null) pada kategori yang dipilih
            for (int i = 0; i < stokBarang[indexKategori].length; i++) {
                if (stokBarang[indexKategori][i][0] == null) {
                    indexItemKosong = i;
                    break;
                }
            }
            String namaBarang = "";
            boolean error = true;

            // Meminta pengguna untuk memasukkan nama barang baru yang belum ada sebelumnya dalam kategori yang dipilih
            while (error) {
                error = false;
                System.out.print("Masukkan nama barang baru: ");
                namaBarang = inputHandler.getUserInputText();

                // Memeriksa apakah nama barang sudah ada dalam kategori yang dipilih
                for (int i = 0; i < stokBarang[indexKategori].length; i++) {
                    if (namaBarang.equals(stokBarang[indexKategori][i][0])) {
                        inputHandler.errorMessage("Barang sudah ada!");
                        error = true;
                        break;
                    }
                }
            }

            // Meminta pengguna untuk memasukkan jumlah item baru dan mengkonversi ke dalam bentuk String
            String jumlahItem = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));

            if (indexItemKosong >= 0) {
                // Jika ada indeks item kosong (null), tambahkan barang baru pada indeks tersebut
                stokBarang[indexKategori][indexItemKosong][0] = namaBarang;
                stokBarang[indexKategori][indexItemKosong][1] = jumlahItem;
            } else {
                // Jika tidak ada indeks item kosong, buat array baru dengan ukuran yang lebih besar untuk menampung barang baru
                String[][][] tmpArray = new String[stokBarang.length][stokBarang[0].length + 1][2];
                for (int i = 0; i < stokBarang.length; i++) {
                    for (int j = 0; j < stokBarang[0].length; j++) {
                        tmpArray[i][j][0] = stokBarang[i][j][0];
                        tmpArray[i][j][1] = stokBarang[i][j][1];
                    }
                }
                // Tambahkan barang baru pada array baru
                tmpArray[indexKategori][stokBarang[0].length][0] = namaBarang;
                tmpArray[indexKategori][stokBarang[0].length][1] = jumlahItem;
                stokBarang = tmpArray; // Timpa array stokBarang dengan array baru
            }
        } else {
            // Jika indexKategori tidak valid (melebihi jumlah kategori yang ada), tampilkan pesan error
            inputHandler.errorMessage("Maaf, kategori tidak ditemukan\n" +
                    "Barang gagal ditambahkan!");
        }
    }

    public String tampilkanListBarangKategori(int indexKategori) {
        StringBuilder text = new StringBuilder();
        if (indexKategori < stokBarang.length) {
            // Tampilkan daftar item pada kategori yang dipilih
            System.out.println("\nList item untuk kategori " + kategori[indexKategori]);
            for (int i = 0; i < stokBarang[0].length; i++) {
                if (stokBarang[indexKategori][i][0] != null) {
                    // Hanya tampilkan item dengan nama barang (non-null)
                    text.append((i + 1)).append(". ").append(stokBarang[indexKategori][i][0]).append(": ").append(stokBarang[indexKategori][i][1]).append("\n");
                }
            }
        }
        return text.toString(); // Mengembalikan daftar item pada kategori yang dipilih dalam bentuk string
    }

    private String tampilanMenuUpdateKategori() {
        return "Kategori\n" +
                tampilkanKategori() +
                "Pilih kategori yang ingin diupdate: ";
    }

    private String tampilanMenuUpdateItem(int indexKategori) {
        return tampilkanListBarangKategori(indexKategori - 1) + "\n" +
                "Pilih item yang ingin diupdate: ";
    }

    public void updateBarang() {
        // Meminta pengguna untuk memilih kategori yang akan diupdate
        int indexKategori = inputHandler.getIntegerInputWithDigitValidation(tampilanMenuUpdateKategori());

        // Melakukan validasi untuk memastikan indexKategori tidak melebihi jumlah kategori yang ada
        while (indexKategori > kategori.length) {
            inputHandler.errorMessage("Input melebihi batas kategori");
            indexKategori = inputHandler.getIntegerInputWithDigitValidation(tampilanMenuUpdateKategori());
        }

        // Meminta pengguna untuk memilih item pada kategori yang akan diupdate
        int indexItem = inputHandler.getIntegerInputWithDigitValidation(tampilanMenuUpdateItem(indexKategori));

        // Melakukan validasi untuk memastikan indexItem tidak melebihi jumlah item pada kategori yang dipilih
        while (indexItem > stokBarang[indexKategori - 1].length) {
            inputHandler.errorMessage("Input melebihi batas item");
            indexItem = inputHandler.getIntegerInputWithDigitValidation(tampilanMenuUpdateItem(indexKategori));
        }

        // Meminta pengguna untuk memasukkan jumlah item yang akan diupdate (dalam bentuk +/-)
        int jumlahItem = inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah (+/-): ");

        // Melakukan pengecekan apakah jumlah item setelah diupdate masih non-negatif
        if (Integer.parseInt(stokBarang[indexKategori - 1][indexItem - 1][1]) + jumlahItem >= 0) {
            // Jika jumlah item setelah diupdate masih non-negatif, lakukan update jumlah item
            int convertInt = Integer.parseInt(stokBarang[indexKategori - 1][indexItem - 1][1]);
            stokBarang[indexKategori - 1][indexItem - 1][1] = String.valueOf(convertInt + jumlahItem);
        } else {
            // Jika jumlah item setelah diupdate negatif, tampilkan pesan error
            inputHandler.errorMessage("Maaf, item tidak mencukupi");
        }
    }

    public String tampilkanKategori() {
        // Menampilkan daftar kategori yang ada dalam bentuk string
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < kategori.length; i++) {
            text.append((i + 1)).append(". ").append(kategori[i]).append("\n");
        }
        return text.toString();
    }

    public void tampilkanListBarang() {
        // Menampilkan daftar barang untuk setiap kategori yang ada
        for (int i = 0; i < stokBarang.length; i++) {
            System.out.println("Kategori: " + kategori[i]);
            for (int j = 0; j < stokBarang[0].length; j++) {
                if (stokBarang[i][j][0] != null) {
                    System.out.println("    " + (j + 1) + ". " + stokBarang[i][j][0] + " - jumlah: " + stokBarang[i][j][1]);
                }
            }
        }
    }

}
