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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "tabel pengelolaan data")
@NamedQueries({
    @NamedQuery(name = "TabelPengelolaanData.findAll", query = "SELECT t FROM TabelPengelolaanData t"),
    @NamedQuery(name = "TabelPengelolaanData.findByIdPengelolaan", query = "SELECT t FROM TabelPengelolaanData t WHERE t.idPengelolaan = :idPengelolaan"),
    @NamedQuery(name = "TabelPengelolaanData.findByIdPegawai", query = "SELECT t FROM TabelPengelolaanData t WHERE t.idPegawai = :idPegawai"),
    @NamedQuery(name = "TabelPengelolaanData.findByKodeBarang", query = "SELECT t FROM TabelPengelolaanData t WHERE t.kodeBarang = :kodeBarang"),
    @NamedQuery(name = "TabelPengelolaanData.findByQty", query = "SELECT t FROM TabelPengelolaanData t WHERE t.qty = :qty")})
public class TabelPengelolaanData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pengelolaan")
    private String idPengelolaan;
    @Basic(optional = false)
    @Column(name = "Id_Pegawai")
    private String idPegawai;
    @Basic(optional = false)
    @Column(name = "Kode_Barang")
    private String kodeBarang;
    @Basic(optional = false)
    @Column(name = "Qty")
    private int qty;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tabelPengelolaanData")
    private TabelPegawai tabelPegawai;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "qty")
    private TabelBarang tabelBarang;

    public TabelPengelolaanData() {
    }

    public TabelPengelolaanData(String idPengelolaan) {
        this.idPengelolaan = idPengelolaan;
    }

    public TabelPengelolaanData(String idPengelolaan, String idPegawai, String kodeBarang, int qty) {
        this.idPengelolaan = idPengelolaan;
        this.idPegawai = idPegawai;
        this.kodeBarang = kodeBarang;
        this.qty = qty;
    }

    public String getIdPengelolaan() {
        return idPengelolaan;
    }

    public void setIdPengelolaan(String idPengelolaan) {
        this.idPengelolaan = idPengelolaan;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
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

    public TabelPegawai getTabelPegawai() {
        return tabelPegawai;
    }

    public void setTabelPegawai(TabelPegawai tabelPegawai) {
        this.tabelPegawai = tabelPegawai;
    }

    public TabelBarang getTabelBarang() {
        return tabelBarang;
    }

    public void setTabelBarang(TabelBarang tabelBarang) {
        this.tabelBarang = tabelBarang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPengelolaan != null ? idPengelolaan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelPengelolaanData)) {
            return false;
        }
        TabelPengelolaanData other = (TabelPengelolaanData) object;
        if ((this.idPengelolaan == null && other.idPengelolaan != null) || (this.idPengelolaan != null && !this.idPengelolaan.equals(other.idPengelolaan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alibatis.demo.TabelPengelolaanData[ idPengelolaan=" + idPengelolaan + " ]";
    }
    
}
