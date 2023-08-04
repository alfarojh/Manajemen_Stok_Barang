public class StokGudang {
    private final inputHandler inputHandler = new inputHandler();
    // Database untuk menyimpan stok barang yang berisi informasi nama barang dan jumlahnya
    private String[][][] itemStock = {
            {{"Baju", "5"}, {"Celana", "8"}},
            {{"Nasi", "3"}, {"Ikan", "2"}}};
    private String[] category = {"Pakaian", "Makanan"}; // Database untuk menyimpan daftar kategori barang

    // Fungsi ini menampilkan menu kategori yang ada
    private String displayCategoryMenu() {
        return displayCategory() +
                "0. Tambah kategori\n" +
                "Silahkan masukkan pilihan: ";
    }

    // Fungsi ini menambahkan kategori baru atau menampilkan barang dalam kategori yang dipilih
    public void addCategory() {
        int choose = inputHandler.getIntegerInputWithDigitValidation(displayCategoryMenu());

        // Jika pengguna memasukkan input di luar batas kategori, tampilkan pesan kesalahan
        while (choose > category.length) {
            inputHandler.errorMessage("Input diluar batas kategori.");
            System.out.println();
            choose = inputHandler.getIntegerInputWithDigitValidation(displayCategoryMenu());
        }

        if (choose == 0) {
            addNewCategory(); // Jika pilihan 0, tambahkan kategori baru
        } else {
            System.out.println();
            System.out.println(displayItemsInCategory(choose - 1)); // Tampilkan barang dalam kategori yang dipilih
        }
    }

    // Fungsi ini menambahkan kategori baru
    private void addNewCategory() {
        String[] tempCategory = new String[category.length + 1];
        String categoryName = "";
        boolean error = true;

        while (error) {
            error = false;
            System.out.print("Berikan nama category: ");
            categoryName = inputHandler.getUserInputText();

            // Pengguna akan kembali ke menu utama ketika mengetik kata 'keluar'
            if (categoryName.equalsIgnoreCase("keluar")) { break; }

            // Periksa apakah kategori sudah ada sebelumnya
            for (String s : category) {
                if (s.equals(categoryName)) {
                    inputHandler.errorMessage("Kategori sudah ada!");
                    System.out.println("Ketik 'keluar' untuk kembali ke menu utama.");
                    error = true;
                    break;
                }
            }
        }

        // Meminta input nama barang dari user
        System.out.print("Masukkan nama barang: ");
        String itemName = inputHandler.getUserInputText();

        // Meminta input jumlah item dari user
        String itemCount = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));

        // Salin isi array category ke dalam array tempCategory
        System.arraycopy(category, 0, tempCategory, 0, category.length);
        tempCategory[category.length] = categoryName; // Tambahkan kategori baru ke dalam tempCategory
        category = tempCategory; // Timpa array category dengan array tempCategory yang sudah ditambahkan kategori baru

        addItemToCategoryInStock(itemName, itemCount); // Tambahkan barang ke dalam kategori yang baru dibuat
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori dan stok barang.
    private void addItemToCategoryInStock(String itemName, String itemCount) {
        // Buat array baru dengan ukuran lebih besar untuk menampung barang baru.
        String[][][] tmpArray = new String[itemStock.length + 1][itemStock[0].length][2];

        // Salin isi array itemStock ke dalam tmpArray.
        for (int indexCategory = 0; indexCategory < itemStock.length; indexCategory++) {
            for (int indexItem = 0; indexItem < itemStock[0].length; indexItem++) {
                tmpArray[indexCategory][indexItem][0] = itemStock[indexCategory][indexItem][0];
                tmpArray[indexCategory][indexItem][1] = itemStock[indexCategory][indexItem][1];
            }
        }

        // Tambahkan barang baru ke dalam tmpArray.
        tmpArray[itemStock.length][0][0] = itemName;
        tmpArray[itemStock.length][0][1] = itemCount;

        // Timpa array itemStock dengan array tmpArray yang sudah ditambahkan barang baru.
        itemStock = tmpArray;
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada atau membuat kategori baru jika tidak ada kategori yang sesuai.
    public void addItem(int indexCategory) {
        // Jika input kategori melebihi jumlah kategori yang tersedia, tampilkan pesan kesalahan
        if (indexCategory >= itemStock.length) {
            inputHandler.errorMessage("Maaf, kategori tidak ditemukan.\nBarang gagal ditambahkan!");
            return;
        }

        String itemName = "";
        boolean error = true;

        while (error) {
            error = false;
            System.out.print("Masukkan nama barang baru: ");
            itemName = inputHandler.getUserInputText();

            // Pengguna akan kembali ke menu utama ketika mengetik kata 'keluar'
            if (itemName.equalsIgnoreCase("keluar")) { break; }

            // Periksa apakah barang dengan nama yang sama sudah ada dalam kategori yang dipilih.
            for (int indexItem = 0; indexItem < itemStock[indexCategory].length; indexItem++) {
                if (itemName.equals(itemStock[indexCategory][indexItem][0])) {
                    inputHandler.errorMessage("Barang sudah ada!");
                    System.out.println("Ketik 'keluar' untuk kembali ke menu utama.");
                    error = true;
                    break;
                }
            }
        }

        // Untuk menginput jumlah item yang ingin dimasukkan oleh user
        String itemCount = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));

        // Tambahkan barang ke indeks yang kosong dalam kategori.
        for (int indexItem = 0; indexItem < itemStock[indexCategory].length; indexItem++) {
            if (itemStock[indexCategory][indexItem][0] == null) {
                itemStock[indexCategory][indexItem][0] = itemName;
                itemStock[indexCategory][indexItem][1] = itemCount;
                return; // Keluar dari fungsi setelah barang ditambahkan.
            }
        }

        // Jika tidak ada indeks kosong, tambahkan barang baru ke dalam kategori dengan ukuran array itemStock yang diperbesar.
        addItemBasedOnStock(indexCategory, itemName, itemCount);
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada dengan memperluas ukuran array itemStock jika kategori tersebut belum memiliki indeks kosong.
    private void addItemBasedOnStock(int indexCategory, String itemName, String itemCount) {
        // Buat array baru dengan ukuran lebih besar untuk menampung barang baru dalam kategori yang sudah ada.
        String[][][] tmpArray = new String[itemStock.length][itemStock[0].length + 1][2];

        // Salin isi array itemStock ke dalam tmpArray.
        for (int categoryIndex = 0; categoryIndex < itemStock.length; categoryIndex++) {
            for (int indexItem = 0; indexItem < itemStock[0].length; indexItem++) {
                tmpArray[categoryIndex][indexItem][0] = itemStock[categoryIndex][indexItem][0];
                tmpArray[categoryIndex][indexItem][1] = itemStock[categoryIndex][indexItem][1];
            }
        }

        // Tambahkan barang baru ke dalam tmpArray pada indeks yang baru.
        tmpArray[indexCategory][itemStock[0].length][0] = itemName;
        tmpArray[indexCategory][itemStock[0].length][1] = itemCount;

        // Timpa array itemStock dengan array tmpArray yang sudah ditambahkan barang baru dalam kategori yang sudah ada.
        itemStock = tmpArray;
    }

    // Fungsi ini menampilkan daftar kategori yang ada dalam bentuk string
    public String displayCategory() {
        StringBuilder text = new StringBuilder();
        for (int indexCategory = 0; indexCategory < category.length; indexCategory++) {
            text.append((indexCategory + 1)).append(". ").append(category[indexCategory]).append("\n");
        }
        return text.toString();
    }

    // Fungsi ini menampilkan menu pemilihan kategori dalam bentuk string
    private String displayCategorySelection() {
        return "Kategori \n" +
                displayCategory() +
                "Pilih category yang ingin diupdate: ";
    }

    // Fungsi ini menampilkan menu pemilihan item berdasarkan kategori yang dipilih dalam bentuk string
    private String displayItemSelectionByCategory(int indexCategory) {
        return displayItemsInCategory(indexCategory - 1) + "\n" +
                "Pilih item yang ingin diupdate: ";
    }

    // Fungsi ini digunakan untuk mengupdate jumlah item pada kategori dan item yang dipilih
    public void updateItem() {
        // Memeriksa apakah input merupakan bilangan bulat dan tidak melebihi jumlah kategori yang tersedia
        int indexCategory = inputHandler.getIntegerInputWithDigitValidation(displayCategorySelection());
        while (indexCategory > category.length) {
            inputHandler.errorMessage("Input melebihi batas category.");
            indexCategory = inputHandler.getIntegerInputWithDigitValidation(displayCategorySelection());
        }

        // Memeriksa apakah input merupakan bilangan bulat dan tidak melebihi jumlah item yang tersedia dalam kategori tertentu
        int indexItem = inputHandler.getIntegerInputWithDigitValidation(displayItemSelectionByCategory(indexCategory));
        while (indexItem > itemStock[indexCategory - 1].length) {
            inputHandler.errorMessage("Input melebihi batas item.");
            indexItem = inputHandler.getIntegerInputWithDigitValidation(displayItemSelectionByCategory(indexCategory));
        }

        // Meminta input jumlah item yang akan ditambahkan atau dikurangi
        int itemCount = inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item (+/-): ");

        // Memperbarui stok item sesuai dengan input
        updateStockItem(indexCategory, indexItem, itemCount);
    }

    // Fungsi ini digunakan untuk mengupdate jumlah item pada kategori dan item yang dipilih
    private void updateStockItem(int indexCategory, int indexItem, int itemCount) {
        // Jika jumlah item yang dikurangi kurang dari stok yang tersedia, tampilkan pesan kesalahan
        if (Integer.parseInt(itemStock[indexCategory - 1][indexItem - 1][1]) + itemCount >= 0) {
            int convertInt = Integer.parseInt(itemStock[indexCategory - 1][indexItem - 1][1]);
            itemStock[indexCategory - 1][indexItem - 1][1] = String.valueOf(convertInt + itemCount);
        } else {
            inputHandler.errorMessage("Maaf, item tidak mencukupi");
        }
    }

    // Fungsi ini menampilkan daftar item pada kategori tertentu dalam bentuk string
    public String displayItemsInCategory(int indexCategory) {
        StringBuilder text = new StringBuilder();
        if (indexCategory < itemStock.length) {
            text.append(displayTitle(indexCategory))
                    .append("\n");
            // Tampilkan daftar item pada kategori yang dipilih
            for (int indexItem = 0; indexItem < itemStock[0].length; indexItem++) {
                if (itemStock[indexCategory][indexItem][0] != null) {
                    text.append("| ")
                            .append(inputHandler.formatAutoSpacingCenter(String.valueOf(indexItem + 1), lengthMaxText()[0]))
                            .append(" | ")
                            .append(inputHandler.formatAutoSpacingLeft(itemStock[indexCategory][indexItem][0],lengthMaxText()[1]))
                            .append(" | ")
                            .append(inputHandler.formatAutoSpacingCenter(itemStock[indexCategory][indexItem][1],lengthMaxText()[2]))
                            .append(" | ")
                            .append("\n");
                }
            }
            text.append(createLine(30));
        }
        return text.toString(); // Mengembalikan daftar item pada category yang dipilih dalam bentuk string
    }

    // Fungsi ini menampilkan daftar barang untuk setiap kategori yang ada
    public void displayAllItems() {
        for (int indexCategory = 0; indexCategory < itemStock.length; indexCategory++) {
            System.out.println(displayItemsInCategory(indexCategory));
            System.out.println("\n");
        }
    }

    //  Tampilan judul untuk tabel barang
    private String displayTitle (int indexCategory) {
        StringBuilder text = new StringBuilder();
        int panjangText = 30; // Menentukan panjang teks yang akan ditampilkan

        text.append(createLine(panjangText))
                .append("\n")
                .append("|  Kategori: ")
                .append(inputHandler.formatAutoSpacingLeft(category[indexCategory], panjangText))
                .append("  |\n")
                .append(createLine(panjangText)).append("\n")
                .append("| ").append(inputHandler.formatAutoSpacingCenter("Id", lengthMaxText()[0]))
                .append(" | ")
                .append(inputHandler.formatAutoSpacingCenter("Nama Barang", lengthMaxText()[1]))
                .append(" | ")
                .append(inputHandler.formatAutoSpacingCenter("Qty", lengthMaxText()[2]))
                .append(" |\n")
                .append(createLine(panjangText));

        return text.toString();
    }


    // Untuk membuat garis penutup berdasarkan panjang text
    private String createLine (int lengthText) {
        String garis = "|"; // Inisialisasi variabel garis dengan karakter pembuka "|"
        for (int i = 0; i < 14 + lengthText; i++) { // Melakukan perulangan sebanyak 14 + panjang teks
            garis += "="; // Menambahkan karakter "=" ke variabel garis
        }
        garis += "|"; // Menambahkan karakter penutup "|" ke variabel garis
        return garis; // Mengembalikan nilai variabel garis
    }

    private int[] lengthMaxText () {
        int[] temp = {4, 27, 5};
        return temp;
    }
}
