/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alibatis.demo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tabel transaksi")
@NamedQueries({
    @NamedQuery(name = "TabelTransaksi.findAll", query = "SELECT t FROM TabelTransaksi t"),
    @NamedQuery(name = "TabelTransaksi.findByIdTransaksi", query = "SELECT t FROM TabelTransaksi t WHERE t.idTransaksi = :idTransaksi"),
    @NamedQuery(name = "TabelTransaksi.findByKodeBarang", query = "SELECT t FROM TabelTransaksi t WHERE t.kodeBarang = :kodeBarang"),
    @NamedQuery(name = "TabelTransaksi.findByQty", query = "SELECT t FROM TabelTransaksi t WHERE t.qty = :qty"),
    @NamedQuery(name = "TabelTransaksi.findByAlamatPembeli", query = "SELECT t FROM TabelTransaksi t WHERE t.alamatPembeli = :alamatPembeli"),
    @NamedQuery(name = "TabelTransaksi.findByNoHpPembeli", query = "SELECT t FROM TabelTransaksi t WHERE t.noHpPembeli = :noHpPembeli")})
public class TabelTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Transaksi")
    private String idTransaksi;
    @Basic(optional = false)
    @Column(name = "Kode_Barang")
    private String kodeBarang;
    @Basic(optional = false)
    @Column(name = "Qty")
    private int qty;
    @Basic(optional = false)
    @Column(name = "Alamat_Pembeli")
    private String alamatPembeli;
    @Basic(optional = false)
    @Column(name = "NoHp_Pembeli")
    private String noHpPembeli;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tabelTransaksi")
    private TabelBarang tabelBarang;
    @JoinColumn(name = "Id_Pembeli", referencedColumnName = "Id_Pembeli")
    @OneToOne(optional = false)
    private TabelPembeli idPembeli;

    public TabelTransaksi() {
    }

    public TabelTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public TabelTransaksi(String idTransaksi, String kodeBarang, int qty, String alamatPembeli, String noHpPembeli) {
        this.idTransaksi = idTransaksi;
        this.kodeBarang = kodeBarang;
        this.qty = qty;
        this.alamatPembeli = alamatPembeli;
        this.noHpPembeli = noHpPembeli;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getAlamatPembeli() {
        return alamatPembeli;
    }

    public void setAlamatPembeli(String alamatPembeli) {
        this.alamatPembeli = alamatPembeli;
    }

    public String getNoHpPembeli() {
        return noHpPembeli;
    }

    public void setNoHpPembeli(String noHpPembeli) {
        this.noHpPembeli = noHpPembeli;
    }

    public TabelBarang getTabelBarang() {
        return tabelBarang;
    }

    public void setTabelBarang(TabelBarang tabelBarang) {
        this.tabelBarang = tabelBarang;
    }

    public TabelPembeli getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(TabelPembeli idPembeli) {
        this.idPembeli = idPembeli;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaksi != null ? idTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelTransaksi)) {
            return false;
        }
        TabelTransaksi other = (TabelTransaksi) object;
        if ((this.idTransaksi == null && other.idTransaksi != null) || (this.idTransaksi != null && !this.idTransaksi.equals(other.idTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alibatis.demo.TabelTransaksi[ idTransaksi=" + idTransaksi + " ]";
    }
    
}
