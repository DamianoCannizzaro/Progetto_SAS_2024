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
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Shift {

    //fixme aggiornare usando variabile per oraario corretto
    private double startTime;
    private double endTime;
    private Date jobDate;
    private boolean group = false;
    private String groupName = "";

    //costruttore
    public Shift(double startTime, double endTime, Date jobDate, boolean group, String groupName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.jobDate = jobDate;
        this.group = group;
        if(group)this.groupName = groupName;
    }

    public Shift UpdateShift(Shift s, double startTime, double endTime, Date jobDate, boolean group, String groupName, double preext, double postext) {
        if(s.startTime != startTime)s.startTime = startTime;
        if(s.endTime != endTime)s.endTime = endTime;
        if(s.jobDate != jobDate)s.jobDate = jobDate;
        if(group!= s.group)s.group = group;
        if(s.groupName != groupName && group) {
            s.groupName = groupName;
        }else if (!group) groupName = " ";
        if (preext < s.startTime) s.startTime = preext;
        if (postext > s.endTime) s.endTime = postext;

        return s;
    }

//TODO aggiungere persistenza mySQL

}
