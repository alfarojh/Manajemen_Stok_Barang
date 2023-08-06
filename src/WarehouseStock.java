public class WarehouseStock {
    private final inputHandler inputHandler = new inputHandler();
    // Database untuk menyimpan stok barang yang berisi informasi nama barang dan jumlahnya
    private String[][][] itemStock = {
            {{"Baju", "5"}, {"Celana", "8"}},
            {{"Nasi", "3"}, {"Ikan", "2"}}};
    private String[] category = {"Pakaian", "Makanan"}; // Database untuk menyimpan daftar kategori barang

    // Fungsi ini menambahkan kategori baru atau menampilkan barang dalam kategori yang dipilih
    public void displayAndAddCategories() {
        String nameCategory = inputHandler.getUserInputTextWithMessage(displayAddMenuCategory());
        int indexCategory = convertUserInputToCategoryIndex(nameCategory);

        // Jika pengguna memasukkan input di luar batas kategori, tampilkan pesan kesalahan
        while (indexCategory > category.length || indexCategory < 0) {
            inputHandler.errorMessage("Maaf, kategori tidak tersedia.");
            System.out.println();
            nameCategory = inputHandler.getUserInputTextWithMessage(displayAddMenuCategory());
            indexCategory = convertUserInputToCategoryIndex(nameCategory);
        }

        if (indexCategory == 0) {
            addCategoryList(); // Jika pilihan 0, tambahkan kategori baru
        } else {
            System.out.println();
            System.out.println(displayItemsInCategory(indexCategory - 1)); // Tampilkan barang dalam kategori yang dipilih
        }
    }

    // Fungsi ini menambahkan kategori baru
    private void addCategoryList() {
        String categoryName = "";
        boolean error = true;

        while (error) {
            error = false;
            System.out.print("Berikan nama kategori: ");
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
        String[] tempCategory = new String[category.length + 1];
        System.arraycopy(category, 0, tempCategory, 0, category.length);
        tempCategory[category.length] = categoryName; // Tambahkan kategori baru ke dalam tempCategory
        category = tempCategory; // Timpa array category dengan array tempCategory yang sudah ditambahkan kategori baru

        addCategoryInStock(itemName, itemCount); // Tambahkan barang ke dalam kategori yang baru dibuat
    }
    // Fungsi ini menambahkan barang baru ke dalam kategori dan stok barang.
    private void addCategoryInStock(String itemName, String itemCount) {
        // Buat array baru dengan ukuran lebih besar untuk menampung barang baru.
        String[][][] tempArray = new String[itemStock.length + 1][1][2];

        // Salin isi array itemStock ke dalam tempArray.
        System.arraycopy(itemStock, 0, tempArray, 0, itemStock.length);

        // Tambahkan barang baru ke dalam tempArray.
        tempArray[tempArray.length - 1][0][0] = itemName;
        tempArray[tempArray.length - 1][0][1] = itemCount;

        // Timpa array itemStock dengan array tempArray yang sudah ditambahkan barang baru.
        itemStock = tempArray;
    }

    // Fungsi ini menambahkan item berdasarkan input pengguna
    public void addItemByUserInput () {
        // Menampilkan daftar kategori dan mendapatkan pilihan kategori dari pengguna
        String choiceCategory = inputHandler.getUserInputTextWithMessage(displayCategoryMenuForUserSelection());

        if (choiceCategory.equals("0")) { return; } // Jika pengguna memilih 0, kembali ke menu utama
        int choice = convertUserInputToCategoryIndex(choiceCategory);

        // Menampilkan daftar barang dalam kategori yang dipilih dan memanggil metode untuk menambahkan barang baru pada kategori tersebut
        System.out.print(displayItemsInCategory(choice - 1));
        addItemWithValidation(choice - 1);
    }
    
    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada atau membuat kategori baru jika tidak ada kategori yang sesuai.
    private void addItemWithValidation(int indexCategory) {
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
        addItemByCategoryInStock(indexCategory, itemName, itemCount);
    }

    // Fungsi ini menambahkan barang baru ke dalam kategori yang sudah ada dengan memperluas ukuran array.
    private void addItemByCategoryInStock(int indexCategory, String itemName, String itemCount) {
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
        String nameCategory = inputHandler.getUserInputTextWithMessage(displayCategoryMenuForUserSelection());
        int indexCategory = convertUserInputToCategoryIndex(nameCategory);
        while (indexCategory > category.length || indexCategory < 0) {
            inputHandler.errorMessage("Kategori tidak tersedia.");
            nameCategory = inputHandler.getUserInputTextWithMessage(displayCategoryMenuForUserSelection());
            indexCategory = convertUserInputToCategoryIndex(nameCategory);
        }
        if (indexCategory == 0) { return; } // Jika pengguna memilih 0, maka kembali ke menu awal

        // Memeriksa apakah input merupakan bilangan bulat dan tidak melebihi jumlah item yang tersedia dalam kategori tertentu
        String nameItem = inputHandler.getUserInputTextWithMessage(displayItemMenuForUserSelection(indexCategory));
        int indexItem = convertUserInputToItemIndex(indexCategory, nameItem);
        System.out.println("Index item: " + indexItem);
        while (indexItem > itemStock[indexCategory - 1].length || indexItem < 0) {
            inputHandler.errorMessage("Item tidak tersedia.");
            nameItem = inputHandler.getUserInputTextWithMessage(displayItemMenuForUserSelection(indexCategory));
            indexItem = convertUserInputToItemIndex(indexCategory, nameItem);
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
    private int convertUserInputToCategoryIndex (String name) {
        try {
            // Jika pengguna memasukkan input berupa angka
            return Integer.parseInt(name);
        } catch (Exception e) {
            // Jika pengguna memasukkan input berupa nama
            return getIndexByCategoryName(name);
        }
    }

    // Fungsi ini digunakan untuk mengonversi input user berupa nama item atau angka menjadi indeks kategori
    private int convertUserInputToItemIndex (int indexCategory, String name) {
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
                if (indexItem % 2 == 0) { text.append("\033[34m"); }
                text.append("| ")
                        .append(inputHandler.formatAutoSpacingCenter(String.valueOf(indexItem + 1), lengthMaxText()[1][0])).append(" | ")
                        .append(inputHandler.formatAutoSpacingLeft(itemStock[indexCategory][indexItem][0],lengthMaxText()[1][1])).append(" | ")
                        .append(inputHandler.formatAutoSpacingCenter(itemStock[indexCategory][indexItem][1],lengthMaxText()[1][2])).append(" | ")
                        .append("\n").append("\u001B[0m");
            }
            text.append(createLine(lengthMaxText()[0][0], '=')).append("\n");
        }
        return text.toString(); // Mengembalikan daftar item pada category yang dipilih dalam bentuk string
    }

    // Fungsi ini menampilkan menu kategori yang ada
    private String displayAddMenuCategory() {
        return displayAllCategories() +
                "0. Tambah kategori\n" +
                "Silahkan masukkan pilihan: ";
    }

    // Fungsi ini menampilkan menu pemilihan item berdasarkan kategori yang dipilih dalam bentuk string
    private String displayItemMenuForUserSelection(int indexCategory) {
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
        return createLine(lengthMaxText()[0][0], '=') + "\n" +
                "|  Kategori: " + inputHandler.formatAutoSpacingLeft(category[indexCategory], lengthMaxText()[0][0]) + "  |\n" +
                createLine(lengthMaxText()[0][0], '=') + "\n" +
                "| " + inputHandler.formatAutoSpacingCenter("Id", lengthMaxText()[1][0]) +
                " | " +
                inputHandler.formatAutoSpacingCenter("Nama Barang", lengthMaxText()[1][1]) +
                " | " + inputHandler.formatAutoSpacingCenter("Qty", lengthMaxText()[1][2]) + " |\n" +
                createLine(lengthMaxText()[0][0], '-');
    }

    // Menampilkan daftar kategori dan meminta pengguna untuk memilih kategori dalam bentuk string
    private String displayCategoryMenuForUserSelection () {
        return displayAllCategories() +
                "0. Keluar\n" +
                "Silakan pilih kategori: ";
    }

    // Untuk membuat garis penutup berdasarkan panjang text
    private String createLine (int lengthText, char character) {
        return "|" + String.valueOf(character).repeat(Math.max(0, 14 + lengthText)) + "|";
    }

    // Mengatur lebar colomn pada tabel
    private int[][] lengthMaxText () {
        /*
            Kolom ke-0 : Id
            Kolom ke-1 : Nama Barang
            Kolom ke-2 : Qty
        */
        int[] widthColumn = {4, 29, 7};
        int lengthText = widthColumn[0] + widthColumn[1] + widthColumn[2] - 6;
        return new int[][] {{lengthText}, widthColumn};
    }
}
