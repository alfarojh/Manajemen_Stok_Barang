public class StokGudang {
    private final inputHandler inputHandler = new inputHandler();
    private String [][][] stokBarang = {{{"Baju", "5"}}};
    private String[] kategori = { "Pakaian" };

    private String tampilanMenuKategori () {
        return tampilkanKategori() +
                "0. Tambah Kategori\n" +
                "Silahkan masukkan pilihan: ";
    }
    public void tambahKategori () {
        int pilih = inputHandler.isDigit(tampilanMenuKategori());

        if (pilih == 0){
            String[] tmpKategori = new String[kategori.length+1];
            String namaKategori = "";
            boolean error = true;
            while (error) {
                error = false;
                System.out.print("Berikan nama kategori: ");
                namaKategori = inputHandler.inputText();
                for (String s : kategori) {
                    if (s.equals(namaKategori)) {
                        inputHandler.errorMessage("Kategori sudah ada!");
                        error = true;
                        break;
                    }
                }
            }

            System.out.print("Masukkan nama barang: ");
            String namaBarang = inputHandler.inputText();

            String jumlahItem = String.valueOf(inputHandler.isDigit("Masukkan jumlah item: "));

            System.arraycopy(kategori, 0, tmpKategori, 0, kategori.length);
            tmpKategori[kategori.length] = namaKategori;
            kategori = tmpKategori;

            String[][][] tmpArray= new String[stokBarang.length + 1][stokBarang[0].length][2];
            for (int i = 0; i < stokBarang.length; i++) {
                for (int j = 0; j < stokBarang[0].length; j++) {
                    tmpArray[i][j][0] = stokBarang[i][j][0];
                    tmpArray[i][j][1] = stokBarang[i][j][1];
                }
            }
            tmpArray[stokBarang.length][0][0] = namaBarang;
            tmpArray[stokBarang.length][0][1] = jumlahItem;
            stokBarang = tmpArray;
        } else {
            System.out.println();
            System.out.println(tampilkanListBarangKategori(pilih-1));
        }
    }
    public void tambahBarang (int indexKategori) {
        if (indexKategori < stokBarang.length) {
            int indexItemKosong = -1;
            for (int i = 0; i < stokBarang[indexKategori].length; i++) {
                if (stokBarang[indexKategori][i][0] == null) {
                    indexItemKosong = i;
                    break;
                }
            }
            String namaBarang = "";
            boolean error = true;
            while (error) {
                error = false;
                System.out.print("Masukkan nama barang baru: ");
                namaBarang = inputHandler.inputText();
                for (int i = 0; i < stokBarang[indexKategori].length; i++) {
                    if (namaBarang.equals(stokBarang[indexKategori][i][0])) {
                        inputHandler.errorMessage("Barang sudah ada!");
                        error = true;
                        break;
                    }
                }
            }

            String jumlahItem = String.valueOf(inputHandler.isDigit("Masukkan jumlah item: "));

            if (indexItemKosong >= 0) {
                stokBarang[indexKategori][indexItemKosong][0] = namaBarang;
                stokBarang[indexKategori][indexItemKosong][1] = jumlahItem;
            } else {
                String[][][] tmpArray = new String[stokBarang.length][stokBarang[0].length + 1][2];
                for (int i = 0; i < stokBarang.length; i++) {
                    for (int j = 0; j < stokBarang[0].length; j++) {
                        tmpArray[i][j][0] = stokBarang[i][j][0];
                        tmpArray[i][j][1] = stokBarang[i][j][1];
                    }
                }
                tmpArray[indexKategori][stokBarang[0].length][0] = namaBarang;
                tmpArray[indexKategori][stokBarang[0].length][1] = jumlahItem;
                stokBarang = tmpArray;
            }
        } else {
            inputHandler.errorMessage("Maaf, kategori tidak ditemukan\n" +
                    "Barang gagal ditambahkan!");
        }
    }
    public String tampilkanListBarangKategori (int indexKategori) {
        StringBuilder text = new StringBuilder();
        if (indexKategori < stokBarang.length) {
            System.out.println("\nList item untuk kategori " + kategori[indexKategori]);
            for (int i = 0; i < stokBarang[0].length; i++) {
                if (stokBarang[indexKategori][i][0] != null) {
                    text.append((i + 1)).append(". ").append(stokBarang[indexKategori][i][0]).append(": ").append(stokBarang[indexKategori][i][1]).append("\n");
                }
            }
        }
        return text.toString();
    }
    private String tampilanMenuUpdateKategori () {
        return "Kategori\n" +
                tampilkanKategori() +
                "Pilih kategori yang mau diupdate: ";
    }
    private String tampilanMenuUpdateItem (int indexKategori) {
        return tampilkanListBarangKategori(indexKategori-1) + "\n" +
                "Pilih item uang ingin diupdate: ";
    }
    public void updateBarang () {
        int indexKategori = inputHandler.isDigit(tampilanMenuUpdateKategori()) ;
        while (indexKategori > kategori.length) {
            inputHandler.errorMessage("Input melebihi batas kategori");
            indexKategori = inputHandler.isDigit(tampilanMenuUpdateKategori());
        }

        int indexItem = inputHandler.isDigit(tampilanMenuUpdateItem(indexKategori));
        while (indexItem > stokBarang[indexKategori - 1].length) {
            inputHandler.errorMessage("Input melebihi batas item");
            indexItem = inputHandler.isDigit(tampilanMenuUpdateItem(indexKategori));
        }

        int jumlahItem = inputHandler.isDigit("Masukkan jumlah (+/-): ");

        if (Integer.parseInt(stokBarang[indexKategori - 1][indexItem - 1][1]) + jumlahItem >= 0) {
            int convertInt = Integer.parseInt(stokBarang[indexKategori - 1][indexItem - 1][1]);
            stokBarang[indexKategori - 1][indexItem - 1][1] = String.valueOf(convertInt + jumlahItem);
        } else {
            inputHandler.errorMessage("Maaf, item tidak mencukupi");
        }

    }
    public String tampilkanKategori () {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < kategori.length; i++) {
            text.append((i + 1)).append(". ").append(kategori[i]).append("\n");
        }
        return text.toString();
    }
    public void tampilkanListBarang () {
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
