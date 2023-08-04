public class StokGudang {
    private final inputHandler inputHandler = new inputHandler();
    private String[][][] itemStock = {{{"Baju", "5"}}}; // Database untuk menyimpan stok barang yang berisi informasi nama barang dan jumlahnya
    private String[] category = {"Pakaian"}; // Database untuk menyimpan daftar kategori barang


    // Fungsi ini menampilkan menu kategori yang ada
    private String displayCategoryMenu() {
        return displayCategory() +
                "0. Tambah kategori\n" +
                "Silahkan masukkan pilihan: ";
    }

    // Fungsi ini menambahkan kategori baru atau menampilkan barang dalam kategori yang dipilih
    public void addCategory() {
        int pilih = inputHandler.getIntegerInputWithDigitValidation(displayCategoryMenu());

        if (pilih == 0) {
            addNewCategory(); // Jika pilihan 0, tambahkan kategori baru
        } else {
            System.out.println();
            System.out.println(displayItemsInCategory(pilih - 1)); // Tampilkan barang dalam kategori yang dipilih
        }
    }

    // Fungsi ini menambahkan kategori baru
    private void addNewCategory() {
        String[] tmpcategory = new String[category.length + 1];
        String namacategory = "";
        boolean error = true;

        while (error) {
            error = false;
            System.out.print("Berikan nama category: ");
            namacategory = inputHandler.getUserInputText();

            // Periksa apakah kategori sudah ada sebelumnya
            for (String s : category) {
                if (s.equals(namacategory)) {
                    inputHandler.errorMessage("category sudah ada!");
                    error = true;
                    break;
                }
            }
        }

        System.out.print("Masukkan nama barang: ");
        String namaBarang = inputHandler.getUserInputText();
        String jumlahItem = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));

        // Salin isi array category ke dalam array tmpcategory
        System.arraycopy(category, 0, tmpcategory, 0, category.length);
        tmpcategory[category.length] = namacategory; // Tambahkan kategori baru ke dalam tmpcategory
        category = tmpcategory; // Timpa array category dengan array tmpcategory yang sudah ditambahkan kategori baru

        addItemToCategoryInStock(namaBarang, jumlahItem); // Tambahkan barang ke dalam kategori yang baru dibuat
    }


    // Fungsi ini menambahkan barang baru ke dalam kategori dan stok barang.
    private void addItemToCategoryInStock(String namaBarang, String jumlahItem) {
        // Buat array baru dengan ukuran lebih besar untuk menampung barang baru.
        String[][][] tmpArray = new String[itemStock.length + 1][itemStock[0].length][2];

        // Salin isi array itemStock ke dalam tmpArray.
        for (int i = 0; i < itemStock.length; i++) {
            for (int j = 0; j < itemStock[0].length; j++) {
                tmpArray[i][j][0] = itemStock[i][j][0];
                tmpArray[i][j][1] = itemStock[i][j][1];
            }
        }

        // Tambahkan barang baru ke dalam tmpArray.
        tmpArray[itemStock.length][0][0] = namaBarang;
        tmpArray[itemStock.length][0][1] = jumlahItem;

        // Timpa array itemStock dengan array tmpArray yang sudah ditambahkan barang baru.
        itemStock = tmpArray;
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada atau membuat kategori baru jika tidak ada kategori yang sesuai.
    public void addItem(int indexCategory) {
        if (indexCategory >= itemStock.length) {
            inputHandler.errorMessage("Maaf, kategori tidak ditemukan\nBarang gagal ditambahkan!");
            return;
        }

        String namaBarang = "";
        boolean error = true;

        while (error) {
            error = false;
            System.out.print("Masukkan nama barang baru: ");
            namaBarang = inputHandler.getUserInputText();

            // Periksa apakah barang dengan nama yang sama sudah ada dalam kategori yang dipilih.
            for (int i = 0; i < itemStock[indexCategory].length; i++) {
                if (namaBarang.equals(itemStock[indexCategory][i][0])) {
                    inputHandler.errorMessage("Barang sudah ada!");
                    error = true;
                    break;
                }
            }
        }

        String jumlahItem = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item:"));

        // Tambahkan barang ke indeks yang kosong dalam kategori.
        for (int i = 0; i < itemStock[indexCategory].length; i++) {
            if (itemStock[indexCategory][i][0] == null) {
                itemStock[indexCategory][i][0] = namaBarang;
                itemStock[indexCategory][i][1] = jumlahItem;
                return; // Keluar dari fungsi setelah barang ditambahkan.
            }
        }

        // Jika tidak ada indeks kosong, tambahkan barang baru ke dalam kategori dengan ukuran array itemStock yang diperbesar.
        addItemBasedOnStock(indexCategory, namaBarang, jumlahItem);
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada dengan memperluas ukuran array itemStock jika kategori tersebut belum memiliki indeks kosong.
    private void addItemBasedOnStock(int indexCategory, String namaBarang, String jumlahItem) {
        // Buat array baru dengan ukuran lebih besar untuk menampung barang baru dalam kategori yang sudah ada.
        String[][][] tmpArray = new String[itemStock.length][itemStock[0].length + 1][2];

        // Salin isi array itemStock ke dalam tmpArray.
        for (int i = 0; i < itemStock.length; i++) {
            for (int j = 0; j < itemStock[0].length; j++) {
                tmpArray[i][j][0] = itemStock[i][j][0];
                tmpArray[i][j][1] = itemStock[i][j][1];
            }
        }

        // Tambahkan barang baru ke dalam tmpArray pada indeks yang baru.
        tmpArray[indexCategory][itemStock[0].length][0] = namaBarang;
        tmpArray[indexCategory][itemStock[0].length][1] = jumlahItem;

        // Timpa array itemStock dengan array tmpArray yang sudah ditambahkan barang baru dalam kategori yang sudah ada.
        itemStock = tmpArray;
    }


    // Fungsi ini menampilkan daftar item pada kategori tertentu dalam bentuk string
    public String displayItemsInCategory(int indexCategory) {
        StringBuilder text = new StringBuilder();
        if (indexCategory < itemStock.length) {
            // Tampilkan daftar item pada kategori yang dipilih
            System.out.println("\nList item untuk category " + category[indexCategory]);
            for (int i = 0; i < itemStock[0].length; i++) {
                if (itemStock[indexCategory][i][0] != null) {
                    // Hanya tampilkan item dengan nama barang (non-null)
                    text.append((i + 1)).append(". ").append(itemStock[indexCategory][i][0]).append(": ").append(itemStock[indexCategory][i][1]).append("\n");
                }
            }
        }
        return text.toString(); // Mengembalikan daftar item pada category yang dipilih dalam bentuk string
    }

    // Fungsi ini menampilkan daftar kategori yang ada dalam bentuk string
    public String displayCategory() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < category.length; i++) {
            text.append((i + 1)).append(". ").append(category[i]).append("\n");
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
        int indexCategory = inputHandler.getIntegerInputWithDigitValidation(displayCategorySelection());

        while (indexCategory > category.length) {
            inputHandler.errorMessage("Input melebihi batas category");
            indexCategory = inputHandler.getIntegerInputWithDigitValidation(displayCategorySelection());
        }

        int indexItem = inputHandler.getIntegerInputWithDigitValidation(displayItemSelectionByCategory(indexCategory));

        while (indexItem > itemStock[indexCategory - 1].length) {
            inputHandler.errorMessage("Input melebihi batas item");
            indexItem = inputHandler.getIntegerInputWithDigitValidation(displayItemSelectionByCategory(indexCategory));
        }

        int jumlahItem = inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah (+/-): ");

        updateStockItem(indexCategory, indexItem, jumlahItem);
    }

    // Fungsi ini digunakan untuk mengupdate jumlah item pada kategori dan item yang dipilih
    private void updateStockItem(int indexCategory, int indexItem, int jumlahItem) {
        if (Integer.parseInt(itemStock[indexCategory - 1][indexItem - 1][1]) + jumlahItem >= 0) {
            int convertInt = Integer.parseInt(itemStock[indexCategory - 1][indexItem - 1][1]);
            itemStock[indexCategory - 1][indexItem - 1][1] = String.valueOf(convertInt + jumlahItem);
        } else {
            inputHandler.errorMessage("Maaf, item tidak mencukupi");
        }
    }

    // Fungsi ini menampilkan daftar barang untuk setiap kategori yang ada
    public void displayAllItems() {
        for (int i = 0; i < itemStock.length; i++) {
            System.out.println("Kategori: " + category[i]);
            for (int j = 0; j < itemStock[0].length; j++) {
                if (itemStock[i][j][0] != null) {
                    System.out.println("    " + (j + 1) + ". " + itemStock[i][j][0] + " - jumlah: " + itemStock[i][j][1]);
                }
            }
        }
    }
}
