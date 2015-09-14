package com.jvege.model;

import com.jvege.entity.RecursiveReference;
import com.jvege.entity.Reminder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Dickson
 */
public class ReminderManager {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CalendarPU");
    private static Logger logger = Logger.getLogger(ReminderManager.class.getName());

    public List<Reminder> getAllReminder() {
        EntityManager em = emf.createEntityManager();
        List<Reminder> reminderList = new ArrayList<Reminder>();
        try {
            Query q = em.createNamedQuery("Reminder.findAll");
            if (!q.getResultList().isEmpty()) {
                reminderList = q.getResultList();
            }
            em.close();
        } catch (Exception e) {
            logger.debug("getAllReminder Fail : " + e.toString());
        } finally {
            if (em.isOpen()) {
                em.close();
                em = null;
            }
        }
        return reminderList;
    }

    /**
     * Add new Reminder into database
     *
     * @param Reminder reminder
     */
    public void addReminder(Reminder reminder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Reminder rem = new Reminder();
            rem.setId(getIncrementId("Reminder"));
            rem.setDate(reminder.getDate());
            rem.setTime(reminder.getTime());
            rem.setTask(reminder.getTask());
            em.persist(rem);
            em.getTransaction().commit();
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("addReminder Fail : " + e.toString());
        } finally {
            if (em.isOpen()) {
                em.close();
                em = null;
                logger.debug("addReminder Fail To Close EntityManager");
            }
        }
    }

    /**
     * Get reminder based on date time
     *
     * @param reminder
     * @return Reminder object
     */
    public Reminder getReminderByDateTime(Reminder reminder) {
        EntityManager em = emf.createEntityManager();
        Reminder rem = null;
        try {
            Query q = em.createNamedQuery("Reminder.findByDateTime");
            q.setParameter("reminderDate", reminder.getDate());
            q.setParameter("reminderTime", reminder.getTime());
            if (!q.getResultList().isEmpty()) {
                rem = (Reminder) q.getSingleResult();
            }
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("getReminderByDateTime Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("getReminderByDateTime Fail To Close EntityManager");
            }
        }
        return rem;
    }

    /**
     * Get reminder based on date
     *
     * @param reminder
     * @return Reminder object
     */
    public List<Reminder> getReminderByDate(Date date) {
        EntityManager em = emf.createEntityManager();
        List<Reminder> dayReminderList = null;
        try {
            Query q = em.createNamedQuery("Reminder.findByDate");
            q.setParameter("reminderDate", date);
            if (!q.getResultList().isEmpty()) {
                dayReminderList = q.getResultList();
            }
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("getReminderByDate Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("getReminderByDate Fail To Close EntityManager");
            }
        }
        return dayReminderList;
    }

    /**
     * Add Reminder into database, or Update if Reminder already exist based on
     * Date and Time
     *
     * @param Reminder reminder
     */
    public void addOrUpdateReminder(Reminder reminder) {
        EntityManager em = emf.createEntityManager();
        Reminder rem;
        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Reminder.findByDateTime");
            q.setParameter("reminderDate", reminder.getDate());
            q.setParameter("reminderTime", reminder.getTime());
            if (!q.getResultList().isEmpty()) {
                rem = (Reminder) q.getSingleResult();
                rem.setTask(reminder.getTask());
                rem.setSnoozeTime(reminder.getSnoozeTime());
                rem.setStatus(reminder.getStatus());
            } else {
                rem = new Reminder();
                rem.setId(getIncrementId("Reminder"));
                rem.setDate(reminder.getDate());
                rem.setTime(reminder.getTime());
                rem.setTask(reminder.getTask());
                rem.setStatus(reminder.getStatus());
                //rem.setSnoozeTime(reminder.getSnoozeTime()); // since that will never add snooze time at the first place
            }
            em.persist(rem);
            em.getTransaction().commit();
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("addOrUpdateReminder Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("addOrUpdateReminder Fail To Close EntityManager");
            }
        }
    }

    public void deleteReminder(Reminder reminder) {
        EntityManager em = emf.createEntityManager();
        Reminder rem;
        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Reminder.findByDateTime");
            q.setParameter("reminderDate", reminder.getDate());
            q.setParameter("reminderTime", reminder.getTime());
            rem = (Reminder) q.getSingleResult();
            em.remove(rem);

            if (rem.getRecursiveId() != null) {
                Query recursiveQuery = em.createNamedQuery("RecursiveReference.findById");
                recursiveQuery.setParameter("id", rem.getRecursiveId());
                RecursiveReference rr = (RecursiveReference) recursiveQuery.getSingleResult();
                em.remove(rr);
            }

            em.getTransaction().commit();
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("deleteReminder Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("deleteReminder Fail To Close EntityManager");
            }
        }
    }

    /**
     * Get Monthly Reminder
     *
     * @param year
     * @param month
     * @return List of Reminder
     */
    public static List<Reminder> getMonthReminder(int year, int month) {
        EntityManager em = emf.createEntityManager();

        try {
            String sql;
            month = month + 1; //month in java calendar system will be 0 for January, 11 for December
            if (month == 12) { //December
                sql = "SELECT * from REMINDER "
                        + "WHERE REMINDER_DATE  >= '" + year + "-" + month + "-01' "
                        + "AND REMINDER_DATE <= '" + year + "-" + (month) + "-31'  ORDER BY REMINDER_DATE";
            } else {
                sql = "SELECT * from REMINDER "
                        + "WHERE REMINDER_DATE  >= '" + year + "-" + month + "-01' "
                        + "AND REMINDER_DATE < '" + year + "-" + (month + 1) + "-01'  ORDER BY REMINDER_DATE";
            }
            Query q = em.createNativeQuery(
                    sql, Reminder.class);
            List<Reminder> list = q.getResultList();
            em.close();
            em = null;
            return list;
        } catch (Exception e) {
            logger.debug("getMonthReminder Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("getMonthReminder Fail To Close EntityManager");
            }
        }
        return null;
    }

    /**
     * Get ID number from Reminder Database
     *
     * @return ID
     */
    private long getIncrementId(String tableName) {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("Select r.id from " + tableName + " r order by r.id desc");
        q.setMaxResults(1);
        Object currentID = q.getSingleResult();
        em.close();
        em = null;
        return (Long) currentID + 1;
    }

    /**
     * Get RecursiveReference from database
     *
     * @param id
     * @return RecursiveReference
     */
    public RecursiveReference getRecursive(long id) {
        EntityManager em = emf.createEntityManager();
        RecursiveReference rec = null;
        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("RecursiveReference.findById");
            q.setParameter("id", id);
            rec = (RecursiveReference) q.getSingleResult();
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("getRecursive Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("getRecursive Fail To Close EntityManager");
            }
        }
        return rec;
    }

    /**
     * addOrUpdateRecursive
     *
     * @param Reminder
     * @param RecursiveReference
     */
    public void addOrUpdateRecursive(Reminder reminder, RecursiveReference recRef) {
        EntityManager em = emf.createEntityManager();
        RecursiveReference recursive;
        try {
            em.getTransaction().begin();
            if (reminder.getRecursiveId() != null) {
                Query q = em.createNamedQuery("RecursiveReference.findById");
                q.setParameter("id", reminder.getRecursiveId());
                recursive = (RecursiveReference) q.getSingleResult();
                recursive.setMonthly(recRef.getMonthly());
                recursive.setMonday(recRef.getMonday());
                recursive.setTuesday(recRef.getTuesday());
                recursive.setWednesday(recRef.getWednesday());
                recursive.setThursday(recRef.getThursday());
                recursive.setFriday(recRef.getFriday());
                recursive.setSaturday(recRef.getSaturday());
                recursive.setSunday(recRef.getSunday());
            } else {
                recursive = new RecursiveReference();
                //recursive.setId(getIncrementId("RecursiveReference"));
                recursive.setId(reminder.getId()); // same as reminder.id
                recursive.setMonthly(recRef.getMonthly());
                recursive.setMonday(recRef.getMonday());
                recursive.setTuesday(recRef.getTuesday());
                recursive.setWednesday(recRef.getWednesday());
                recursive.setThursday(recRef.getThursday());
                recursive.setFriday(recRef.getFriday());
                recursive.setSaturday(recRef.getSaturday());
                recursive.setSunday(recRef.getSunday());

                Query q = em.createNamedQuery("Reminder.findByDateTime");
                q.setParameter("reminderDate", reminder.getDate());
                q.setParameter("reminderTime", reminder.getTime());
                Reminder rem = (Reminder) q.getSingleResult();
                rem.setRecursiveId(recursive.getId()); // update reminder recursive id
                em.persist(rem);
            }
            em.persist(recursive);
            em.getTransaction().commit();
            em.close();
            em = null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.debug("addOrUpdateRecursive Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("addOrUpdateReminder Fail To Close EntityManager");
            }
        }
    }

    /**
     * Delete RecursiveReference
     *
     * @param id
     */
    public void deleteRecursive(Reminder reminder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("RecursiveReference.findById");
            q.setParameter("id", reminder.getRecursiveId());
            RecursiveReference rr = (RecursiveReference) q.getSingleResult();
            em.remove(rr);

            Query reminderQuery = em.createNamedQuery("Reminder.findByDateTime");
            reminderQuery.setParameter("reminderDate", reminder.getDate());
            reminderQuery.setParameter("reminderTime", reminder.getTime());
            Reminder rem = (Reminder) reminderQuery.getSingleResult();
            rem.setRecursiveId(null); // update reminder recursive id

            em.getTransaction().commit();
            em.close();
            em = null;
        } catch (Exception e) {
            logger.debug("deleteRecursive Fail : " + e.toString());
        } finally {
            if (em != null) {
                em.close();
                em = null;
                logger.debug("deleteRecursive Fail To Close EntityManager");
            }
        }
    }
}
