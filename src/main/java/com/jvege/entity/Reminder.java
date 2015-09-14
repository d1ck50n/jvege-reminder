package com.jvege.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dickson
 */
@Entity
@Table(name = "REMINDER")
@NamedQueries({
               @NamedQuery(name = "Reminder.findAll", query = "SELECT r FROM Reminder r"),
               @NamedQuery(name = "Reminder.findById",query = "SELECT r FROM Reminder r WHERE r.id = :id"),
               @NamedQuery(name = "Reminder.findByDate", query = "SELECT r FROM Reminder r WHERE r.reminderDate = :reminderDate"),
               @NamedQuery(name = "Reminder.findByTime", query = "SELECT r FROM Reminder r WHERE r.reminderTime = :reminderTime"),
               @NamedQuery(name = "Reminder.findByTask", query = "SELECT r FROM Reminder r WHERE r.task = :task"),
               @NamedQuery(name = "Reminder.findByDateTime", query = "SELECT r FROM Reminder r WHERE r.reminderDate = :reminderDate AND r.reminderTime = :reminderTime")})
 
public class Reminder implements Serializable {
    private static final long serialVersionUID = 1L;    
    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "REMINDER_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reminderDate;
    @Basic(optional = false)
    @Column(name = "REMINDER_TIME", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date reminderTime;
    @Basic(optional = false)
    @Column(name = "TASK", nullable = false, length = 255)
    private String task;
    @Column(name = "SNOOZE_TIME")
    @Temporal(TemporalType.TIME)
    private Date snoozeTime;
    @Column(name = "STATUS", length = 7)
    private String status;
    @Column(name = "RECURSIVE_ID")
    private Long recursiveId;

    public Reminder() {
    }

    public Reminder(Long id) {
        this.id = id;
    }

    public Reminder(Long id, Date date, Date time, String task, Date snoozeTime, String status, long recursiveId) {
        this.id = id;
        this.reminderDate = date;
        this.reminderTime = time;
        this.task = task;
        this.snoozeTime = snoozeTime;
        this.status = status;
        this.recursiveId = recursiveId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return reminderDate;
    }

    public void setDate(Date date) {
        this.reminderDate = date;
    }

    public Date getTime() {
        return reminderTime;
    }

    public void setTime(Date time) {
        this.reminderTime = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getSnoozeTime() {
        return snoozeTime;
    }

    public void setSnoozeTime(Date snoozeTime) {
        this.snoozeTime = snoozeTime;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

   public Long getRecursiveId() {
        return recursiveId;
    }

    public void setRecursiveId(Long recursiveId) {
        this.recursiveId = recursiveId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reminder)) {
            return false;
        }
        Reminder other = (Reminder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reminder[id=" + id + "], " +
                "[date=" + reminderDate + "], " +
                "[time=" + reminderTime + "], " +
                "[task=" + task + "], " +
                "[snoozeTime=" + snoozeTime + "], " +
                "[status=" + status + "], " +
                "[recursive=" + recursiveId + "]";
    }
}

