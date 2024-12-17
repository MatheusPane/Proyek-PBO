package admin;

public class Film {

    private String judul;
    private String genre;
    private int tahunRilis;
    private int durasi;
    private String sutradara;

    public Film(String judul, String genre, int tahunRilis, int durasi, String sutradara) {
        this.judul = judul;
        this.genre = genre;
        this.tahunRilis = tahunRilis;
        this.durasi = durasi;
        this.sutradara = sutradara;
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
}
