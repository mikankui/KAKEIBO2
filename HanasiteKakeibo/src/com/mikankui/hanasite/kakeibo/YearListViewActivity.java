package com.mikankui.hanasite.kakeibo;import java.util.ArrayList;import java.util.Calendar;import java.util.Date;import java.util.List;import com.mikankui.hanasite.kakeibo.db.DbQuery;import android.app.ListActivity;import android.content.res.Resources;import android.os.Bundle;import android.view.View;import android.view.View.OnClickListener;import android.widget.AdapterView;import android.widget.AdapterView.OnItemClickListener;import android.widget.Button;import android.widget.DatePicker;import android.widget.TextView;/** * http://android.roof-balcony.com/view/listview/custom/ *  * @author kentarokira *  */public class YearListViewActivity extends ListActivity implements		OnItemClickListener, OnClickListener {	private YearListMemberAdapter adapter;	private List<RowItem> items;	private TextView yearPicker;	private TextView yearSum;	private Button NEXT_YEAR;	private Button PREV_YEAR;	private DbQuery q;	private String unit;	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.year_listview);		unit = getString(R.string.unit).toString();		q = new DbQuery();		items = new ArrayList<RowItem>();		/*		 * //year picker DatePicker yearPicker =		 * (DatePicker)findViewById(R.id.yearPicker);		 * yearPicker.updateDate(2013, 8, 11); //hide day int day_id =		 * Resources.getSystem().getIdentifier("day", "id", "android");		 * yearPicker.findViewById(day_id).setVisibility(View.GONE); //hide		 * month int month_id = Resources.getSystem().getIdentifier("month",		 * "id", "android");		 * yearPicker.findViewById(month_id).setVisibility(View.GONE);		 */		// year picker		yearPicker = (TextView) findViewById(R.id.yearPicker);		yearPicker.setText("" + TargetDate.getYear());		// year sum		yearSum = (TextView) findViewById(R.id.yearSum);		// next and prev Buttom		NEXT_YEAR = (Button) findViewById(R.id.nextYear);		NEXT_YEAR.setOnClickListener(this);		PREV_YEAR = (Button) findViewById(R.id.previousYear);		PREV_YEAR.setOnClickListener(this);		// calc this year		redraw();	}	private void setOnItemClickListener(			YearListViewActivity mainListViewActivity) {		// TODO 自動生成されたメソッド・スタブ	}	@Override	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		// TODO 自動生成されたメソッド・スタブ	}	@Override	public void onClick(View v) {		int year;		switch (v.getId()) {		case R.id.nextYear:			TargetDate.nextYear();			year = TargetDate.getYear();			yearPicker.setText("" + year);			redraw();			break;		case R.id.previousYear:			TargetDate.backYear();			year = TargetDate.getYear();			yearPicker.setText("" + year);			redraw();			break;		}	}	private void redraw() {		items.clear();		q = new DbQuery();		int sum = 0;		int tmp = 0;		for (int i = 1; i <= 12; i++) {			tmp = q.calMonth("kira", "" + yearPicker.getText(), "" + i);			sum = sum + tmp;			items.add(new RowItem(i + getString(R.string.month),					getString(R.string.unit) + " " + tmp, ""));		}		yearSum.setText(unit + " " + sum);		adapter = new YearListMemberAdapter(this, items);		setListAdapter(adapter);		setOnItemClickListener(this);	}}