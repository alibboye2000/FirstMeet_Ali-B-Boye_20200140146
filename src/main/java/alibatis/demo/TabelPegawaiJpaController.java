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
import javax.persistence.Persistence;

/**
 *
 * @author lenovo
 */
public class TabelPegawaiJpaController implements Serializable {

    public TabelPegawaiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("alibatis_demo_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TabelPegawaiJpaController() {
    }
    

    public void create(TabelPegawai tabelPegawai) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TabelPengelolaanData tabelPengelolaanDataOrphanCheck = tabelPegawai.getTabelPengelolaanData();
        if (tabelPengelolaanDataOrphanCheck != null) {
            TabelPegawai oldTabelPegawaiOfTabelPengelolaanData = tabelPengelolaanDataOrphanCheck.getTabelPegawai();
            if (oldTabelPegawaiOfTabelPengelolaanData != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TabelPengelolaanData " + tabelPengelolaanDataOrphanCheck + " already has an item of type TabelPegawai whose tabelPengelolaanData column cannot be null. Please make another selection for the tabelPengelolaanData field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPengelolaanData tabelPengelolaanData = tabelPegawai.getTabelPengelolaanData();
            if (tabelPengelolaanData != null) {
                tabelPengelolaanData = em.getReference(tabelPengelolaanData.getClass(), tabelPengelolaanData.getIdPengelolaan());
                tabelPegawai.setTabelPengelolaanData(tabelPengelolaanData);
            }
            em.persist(tabelPegawai);
            if (tabelPengelolaanData != null) {
                tabelPengelolaanData.setTabelPegawai(tabelPegawai);
                tabelPengelolaanData = em.merge(tabelPengelolaanData);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTabelPegawai(tabelPegawai.getIdPegawai()) != null) {
                throw new PreexistingEntityException("TabelPegawai " + tabelPegawai + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TabelPegawai tabelPegawai) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPegawai persistentTabelPegawai = em.find(TabelPegawai.class, tabelPegawai.getIdPegawai());
            TabelPengelolaanData tabelPengelolaanDataOld = persistentTabelPegawai.getTabelPengelolaanData();
            TabelPengelolaanData tabelPengelolaanDataNew = tabelPegawai.getTabelPengelolaanData();
            List<String> illegalOrphanMessages = null;
            if (tabelPengelolaanDataNew != null && !tabelPengelolaanDataNew.equals(tabelPengelolaanDataOld)) {
                TabelPegawai oldTabelPegawaiOfTabelPengelolaanData = tabelPengelolaanDataNew.getTabelPegawai();
                if (oldTabelPegawaiOfTabelPengelolaanData != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TabelPengelolaanData " + tabelPengelolaanDataNew + " already has an item of type TabelPegawai whose tabelPengelolaanData column cannot be null. Please make another selection for the tabelPengelolaanData field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tabelPengelolaanDataNew != null) {
                tabelPengelolaanDataNew = em.getReference(tabelPengelolaanDataNew.getClass(), tabelPengelolaanDataNew.getIdPengelolaan());
                tabelPegawai.setTabelPengelolaanData(tabelPengelolaanDataNew);
            }
            tabelPegawai = em.merge(tabelPegawai);
            if (tabelPengelolaanDataOld != null && !tabelPengelolaanDataOld.equals(tabelPengelolaanDataNew)) {
                tabelPengelolaanDataOld.setTabelPegawai(null);
                tabelPengelolaanDataOld = em.merge(tabelPengelolaanDataOld);
            }
            if (tabelPengelolaanDataNew != null && !tabelPengelolaanDataNew.equals(tabelPengelolaanDataOld)) {
                tabelPengelolaanDataNew.setTabelPegawai(tabelPegawai);
                tabelPengelolaanDataNew = em.merge(tabelPengelolaanDataNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tabelPegawai.getIdPegawai();
                if (findTabelPegawai(id) == null) {
                    throw new NonexistentEntityException("The tabelPegawai with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPegawai tabelPegawai;
            try {
                tabelPegawai = em.getReference(TabelPegawai.class, id);
                tabelPegawai.getIdPegawai();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelPegawai with id " + id + " no longer exists.", enfe);
            }
            TabelPengelolaanData tabelPengelolaanData = tabelPegawai.getTabelPengelolaanData();
            if (tabelPengelolaanData != null) {
                tabelPengelolaanData.setTabelPegawai(null);
                tabelPengelolaanData = em.merge(tabelPengelolaanData);
            }
            em.remove(tabelPegawai);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TabelPegawai> findTabelPegawaiEntities() {
        return findTabelPegawaiEntities(true, -1, -1);
    }

    public List<TabelPegawai> findTabelPegawaiEntities(int maxResults, int firstResult) {
        return findTabelPegawaiEntities(false, maxResults, firstResult);
    }

    private List<TabelPegawai> findTabelPegawaiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TabelPegawai.class));
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

    public TabelPegawai findTabelPegawai(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TabelPegawai.class, id);
        } finally {
            em.close();
        }
    }

    public int getTabelPegawaiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TabelPegawai> rt = cq.from(TabelPegawai.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
