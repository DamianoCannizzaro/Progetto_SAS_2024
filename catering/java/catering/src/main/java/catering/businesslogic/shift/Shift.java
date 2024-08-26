package catering.businesslogic.shift;

import catering.businesslogic.CatERing;
import catering.businesslogic.recipe.Recipe;
import catering.businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Shift {

    //fixme aggiornare usando variabile per oraario corretto
    private LocalTime startTime;
    private LocalTime endTime;
    private Date jobDate;
    private Date deadline;
    private boolean group = false;
    private String groupName = "";

    //costruttore
    public Shift(LocalTime startTime, LocalTime endTime, Date jobDate, Date deadline, boolean group, String groupName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.jobDate = jobDate;
        this.deadline = deadline;
        this.group = group;
        if(group)this.groupName = groupName;
    }

    public Shift UpdateShift(Shift s, LocalTime startTime, LocalTime endTime, Date jobDate, Date deadline, boolean group, String groupName, LocalTime preExt, LocalTime postExt) {
        if(s.startTime != startTime)s.startTime = startTime;
        if(s.endTime != endTime)s.endTime = endTime;
        if(s.jobDate != jobDate)s.jobDate = jobDate;
        if(s.deadline != deadline && deadline.before(jobDate))s.deadline = deadline;
        if(group!= s.group)s.group = group;
        if(s.groupName != groupName && group) {
            s.groupName = groupName;
        }else if (!group) groupName = " ";
        if (preExt.isBefore(startTime)) s.startTime = preExt;
        if (postExt.isAfter(endTime)) s.endTime = postExt;

        return s;
    }

//TODO aggiungere persistenza mySQL

}
