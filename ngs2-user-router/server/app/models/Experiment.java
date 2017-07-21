package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by anuradha_uduwage.
 */
@Entity
public class Experiment extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String experimentName;

    @Column(name = "experiment_url_actual", length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String actualURL;

    @Column(name = "experiment_url_short", length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String shortenURL;

    @Column(name = "n_participants")
    public int numberOfParticipants;

    @Column(name = "priority")
    public int priority;

    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String status;

    @Constraints.Required
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp createdTime;

    public static Finder<Long, Experiment> find = new Finder<Long, Experiment>(Experiment.class);

    /**
     * Get list of experiments by the priority.
     * @param priority
     * @return
     */
    public static List<Experiment> findExperimentsByPriority(int priority) {
        return find.where()
                .eq("priority", priority).findList();
    }

    /**
     * Get list of experiments by status.
     * @param status
     * @return
     */
    public static List<Experiment> findExperimentsByStatus(String status) {
        return find.where()
                .eq("status", status.toUpperCase()).findList();
    }

    /**
     * Get list of experiments by status and priority.
     * @param status status of the experiment
     * @param priority priority number of the experiment.
     * @return
     */
    public static List<Experiment> findExperimentsByStatusAndPriority(String status, int priority) {
        return find.where()
                .eq("status", status.toUpperCase())
                .eq("priority", priority).findList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getActualURL() {
        return actualURL;
    }

    public void setActualURL(String actualURL) {
        this.actualURL = actualURL;
    }

    public String getShortenURL() {
        return shortenURL;
    }

    public void setShortenURL(String shortenURL) {
        this.shortenURL = shortenURL;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
