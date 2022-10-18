/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alibatis.demo;

import alibatis.demo.exceptions.IllegalOrphanException;
import alibatis.demo.exceptions.NonexistentEntityException;
import alibatis.demo.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class TabelPengelolaanDataJpaController implements Serializable {

    public TabelPengelolaanDataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TabelPengelolaanData tabelPengelolaanData) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPegawai tabelPegawai = tabelPengelolaanData.getTabelPegawai();
            if (tabelPegawai != null) {
                tabelPegawai = em.getReference(tabelPegawai.getClass(), tabelPegawai.getIdPegawai());
                tabelPengelolaanData.setTabelPegawai(tabelPegawai);
            }
            TabelBarang tabelBarang = tabelPengelolaanData.getTabelBarang();
            if (tabelBarang != null) {
                tabelBarang = em.getReference(tabelBarang.getClass(), tabelBarang.getKodeBarang());
                tabelPengelolaanData.setTabelBarang(tabelBarang);
            }
            em.persist(tabelPengelolaanData);
            if (tabelPegawai != null) {
                TabelPengelolaanData oldTabelPengelolaanDataOfTabelPegawai = tabelPegawai.getTabelPengelolaanData();
                if (oldTabelPengelolaanDataOfTabelPegawai != null) {
                    oldTabelPengelolaanDataOfTabelPegawai.setTabelPegawai(null);
                    oldTabelPengelolaanDataOfTabelPegawai = em.merge(oldTabelPengelolaanDataOfTabelPegawai);
                }
                tabelPegawai.setTabelPengelolaanData(tabelPengelolaanData);
                tabelPegawai = em.merge(tabelPegawai);
            }
            if (tabelBarang != null) {
                TabelPengelolaanData oldQtyOfTabelBarang = tabelBarang.getQty();
                if (oldQtyOfTabelBarang != null) {
                    oldQtyOfTabelBarang.setTabelBarang(null);
                    oldQtyOfTabelBarang = em.merge(oldQtyOfTabelBarang);
                }
                tabelBarang.setQty(tabelPengelolaanData);
                tabelBarang = em.merge(tabelBarang);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTabelPengelolaanData(tabelPengelolaanData.getIdPengelolaan()) != null) {
                throw new PreexistingEntityException("TabelPengelolaanData " + tabelPengelolaanData + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TabelPengelolaanData tabelPengelolaanData) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPengelolaanData persistentTabelPengelolaanData = em.find(TabelPengelolaanData.class, tabelPengelolaanData.getIdPengelolaan());
            TabelPegawai tabelPegawaiOld = persistentTabelPengelolaanData.getTabelPegawai();
            TabelPegawai tabelPegawaiNew = tabelPengelolaanData.getTabelPegawai();
            TabelBarang tabelBarangOld = persistentTabelPengelolaanData.getTabelBarang();
            TabelBarang tabelBarangNew = tabelPengelolaanData.getTabelBarang();
            List<String> illegalOrphanMessages = null;
            if (tabelPegawaiOld != null && !tabelPegawaiOld.equals(tabelPegawaiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TabelPegawai " + tabelPegawaiOld + " since its tabelPengelolaanData field is not nullable.");
            }
            if (tabelBarangOld != null && !tabelBarangOld.equals(tabelBarangNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TabelBarang " + tabelBarangOld + " since its qty field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tabelPegawaiNew != null) {
                tabelPegawaiNew = em.getReference(tabelPegawaiNew.getClass(), tabelPegawaiNew.getIdPegawai());
                tabelPengelolaanData.setTabelPegawai(tabelPegawaiNew);
            }
            if (tabelBarangNew != null) {
                tabelBarangNew = em.getReference(tabelBarangNew.getClass(), tabelBarangNew.getKodeBarang());
                tabelPengelolaanData.setTabelBarang(tabelBarangNew);
            }
            tabelPengelolaanData = em.merge(tabelPengelolaanData);
            if (tabelPegawaiNew != null && !tabelPegawaiNew.equals(tabelPegawaiOld)) {
                TabelPengelolaanData oldTabelPengelolaanDataOfTabelPegawai = tabelPegawaiNew.getTabelPengelolaanData();
                if (oldTabelPengelolaanDataOfTabelPegawai != null) {
                    oldTabelPengelolaanDataOfTabelPegawai.setTabelPegawai(null);
                    oldTabelPengelolaanDataOfTabelPegawai = em.merge(oldTabelPengelolaanDataOfTabelPegawai);
                }
                tabelPegawaiNew.setTabelPengelolaanData(tabelPengelolaanData);
                tabelPegawaiNew = em.merge(tabelPegawaiNew);
            }
            if (tabelBarangNew != null && !tabelBarangNew.equals(tabelBarangOld)) {
                TabelPengelolaanData oldQtyOfTabelBarang = tabelBarangNew.getQty();
                if (oldQtyOfTabelBarang != null) {
                    oldQtyOfTabelBarang.setTabelBarang(null);
                    oldQtyOfTabelBarang = em.merge(oldQtyOfTabelBarang);
                }
                tabelBarangNew.setQty(tabelPengelolaanData);
                tabelBarangNew = em.merge(tabelBarangNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tabelPengelolaanData.getIdPengelolaan();
                if (findTabelPengelolaanData(id) == null) {
                    throw new NonexistentEntityException("The tabelPengelolaanData with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPengelolaanData tabelPengelolaanData;
            try {
                tabelPengelolaanData = em.getReference(TabelPengelolaanData.class, id);
                tabelPengelolaanData.getIdPengelolaan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelPengelolaanData with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TabelPegawai tabelPegawaiOrphanCheck = tabelPengelolaanData.getTabelPegawai();
            if (tabelPegawaiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TabelPengelolaanData (" + tabelPengelolaanData + ") cannot be destroyed since the TabelPegawai " + tabelPegawaiOrphanCheck + " in its tabelPegawai field has a non-nullable tabelPengelolaanData field.");
            }
            TabelBarang tabelBarangOrphanCheck = tabelPengelolaanData.getTabelBarang();
            if (tabelBarangOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TabelPengelolaanData (" + tabelPengelolaanData + ") cannot be destroyed since the TabelBarang " + tabelBarangOrphanCheck + " in its tabelBarang field has a non-nullable qty field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tabelPengelolaanData);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TabelPengelolaanData> findTabelPengelolaanDataEntities() {
        return findTabelPengelolaanDataEntities(true, -1, -1);
    }

    public List<TabelPengelolaanData> findTabelPengelolaanDataEntities(int maxResults, int firstResult) {
        return findTabelPengelolaanDataEntities(false, maxResults, firstResult);
    }

    private List<TabelPengelolaanData> findTabelPengelolaanDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TabelPengelolaanData.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TabelPengelolaanData findTabelPengelolaanData(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TabelPengelolaanData.class, id);
        } finally {
            em.close();
        }
    }

    public int getTabelPengelolaanDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TabelPengelolaanData> rt = cq.from(TabelPengelolaanData.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
