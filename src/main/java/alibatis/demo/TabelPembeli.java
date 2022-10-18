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
@Table(name = "tabel pembeli")
@NamedQueries({
    @NamedQuery(name = "TabelPembeli.findAll", query = "SELECT t FROM TabelPembeli t"),
    @NamedQuery(name = "TabelPembeli.findByIdPembeli", query = "SELECT t FROM TabelPembeli t WHERE t.idPembeli = :idPembeli"),
    @NamedQuery(name = "TabelPembeli.findByNamaPembeli", query = "SELECT t FROM TabelPembeli t WHERE t.namaPembeli = :namaPembeli"),
    @NamedQuery(name = "TabelPembeli.findByNoHpPembeli", query = "SELECT t FROM TabelPembeli t WHERE t.noHpPembeli = :noHpPembeli"),
    @NamedQuery(name = "TabelPembeli.findByAlamatPembeli", query = "SELECT t FROM TabelPembeli t WHERE t.alamatPembeli = :alamatPembeli")})
public class TabelPembeli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pembeli")
    private String idPembeli;
    @Basic(optional = false)
    @Column(name = "Nama_Pembeli")
    private String namaPembeli;
    @Basic(optional = false)
    @Column(name = "NoHp_Pembeli")
    private String noHpPembeli;
    @Basic(optional = false)
    @Column(name = "Alamat_Pembeli")
    private String alamatPembeli;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPembeli")
    private TabelTransaksi tabelTransaksi;

    public TabelPembeli() {
    }

    public TabelPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public TabelPembeli(String idPembeli, String namaPembeli, String noHpPembeli, String alamatPembeli) {
        this.idPembeli = idPembeli;
        this.namaPembeli = namaPembeli;
        this.noHpPembeli = noHpPembeli;
        this.alamatPembeli = alamatPembeli;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNoHpPembeli() {
        return noHpPembeli;
    }

    public void setNoHpPembeli(String noHpPembeli) {
        this.noHpPembeli = noHpPembeli;
    }

    public String getAlamatPembeli() {
        return alamatPembeli;
    }

    public void setAlamatPembeli(String alamatPembeli) {
        this.alamatPembeli = alamatPembeli;
    }

    public TabelTransaksi getTabelTransaksi() {
        return tabelTransaksi;
    }

    public void setTabelTransaksi(TabelTransaksi tabelTransaksi) {
        this.tabelTransaksi = tabelTransaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPembeli != null ? idPembeli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelPembeli)) {
            return false;
        }
        TabelPembeli other = (TabelPembeli) object;
        if ((this.idPembeli == null && other.idPembeli != null) || (this.idPembeli != null && !this.idPembeli.equals(other.idPembeli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alibatis.demo.TabelPembeli[ idPembeli=" + idPembeli + " ]";
    }
    
}
