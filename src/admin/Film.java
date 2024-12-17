package admin;

public class Film {

    private String judul;
    private String genre;
    private int tahunRilis;
    private int durasi;
    private String sutradara;
    private String jadwalTayang;
    private int hargaTiket;

    public Film(String judul, String genre, int tahunRilis, int durasi, String sutradara, String jadwalTayang, int hargaTiket) {
        this.judul = judul;
        this.genre = genre;
        this.tahunRilis = tahunRilis;
        this.durasi = durasi;
        this.sutradara = sutradara;
        this.jadwalTayang = jadwalTayang;
        this.hargaTiket = hargaTiket;
    }

    public String getJudul() {
        return judul;
    }

    public String getGenre() {
        return genre;
    }

    public int getTahunRilis() {
        return tahunRilis;
    }

    public int getDurasi() {
        return durasi;
    }

    public String getSutradara() {
        return sutradara;
    }
    
    public String getJadwalTayang() {
        return jadwalTayang;
    }
    public int getHargaTiket() {
        return hargaTiket;
    }
}
