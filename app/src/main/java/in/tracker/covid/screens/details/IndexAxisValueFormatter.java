package in.tracker.covid.screens.details;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class IndexAxisValueFormatter extends ValueFormatter {
    private String[] mValues = new String[]{};
    private int mValueCount = 0;

    /**
     * An empty constructor.
     * Use `setValues` to set the axis labels.
     */
    public IndexAxisValueFormatter() {
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public IndexAxisValueFormatter(List<String> values) {
        if (!values.isEmpty()) {
            Object[] objArr = values.toArray();
            setValues(Arrays
                    .copyOf(objArr, objArr.length, String[].class));
        }
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public IndexAxisValueFormatter(Collection<String> values) {
        if (values != null)
            setValues(values.toArray(new String[values.size()]));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axisBase) {
        int index = Math.round(value);

        if (index < 0 || index >= mValueCount || index != (int) value)
            return "";
        Log.i("Sanoop", "Inside formatter ! " + mValues[index]);
        return mValues[index];
    }

    public String[] getValues() {
        return mValues;
    }

    public void setValues(String[] values) {
        if (values == null)
            values = new String[]{};

        this.mValues = values;
        this.mValueCount = values.length;
    }
}