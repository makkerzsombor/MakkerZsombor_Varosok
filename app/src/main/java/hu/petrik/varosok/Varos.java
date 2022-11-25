package hu.petrik.varosok;

import com.google.gson.annotations.Expose;

public class Varos {
    private int id;
    @Expose(serialize = false)
    private String nev, orszag;
    private int lakossag;

    public Varos(int id, String nev, String orszag, int lakossag) {
        this.id = id;
        this.nev = nev;
        this.orszag = orszag;
        this.lakossag = lakossag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }

    public int getLakossag() {
        return lakossag;
    }

    public void setLakossag(int lakossag) {
        this.lakossag = lakossag;
    }
    @Override
    public String toString() {
        return String.format("%s %s %d\n", this.nev, this.orszag, this.lakossag);
    }
}
