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
public class TabelPembeliJpaController implements Serializable {

    public TabelPembeliJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TabelPembeli tabelPembeli) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelTransaksi tabelTransaksi = tabelPembeli.getTabelTransaksi();
            if (tabelTransaksi != null) {
                tabelTransaksi = em.getReference(tabelTransaksi.getClass(), tabelTransaksi.getIdTransaksi());
                tabelPembeli.setTabelTransaksi(tabelTransaksi);
            }
            em.persist(tabelPembeli);
            if (tabelTransaksi != null) {
                TabelPembeli oldIdPembeliOfTabelTransaksi = tabelTransaksi.getIdPembeli();
                if (oldIdPembeliOfTabelTransaksi != null) {
                    oldIdPembeliOfTabelTransaksi.setTabelTransaksi(null);
                    oldIdPembeliOfTabelTransaksi = em.merge(oldIdPembeliOfTabelTransaksi);
                }
                tabelTransaksi.setIdPembeli(tabelPembeli);
                tabelTransaksi = em.merge(tabelTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTabelPembeli(tabelPembeli.getIdPembeli()) != null) {
                throw new PreexistingEntityException("TabelPembeli " + tabelPembeli + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TabelPembeli tabelPembeli) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelPembeli persistentTabelPembeli = em.find(TabelPembeli.class, tabelPembeli.getIdPembeli());
            TabelTransaksi tabelTransaksiOld = persistentTabelPembeli.getTabelTransaksi();
            TabelTransaksi tabelTransaksiNew = tabelPembeli.getTabelTransaksi();
            List<String> illegalOrphanMessages = null;
            if (tabelTransaksiOld != null && !tabelTransaksiOld.equals(tabelTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TabelTransaksi " + tabelTransaksiOld + " since its idPembeli field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tabelTransaksiNew != null) {
                tabelTransaksiNew = em.getReference(tabelTransaksiNew.getClass(), tabelTransaksiNew.getIdTransaksi());
                tabelPembeli.setTabelTransaksi(tabelTransaksiNew);
            }
            tabelPembeli = em.merge(tabelPembeli);
            if (tabelTransaksiNew != null && !tabelTransaksiNew.equals(tabelTransaksiOld)) {
                TabelPembeli oldIdPembeliOfTabelTransaksi = tabelTransaksiNew.getIdPembeli();
                if (oldIdPembeliOfTabelTransaksi != null) {
                    oldIdPembeliOfTabelTransaksi.setTabelTransaksi(null);
                    oldIdPembeliOfTabelTransaksi = em.merge(oldIdPembeliOfTabelTransaksi);
                }
                tabelTransaksiNew.setIdPembeli(tabelPembeli);
                tabelTransaksiNew = em.merge(tabelTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tabelPembeli.getIdPembeli();
                if (findTabelPembeli(id) == null) {
                    throw new NonexistentEntityException("The tabelPembeli with id " + id + " no longer exists.");
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
            TabelPembeli tabelPembeli;
            try {
                tabelPembeli = em.getReference(TabelPembeli.class, id);
                tabelPembeli.getIdPembeli();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelPembeli with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TabelTransaksi tabelTransaksiOrphanCheck = tabelPembeli.getTabelTransaksi();
            if (tabelTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TabelPembeli (" + tabelPembeli + ") cannot be destroyed since the TabelTransaksi " + tabelTransaksiOrphanCheck + " in its tabelTransaksi field has a non-nullable idPembeli field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tabelPembeli);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TabelPembeli> findTabelPembeliEntities() {
        return findTabelPembeliEntities(true, -1, -1);
    }

    public List<TabelPembeli> findTabelPembeliEntities(int maxResults, int firstResult) {
        return findTabelPembeliEntities(false, maxResults, firstResult);
    }

    private List<TabelPembeli> findTabelPembeliEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TabelPembeli.class));
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

    public TabelPembeli findTabelPembeli(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TabelPembeli.class, id);
        } finally {
            em.close();
        }
    }

    public int getTabelPembeliCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TabelPembeli> rt = cq.from(TabelPembeli.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
