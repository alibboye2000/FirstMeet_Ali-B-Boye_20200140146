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
public class TabelTransaksiJpaController implements Serializable {

    public TabelTransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("alibatis_demo_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TabelTransaksiJpaController() {
    }
    

    public void create(TabelTransaksi tabelTransaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TabelPembeli idPembeliOrphanCheck = tabelTransaksi.getIdPembeli();
        if (idPembeliOrphanCheck != null) {
            TabelTransaksi oldTabelTransaksiOfIdPembeli = idPembeliOrphanCheck.getTabelTransaksi();
            if (oldTabelTransaksiOfIdPembeli != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TabelPembeli " + idPembeliOrphanCheck + " already has an item of type TabelTransaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelBarang tabelBarang = tabelTransaksi.getTabelBarang();
            if (tabelBarang != null) {
                tabelBarang = em.getReference(tabelBarang.getClass(), tabelBarang.getKodeBarang());
                tabelTransaksi.setTabelBarang(tabelBarang);
            }
            TabelPembeli idPembeli = tabelTransaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli = em.getReference(idPembeli.getClass(), idPembeli.getIdPembeli());
                tabelTransaksi.setIdPembeli(idPembeli);
            }
            em.persist(tabelTransaksi);
            if (tabelBarang != null) {
                TabelTransaksi oldTabelTransaksiOfTabelBarang = tabelBarang.getTabelTransaksi();
                if (oldTabelTransaksiOfTabelBarang != null) {
                    oldTabelTransaksiOfTabelBarang.setTabelBarang(null);
                    oldTabelTransaksiOfTabelBarang = em.merge(oldTabelTransaksiOfTabelBarang);
                }
                tabelBarang.setTabelTransaksi(tabelTransaksi);
                tabelBarang = em.merge(tabelBarang);
            }
            if (idPembeli != null) {
                idPembeli.setTabelTransaksi(tabelTransaksi);
                idPembeli = em.merge(idPembeli);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTabelTransaksi(tabelTransaksi.getIdTransaksi()) != null) {
                throw new PreexistingEntityException("TabelTransaksi " + tabelTransaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TabelTransaksi tabelTransaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TabelTransaksi persistentTabelTransaksi = em.find(TabelTransaksi.class, tabelTransaksi.getIdTransaksi());
            TabelBarang tabelBarangOld = persistentTabelTransaksi.getTabelBarang();
            TabelBarang tabelBarangNew = tabelTransaksi.getTabelBarang();
            TabelPembeli idPembeliOld = persistentTabelTransaksi.getIdPembeli();
            TabelPembeli idPembeliNew = tabelTransaksi.getIdPembeli();
            List<String> illegalOrphanMessages = null;
            if (tabelBarangOld != null && !tabelBarangOld.equals(tabelBarangNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TabelBarang " + tabelBarangOld + " since its tabelTransaksi field is not nullable.");
            }
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                TabelTransaksi oldTabelTransaksiOfIdPembeli = idPembeliNew.getTabelTransaksi();
                if (oldTabelTransaksiOfIdPembeli != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TabelPembeli " + idPembeliNew + " already has an item of type TabelTransaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tabelBarangNew != null) {
                tabelBarangNew = em.getReference(tabelBarangNew.getClass(), tabelBarangNew.getKodeBarang());
                tabelTransaksi.setTabelBarang(tabelBarangNew);
            }
            if (idPembeliNew != null) {
                idPembeliNew = em.getReference(idPembeliNew.getClass(), idPembeliNew.getIdPembeli());
                tabelTransaksi.setIdPembeli(idPembeliNew);
            }
            tabelTransaksi = em.merge(tabelTransaksi);
            if (tabelBarangNew != null && !tabelBarangNew.equals(tabelBarangOld)) {
                TabelTransaksi oldTabelTransaksiOfTabelBarang = tabelBarangNew.getTabelTransaksi();
                if (oldTabelTransaksiOfTabelBarang != null) {
                    oldTabelTransaksiOfTabelBarang.setTabelBarang(null);
                    oldTabelTransaksiOfTabelBarang = em.merge(oldTabelTransaksiOfTabelBarang);
                }
                tabelBarangNew.setTabelTransaksi(tabelTransaksi);
                tabelBarangNew = em.merge(tabelBarangNew);
            }
            if (idPembeliOld != null && !idPembeliOld.equals(idPembeliNew)) {
                idPembeliOld.setTabelTransaksi(null);
                idPembeliOld = em.merge(idPembeliOld);
            }
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                idPembeliNew.setTabelTransaksi(tabelTransaksi);
                idPembeliNew = em.merge(idPembeliNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tabelTransaksi.getIdTransaksi();
                if (findTabelTransaksi(id) == null) {
                    throw new NonexistentEntityException("The tabelTransaksi with id " + id + " no longer exists.");
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
            TabelTransaksi tabelTransaksi;
            try {
                tabelTransaksi = em.getReference(TabelTransaksi.class, id);
                tabelTransaksi.getIdTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tabelTransaksi with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TabelBarang tabelBarangOrphanCheck = tabelTransaksi.getTabelBarang();
            if (tabelBarangOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TabelTransaksi (" + tabelTransaksi + ") cannot be destroyed since the TabelBarang " + tabelBarangOrphanCheck + " in its tabelBarang field has a non-nullable tabelTransaksi field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TabelPembeli idPembeli = tabelTransaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli.setTabelTransaksi(null);
                idPembeli = em.merge(idPembeli);
            }
            em.remove(tabelTransaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TabelTransaksi> findTabelTransaksiEntities() {
        return findTabelTransaksiEntities(true, -1, -1);
    }

    public List<TabelTransaksi> findTabelTransaksiEntities(int maxResults, int firstResult) {
        return findTabelTransaksiEntities(false, maxResults, firstResult);
    }

    private List<TabelTransaksi> findTabelTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TabelTransaksi.class));
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

    public TabelTransaksi findTabelTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TabelTransaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getTabelTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TabelTransaksi> rt = cq.from(TabelTransaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
