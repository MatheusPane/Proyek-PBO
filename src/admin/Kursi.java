package admin;

public class Kursi {
    private String nomorKursi;
    private String status;

    public Kursi(String nomorKursi, String status) {
        this.nomorKursi = nomorKursi;
        this.status = status;
    }

    public String getNomorKursi() {
        return nomorKursi;
    }

    public void setNomorKursi(String nomorKursi) {
        this.nomorKursi = nomorKursi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
