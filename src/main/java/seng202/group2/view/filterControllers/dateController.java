package seng202.group2.view.filterControllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import seng202.group2.model.Filter;
import seng202.group2.model.FilterType;
import seng202.group2.model.datacategories.DataCategory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Controls the filter inputs for date values
 * @author Moses Wescombe
 * @author Sam Clark
 */
public class dateController extends OptionsController{
    //Inputs
    @FXML
    private DatePicker initialDate;
    @FXML
    private DatePicker finalDate;

    @Override
    public List<Filter> generateFilter(DataCategory category) {
        List<Filter> results = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Turn initial date into seconds
        LocalDate date1 = initialDate.getValue();
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date1.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long secondsDate1 = cal.getTimeInMillis() / 1000L;

        //Turn final date into seconds
        LocalDate date2 = finalDate.getValue();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal2.setTime(sdf.parse(date2.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long secondsDate2 = cal2.getTimeInMillis() / 1000L;

        //Add a filter that contains both ends
        results.add(FilterType.RANGE.createFilter(category, "BETWEEN " + secondsDate1 + " AND " + secondsDate2));

        return results;
    }

    @Override
    public void initialize() {}
}
