package graph;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * LineChart used to display trial data from DataModel
 */
public class GraphView extends LineChart {

    /**
     * x-axis
     */
    NumberAxis xAxis;

    /**
     * y-axis
     */
    NumberAxis yAxis;

    /**
     * data model
     */
    DataModel model;

    /**
     * Initialize graph with data model
     * @param model data model to read from
     */
    public GraphView(DataModel model) {
        this(new NumberAxis(), new NumberAxis(), model);
    }

    /**
     * Initialize graph with axes and data model
     * @param xAxis x-axis
     * @param yAxis y-axis
     * @param model data model
     */
    public GraphView(NumberAxis xAxis, NumberAxis yAxis, DataModel model) {
        super(xAxis, yAxis);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.model = model;

        this.xAxis.setAutoRanging(false);
        resetBounds();
    }

    /**
     * read entry from data model with specified key
     * @param key key to read from
     * @return Series of trial result
     */
    Series<Integer, Integer> seriesFromData(int key) {
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        series.setName("size " + key);
        for (Trial trial : model.getData().get(key)) {
            series.getData().add(new XYChart.Data(trial.getLimit(), trial.getTime()));
        }
        return series;
    }

    /**
     * reset x/y bounds
     */
    void resetBounds() {
        this.xAxis.setLowerBound(0);
        this.xAxis.setUpperBound(300);
    }

    /**
     * display graph with all data entries
     */
    public void update() {
        resetBounds();
        for (int key : model.getData().keySet()) {
            this.getData().add(seriesFromData(key));
        }
    }

    /**
     * display graph of specific entry
     * @param key key to read from
     */
    public void update(int key) {
        resetBounds();
        this.getData().add(seriesFromData(key));
    }

    /**
     * display graph of all entries within specific x-bounds
     * @param lower lower bound
     * @param upper upper bound
     */
    public void update(int lower, int upper){
        this.xAxis.setLowerBound(lower);
        this.xAxis.setUpperBound(upper);
        for (int key : model.getData().keySet()) {
            this.getData().add(seriesFromData(key));
        }
    }

    /**
     * display graph of specific entry within specific x-bounds
     * @param key key to entry
     * @param lower lower bound
     * @param upper upper bound
     */
    public void update(int key, int lower, int upper) {
        this.xAxis.setLowerBound(lower);
        this.xAxis.setUpperBound(upper);
        this.getData().add(seriesFromData(key));
    }

    /**
     * remove all data from graph
     */
    public void clearGraph() {
        this.getData().removeAll(this.getData());
    }
}
