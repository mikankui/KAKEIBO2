package com.mikankui.hanasite.kakeibo.graph;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.mikankui.hanasite.kakeibo.GraphData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

public class LineBarGraphActivity extends Activity {

	ArrayList<GraphData> GRAPH_DATA = new ArrayList<GraphData>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		GRAPH_DATA = (ArrayList<GraphData>) intent.getSerializableExtra("data");

		LinearLayout layout = new LinearLayout(this);
		setContentView(layout);

		GraphicalView graphView = TimeChartView();

		layout.addView(graphView);
	}

	private GraphicalView TimeChartView() {
		// (1)???????????????????????????

		Date[] xDateValue = new Date[GRAPH_DATA.size()];
		int[] yDoubleValue1 = new int[GRAPH_DATA.size()];
		;
		int i = 0;
		int maxCost = 0;
		Date dateMin = new Date(Long.MAX_VALUE);
		Date dateMax = new Date(Long.MIN_VALUE);
		for (GraphData g : GRAPH_DATA) {
			xDateValue[i] = g.getX();
			yDoubleValue1[i] = g.getY();
			if (maxCost <= yDoubleValue1[i])
				maxCost = yDoubleValue1[i];
			if (dateMin.after(xDateValue[i]))
				dateMin = xDateValue[i];
			if (dateMax.before(xDateValue[i]))
				dateMax = xDateValue[i];
			i++;
		}

		// (2) ???????????????????????????X???Y??????????????????????????????
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setChartTitle("?????????"); // ?????????????????????
		renderer.setXTitle("??????"); // X???????????????
		renderer.setYTitle("??????"); // Y???????????????
		renderer.setAxisTitleTextSize(50); //
		renderer.setChartTitleTextSize(50); //
		renderer.setLabelsTextSize(50);
		renderer.setAxisTitleTextSize(50);
		renderer.setLegendTextSize(50); // ??????????????????????????????
		renderer.setPointSize(10f); // ?????????????????????????????????
		renderer.setXAxisMin(dateMin.getTime()); // X????????????
		renderer.setXAxisMax(dateMax.getTime()); // X????????????
		renderer.setYAxisMin(0.0); // Y????????????
		renderer.setYAxisMax(maxCost); // Y????????????
		renderer.setXLabels(20);
		renderer.setYLabels(20);
		renderer.setXLabelsAlign(Align.CENTER); // X????????????????????????
		renderer.setYLabelsAlign(Align.RIGHT); // Y????????????????????????
		renderer.setAxesColor(Color.LTGRAY); // X??????Y????????????
		renderer.setLabelsColor(Color.parseColor("#191970")); // ??????????????????

		// X????????????????????????????????????????????????
		renderer.setXLabels(0); // X????????????????????????????????????0?????????
		makeCustomXLabel(renderer, dateMin, dateMax, 1); // 3???????????????????????????
		// ????????????X????????????????????????????????????????????????????????????
		renderer.setShowCustomTextGrid(true);

		renderer.setYLabels(5); // Y?????????????????????????????????
		renderer.setLabelsTextSize(15); // ??????????????????
		// ??????????????????
		renderer.setShowGrid(true);
		// ???????????????
		renderer.setGridColor(Color.BLACK); // ?????????????????????aqua
		// ????????????????????????????????? top, left, bottom, right
		renderer.setMargins(new int[] { 50, 80, 15, 50 }); //
		renderer.setMarginsColor(Color.parseColor("#F0F8FF"));

		// (3) ?????????????????????????????????????????????
		XYSeriesRenderer r1 = new XYSeriesRenderer();
		r1.setColor(Color.BLUE);
		r1.setPointStyle(PointStyle.CIRCLE);
		r1.setFillPoints(true);
		renderer.addSeriesRenderer(r1);
		// XYSeriesRenderer r2 = new XYSeriesRenderer();
		// r2.setColor(Color.MAGENTA);
		// r2.setPointStyle(PointStyle.DIAMOND);
		// r2.setFillPoints(true);
		// renderer.addSeriesRenderer(r2);

		// (4) ????????????????????????????????????
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		TimeSeries series1 = new TimeSeries("??????"); // ?????????????????????
		// TimeSeries series2 = new TimeSeries("??????????????????"); //
		// ?????????????????????
		for (int j = 0; j < GRAPH_DATA.size(); j++) {
			series1.add(xDateValue[j], yDoubleValue1[j]);
			// series2.add(xDateValue[i], yDoubleValue2[i]);
		}
		dataset.addSeries(series1);
		// dataset.addSeries(series2);

		// (5)???????????????????????????????????????(?????????????????????????????????)
		renderer.setBarSpacing(0.2); // ??????????????????
		// String[] types = new String[] { BarChart.TYPE , LineChart.TYPE };
		// GraphicalView mChartView = ChartFactory.getCombinedXYChartView(this,
		// dataset, renderer, types);
		GraphicalView mChartView = ChartFactory.getBarChartView(this, dataset,
				renderer, BarChart.Type.DEFAULT);
		// GraphicalView mChartView=ChartFactory.getTimeChartView(this, dataset,
		// renderer, "dd");

		return mChartView;
	}

	/*
	 * X?????????????????????????????????
	 */
	private void makeCustomXLabel(XYMultipleSeriesRenderer renderer, Date xMin,
			Date xMax, int xInterval) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		Date CurrDate;

		Calendar cal = Calendar.getInstance();
		cal.setTime(xMin);
		int month = cal.get(Calendar.MONTH);

		while (true) {
			CurrDate = cal.getTime();

			if (CurrDate.after(xMax)) {
				break;
			}
			String Xlabel = "";

			if (month != cal.get(Calendar.MONTH)) {
				Xlabel = sdf1.format(CurrDate);
				renderer.addXTextLabel(CurrDate.getTime(), Xlabel);
				month = cal.get(Calendar.MONTH);
			} else {
				Xlabel = sdf2.format(CurrDate);
				renderer.addXTextLabel(CurrDate.getTime(), Xlabel);
			}

			cal.add(Calendar.DAY_OF_MONTH, 1);

		}

	}
}
