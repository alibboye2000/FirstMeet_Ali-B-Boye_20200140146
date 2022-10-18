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
@Table(name = "tabel pegawai")
@NamedQueries({
    @NamedQuery(name = "TabelPegawai.findAll", query = "SELECT t FROM TabelPegawai t"),
    @NamedQuery(name = "TabelPegawai.findByIdPegawai", query = "SELECT t FROM TabelPegawai t WHERE t.idPegawai = :idPegawai"),
    @NamedQuery(name = "TabelPegawai.findByNoHpPegawai", query = "SELECT t FROM TabelPegawai t WHERE t.noHpPegawai = :noHpPegawai"),
    @NamedQuery(name = "TabelPegawai.findByNamaPegawai", query = "SELECT t FROM TabelPegawai t WHERE t.namaPegawai = :namaPegawai"),
    @NamedQuery(name = "TabelPegawai.findByAlamatPegawai", query = "SELECT t FROM TabelPegawai t WHERE t.alamatPegawai = :alamatPegawai")})
public class TabelPegawai implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pegawai")
    private String idPegawai;
    @Basic(optional = false)
    @Column(name = "NoHp_Pegawai")
    private String noHpPegawai;
    @Basic(optional = false)
    @Column(name = "Nama_Pegawai")
    private String namaPegawai;
    @Basic(optional = false)
    @Column(name = "Alamat_Pegawai")
    private String alamatPegawai;
    @JoinColumn(name = "Id_Pegawai", referencedColumnName = "Id_Pegawai", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TabelPengelolaanData tabelPengelolaanData;

    public TabelPegawai() {
    }

    public TabelPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public TabelPegawai(String idPegawai, String noHpPegawai, String namaPegawai, String alamatPegawai) {
        this.idPegawai = idPegawai;
        this.noHpPegawai = noHpPegawai;
        this.namaPegawai = namaPegawai;
        this.alamatPegawai = alamatPegawai;
    }

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNoHpPegawai() {
        return noHpPegawai;
    }

    public void setNoHpPegawai(String noHpPegawai) {
        this.noHpPegawai = noHpPegawai;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getAlamatPegawai() {
        return alamatPegawai;
    }

    public void setAlamatPegawai(String alamatPegawai) {
        this.alamatPegawai = alamatPegawai;
    }

    public TabelPengelolaanData getTabelPengelolaanData() {
        return tabelPengelolaanData;
    }

    public void setTabelPengelolaanData(TabelPengelolaanData tabelPengelolaanData) {
        this.tabelPengelolaanData = tabelPengelolaanData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPegawai != null ? idPegawai.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelPegawai)) {
            return false;
        }
        TabelPegawai other = (TabelPegawai) object;
        if ((this.idPegawai == null && other.idPegawai != null) || (this.idPegawai != null && !this.idPegawai.equals(other.idPegawai))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alibatis.demo.TabelPegawai[ idPegawai=" + idPegawai + " ]";
    }
    
}
