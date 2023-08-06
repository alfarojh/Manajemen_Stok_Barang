public class StokGudang {
    private final inputHandler inputHandler = new inputHandler();
    // Database untuk menyimpan stok barang yang berisi informasi nama barang dan jumlahnya
    private String[][][] itemStock = {
            {{"Baju", "5"}, {"Celana", "8"}},
            {{"Nasi", "3"}, {"Ikan", "2"}}};
    private String[] category = {"Pakaian", "Makanan"}; // Database untuk menyimpan daftar kategori barang

    // Fungsi ini menambahkan kategori baru atau menampilkan barang dalam kategori yang dipilih
    public void displayAndAddCategories() {
        String nameCategory = inputHandler.getUserInputTextWithMessage(displayCategoryMenu());
        int indexCategory = convertCategoryNameToIndex(nameCategory);

        // Jika pengguna memasukkan input di luar batas kategori, tampilkan pesan kesalahan
        while (indexCategory > category.length || indexCategory < 0) {
            inputHandler.errorMessage("Maaf, kategori tidak tersedia.");
            System.out.println();
            nameCategory = inputHandler.getUserInputTextWithMessage(displayCategoryMenu());
            indexCategory = convertCategoryNameToIndex(nameCategory);
        }

        if (indexCategory == 0) {
            addCategory(); // Jika pilihan 0, tambahkan kategori baru
        } else {
            System.out.println();
            System.out.println(displayItemsInCategory(indexCategory - 1)); // Tampilkan barang dalam kategori yang dipilih
        }
    }

    // Fungsi ini menambahkan kategori baru
    private void addCategory() {
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
        if (indexCategory >= itemStock.length || indexCategory < 0) {
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

        // Menambahkan barang baru ke dalam kategori berdasarkan input pengguna dengan memperluas ukuran array itemStock.
        String itemCount = String.valueOf(inputHandler.getIntegerInputWithDigitValidation("Masukkan jumlah item: "));
        addItemBasedOnStock(indexCategory, itemName, itemCount);
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada dengan memperluas ukuran array.
    private void addItemBasedOnStock(int indexCategory, String itemName, String itemCount) {
        // Membuat array sementara dengan ukuran yang diperluas
        String[][] tempArray = new String[itemStock[indexCategory].length + 1][2];

        // Menyalin data dari array lama ke array sementara
        System.arraycopy(itemStock[indexCategory], 0, tempArray, 0, tempArray.length - 1);

        // Menambahkan data barang baru ke array sementara
        tempArray[tempArray.length - 1][0] = itemName;
        tempArray[tempArray.length - 1][1] = itemCount;

        // Mengganti array lama dengan array sementara yang sudah diperluas
        itemStock[indexCategory] = tempArray;
    }

    // Fungsi ini digunakan untuk mengupdate jumlah item pada kategori dan item yang dipilih
    public void updateItemQty() {
        // Memeriksa apakah input merupakan bilangan bulat dan tidak melebihi jumlah kategori yang tersedia
        String nameCategory = inputHandler.getUserInputTextWithMessage(displayCategorySelection());
        int indexCategory = convertCategoryNameToIndex(nameCategory);
        while (indexCategory > category.length || indexCategory < 0) {
            inputHandler.errorMessage("Kategori tidak tersedia.");
            nameCategory = inputHandler.getUserInputTextWithMessage(displayCategorySelection());
            indexCategory = convertCategoryNameToIndex(nameCategory);
        }

        // Memeriksa apakah input merupakan bilangan bulat dan tidak melebihi jumlah item yang tersedia dalam kategori tertentu
        String nameItem = inputHandler.getUserInputTextWithMessage(displayItemSelectionByCategory(indexCategory));
        int indexItem = convertItemNameToIndex(indexCategory, nameItem);
        System.out.println("Index item: " + indexItem);
        while (indexItem > itemStock[indexCategory - 1].length || indexItem < 0) {
            inputHandler.errorMessage("Item tidak tersedia.");
            nameItem = inputHandler.getUserInputTextWithMessage(displayItemSelectionByCategory(indexCategory));
            indexItem = convertItemNameToIndex(indexCategory, nameItem);
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

    // Fungsi ini digunakan untuk mencari indeks kategori berdasarkan nama kategorinya
    private int getIndexByCategoryName (String categoryName) {
        // Melakukan perulangan untuk mencari indeks kategori berdasarkan nama kategori
        for (int indexCategory = 0; indexCategory < category.length; indexCategory++) {
            // Memeriksa apakah nama kategori sama dengan kategori yang dicari
            if (categoryName.equalsIgnoreCase(category[indexCategory])) {
                return indexCategory + 1; // Mengembalikan indeks kategori yang ditemukan ditambah 1 agar sesuai list
            }
        }

        return -1; // Jika tidak ditemukan, mengembalikan -1
    }

    // Fungsi ini digunakan untuk mencari indeks item berdasarkan nama item di kategori tertentu
    private int getIndexByItemName (int indexCategory, String itemName) {
        // Melakukan perulangan untuk mencari indeks item berdasarkan nama item
        for (int indexItem = 0; indexItem < itemStock[indexCategory].length; indexItem++) {
            // Memeriksa apakah nama item sama dengan item yang dicari
            if (itemStock[indexCategory-1][indexItem][0].equals(itemName)) {
                return indexItem + 1; // Mengembalikan indeks item yang ditemukan ditambah 1 agar sesuai list
            }
        }

        return -1; // Jika tidak ditemukan, mengembalikan -1
    }

    // Fungsi ini digunakan untuk mengonversi input user berupa nama kategori atau angka menjadi indeks kategori
    public int convertCategoryNameToIndex (String name) {
        try {
            // Jika pengguna memasukkan input berupa angka
            return Integer.parseInt(name);
        } catch (Exception e) {
            // Jika pengguna memasukkan input berupa nama
            return getIndexByCategoryName(name);
        }
    }

    // Fungsi ini digunakan untuk mengonversi input user berupa nama item atau angka menjadi indeks kategori
    private int convertItemNameToIndex (int indexCategory, String name) {
        try {
            // Jika pengguna memasukkan input berupa angka
            return Integer.parseInt(name);
        } catch (Exception e) {
            // Jika pengguna memasukkan input berupa nama
            return getIndexByItemName(indexCategory, name);
        }
    }

    // Fungsi ini menampilkan daftar kategori yang ada dalam bentuk string
    public String displayAllCategories() {
        StringBuilder text = new StringBuilder();
        for (int indexCategory = 0; indexCategory < category.length; indexCategory++) {
            text.append((indexCategory + 1)).append(". ").append(category[indexCategory]).append("\n");
        }
        return text.toString();
    }

    // Fungsi ini menampilkan daftar item pada kategori tertentu dalam bentuk string
    public String displayItemsInCategory(int indexCategory) {
        StringBuilder text = new StringBuilder();
        if (indexCategory > -1 && indexCategory < itemStock.length) {
            text.append(displayTitle(indexCategory))
                    .append("\n");
            // Tampilkan daftar item pada kategori yang dipilih
            for (int indexItem = 0; indexItem < itemStock[indexCategory].length; indexItem++) {
                text.append("| ")
                        .append(inputHandler.formatAutoSpacingCenter(String.valueOf(indexItem + 1), lengthMaxText()[0])).append(" | ")
                        .append(inputHandler.formatAutoSpacingLeft(itemStock[indexCategory][indexItem][0],lengthMaxText()[1])).append(" | ")
                        .append(inputHandler.formatAutoSpacingCenter(itemStock[indexCategory][indexItem][1],lengthMaxText()[2])).append(" | ").append("\n");
            }
            text.append(createLine(30, '=')).append("\n");
        }
        return text.toString(); // Mengembalikan daftar item pada category yang dipilih dalam bentuk string
    }

    // Fungsi ini menampilkan menu kategori yang ada
    private String displayCategoryMenu() {
        return displayAllCategories() +
                "0. Tambah kategori\n" +
                "Silahkan masukkan pilihan: ";
    }

    // Fungsi ini menampilkan menu pemilihan kategori dalam bentuk string
    private String displayCategorySelection() {
        return "Kategori \n" +
                displayAllCategories() +
                "Pilih category yang ingin diupdate: ";
    }

    // Fungsi ini menampilkan menu pemilihan item berdasarkan kategori yang dipilih dalam bentuk string
    private String displayItemSelectionByCategory(int indexCategory) {
        return displayItemsInCategory(indexCategory - 1) + "\n" +
                "Pilih item yang ingin diupdate: ";
    }

    // Fungsi ini menampilkan daftar barang untuk setiap kategori yang ada
    public void displayAllItems() {
        for (int indexCategory = 0; indexCategory < itemStock.length; indexCategory++) {
            System.out.println(displayItemsInCategory(indexCategory) + "\n");
        }
    }

    //  Tampilan judul untuk tabel barang
    private String displayTitle (int indexCategory) {
        int panjangText = 30; // Menentukan panjang teks yang akan ditampilkan

        return createLine(panjangText, '=') + "\n" +
                "|  Kategori: " + inputHandler.formatAutoSpacingLeft(category[indexCategory], panjangText) + "  |\n" +
                createLine(panjangText, '=') + "\n" +
                "| " + inputHandler.formatAutoSpacingCenter("Id", lengthMaxText()[0]) +
                " | " +
                inputHandler.formatAutoSpacingCenter("Nama Barang", lengthMaxText()[1]) +
                " | " + inputHandler.formatAutoSpacingCenter("Qty", lengthMaxText()[2]) + " |\n" +
                createLine(panjangText, '-');
    }

    // Untuk membuat garis penutup berdasarkan panjang text
    private String createLine (int lengthText, char character) {
        return "|" + String.valueOf(character).repeat(Math.max(0, 14 + lengthText)) + "|";
    }

    // Mengatur lebar colomn pada tabel
    private int[] lengthMaxText () {
        return new int[]{4, 27, 5};
    }
}
