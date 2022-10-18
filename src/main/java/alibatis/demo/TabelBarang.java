/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alibatis.demo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "tabel barang")
@NamedQueries({
    @NamedQuery(name = "TabelBarang.findAll", query = "SELECT t FROM TabelBarang t"),
    @NamedQuery(name = "TabelBarang.findByKodeBarang", query = "SELECT t FROM TabelBarang t WHERE t.kodeBarang = :kodeBarang"),
    @NamedQuery(name = "TabelBarang.findByNamaBarang", query = "SELECT t FROM TabelBarang t WHERE t.namaBarang = :namaBarang"),
    @NamedQuery(name = "TabelBarang.findByMerk", query = "SELECT t FROM TabelBarang t WHERE t.merk = :merk"),
    @NamedQuery(name = "TabelBarang.findByKategori", query = "SELECT t FROM TabelBarang t WHERE t.kategori = :kategori")})
public class TabelBarang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Barang")
    private String kodeBarang;
    @Basic(optional = false)
    @Column(name = "Nama_Barang")
    private String namaBarang;
    @Basic(optional = false)
    @Column(name = "Merk")
    private String merk;
    @Basic(optional = false)
    @Column(name = "Kategori")
    private String kategori;
    @JoinColumn(name = "Kode_Barang", referencedColumnName = "Kode_Barang", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TabelTransaksi tabelTransaksi;
    @JoinColumn(name = "Qty", referencedColumnName = "Qty")
    @OneToOne(optional = false)
    private TabelPengelolaanData qty;

    public TabelBarang() {
    }

    public TabelBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public TabelBarang(String kodeBarang, String namaBarang, String merk, String kategori) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.merk = merk;
        this.kategori = kategori;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public TabelTransaksi getTabelTransaksi() {
        return tabelTransaksi;
    }

    public void setTabelTransaksi(TabelTransaksi tabelTransaksi) {
        this.tabelTransaksi = tabelTransaksi;
    }

    public TabelPengelolaanData getQty() {
        return qty;
    }

    public void setQty(TabelPengelolaanData qty) {
        this.qty = qty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeBarang != null ? kodeBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelBarang)) {
            return false;
        }
        TabelBarang other = (TabelBarang) object;
        if ((this.kodeBarang == null && other.kodeBarang != null) || (this.kodeBarang != null && !this.kodeBarang.equals(other.kodeBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alibatis.demo.TabelBarang[ kodeBarang=" + kodeBarang + " ]";
    }
    
}
