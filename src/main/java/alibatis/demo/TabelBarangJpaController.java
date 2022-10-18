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
public class TabelBarangJpaController implements Serializable {

    public TabelBarangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("alibatis_demo_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TabelBarangJpaController() {
    }
    

    public void create(TabelBarang tabelBarang) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TabelTransaksi tabelTransaksiOrphanCheck = tabelBarang.getTabelTransaksi();
        if (tabelTransaksiOrphanCheck != null) {
            TabelBarang oldTabelBarangOfTabelTransaksi = tabelTransaksiOrphanCheck.getTabelBarang();
            if (oldTabelBarangOfTabelTransaksi != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TabelTransaksi " + tabelTransaksiOrphanCheck + " already has an item of type TabelBarang whose tabelTransaksi column cannot be null. Please make another selection for the tabelTransaksi field.");
            }
        }
        TabelPengelolaanData qtyOrphanCheck = tabelBarang.getQty();
        if (qtyOrphanCheck != null) {
            TabelBarang oldTabelBarangOfQty = qtyOrphanCheck.getTabelBarang();
            if (oldTabelBarangOfQty != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TabelPengelolaanData " + qtyOrphanCheck + " already has an item of type TabelBarang whose qty column cannot be null. Please make another selection for the qty field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelTransaksi tabelTransaksi = tabelBarang.getTabelTransaksi();
            if (tabelTransaksi != null) {
                tabelTransaksi = em.getReference(tabelTransaksi.getClass(), tabelTransaksi.getIdTransaksi());
                tabelBarang.setTabelTransaksi(tabelTransaksi);
            }
            TabelPengelolaanData qty = tabelBarang.getQty();
            if (qty != null) {
                qty = em.getReference(qty.getClass(), qty.getIdPengelolaan());
                tabelBarang.setQty(qty);
            }
            em.persist(tabelBarang);
            if (tabelTransaksi != null) {
                tabelTransaksi.setTabelBarang(tabelBarang);
                tabelTransaksi = em.merge(tabelTransaksi);
            }
            if (qty != null) {
                qty.setTabelBarang(tabelBarang);
                qty = em.merge(qty);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTabelBarang(tabelBarang.getKodeBarang()) != null) {
                throw new PreexistingEntityException("TabelBarang " + tabelBarang + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TabelBarang tabelBarang) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelBarang persistentTabelBarang = em.find(TabelBarang.class, tabelBarang.getKodeBarang());
            TabelTransaksi tabelTransaksiOld = persistentTabelBarang.getTabelTransaksi();
            TabelTransaksi tabelTransaksiNew = tabelBarang.getTabelTransaksi();
            TabelPengelolaanData qtyOld = persistentTabelBarang.getQty();
            TabelPengelolaanData qtyNew = tabelBarang.getQty();
            List<String> illegalOrphanMessages = null;
            if (tabelTransaksiNew != null && !tabelTransaksiNew.equals(tabelTransaksiOld)) {
                TabelBarang oldTabelBarangOfTabelTransaksi = tabelTransaksiNew.getTabelBarang();
                if (oldTabelBarangOfTabelTransaksi != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TabelTransaksi " + tabelTransaksiNew + " already has an item of type TabelBarang whose tabelTransaksi column cannot be null. Please make another selection for the tabelTransaksi field.");
                }
            }
            if (qtyNew != null && !qtyNew.equals(qtyOld)) {
                TabelBarang oldTabelBarangOfQty = qtyNew.getTabelBarang();
                if (oldTabelBarangOfQty != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TabelPengelolaanData " + qtyNew + " already has an item of type TabelBarang whose qty column cannot be null. Please make another selection for the qty field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tabelTransaksiNew != null) {
                tabelTransaksiNew = em.getReference(tabelTransaksiNew.getClass(), tabelTransaksiNew.getIdTransaksi());
                tabelBarang.setTabelTransaksi(tabelTransaksiNew);
            }
            if (qtyNew != null) {
                qtyNew = em.getReference(qtyNew.getClass(), qtyNew.getIdPengelolaan());
                tabelBarang.setQty(qtyNew);
            }
            tabelBarang = em.merge(tabelBarang);
            if (tabelTransaksiOld != null && !tabelTransaksiOld.equals(tabelTransaksiNew)) {
                tabelTransaksiOld.setTabelBarang(null);
                tabelTransaksiOld = em.merge(tabelTransaksiOld);
            }
            if (tabelTransaksiNew != null && !tabelTransaksiNew.equals(tabelTransaksiOld)) {
                tabelTransaksiNew.setTabelBarang(tabelBarang);
                tabelTransaksiNew = em.merge(tabelTransaksiNew);
            }
            if (qtyOld != null && !qtyOld.equals(qtyNew)) {
                qtyOld.setTabelBarang(null);
                qtyOld = em.merge(qtyOld);
            }
            if (qtyNew != null && !qtyNew.equals(qtyOld)) {
                qtyNew.setTabelBarang(tabelBarang);
                qtyNew = em.merge(qtyNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tabelBarang.getKodeBarang();
                if (findTabelBarang(id) == null) {
                    throw new NonexistentEntityException("The tabelBarang with id " + id + " no longer exists.");
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
            TabelBarang tabelBarang;
            try {
                tabelBarang = em.getReference(TabelBarang.class, id);
                tabelBarang.getKodeBarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelBarang with id " + id + " no longer exists.", enfe);
            }
            TabelTransaksi tabelTransaksi = tabelBarang.getTabelTransaksi();
            if (tabelTransaksi != null) {
                tabelTransaksi.setTabelBarang(null);
                tabelTransaksi = em.merge(tabelTransaksi);
            }
            TabelPengelolaanData qty = tabelBarang.getQty();
            if (qty != null) {
                qty.setTabelBarang(null);
                qty = em.merge(qty);
            }
            em.remove(tabelBarang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TabelBarang> findTabelBarangEntities() {
        return findTabelBarangEntities(true, -1, -1);
    }

    public List<TabelBarang> findTabelBarangEntities(int maxResults, int firstResult) {
        return findTabelBarangEntities(false, maxResults, firstResult);
    }

    private List<TabelBarang> findTabelBarangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TabelBarang.class));
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

    public TabelBarang findTabelBarang(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TabelBarang.class, id);
        } finally {
            em.close();
        }
    }

    public int getTabelBarangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TabelBarang> rt = cq.from(TabelBarang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
