package com.mikankui.hanasite.kakeibo;import java.util.ArrayList;import java.util.Calendar;import java.util.Date;import java.util.List;import com.mikankui.hanasite.kakeibo.db.DbQuery;import android.app.ListActivity;import android.content.Intent;import android.content.res.Resources;import android.os.Bundle;import android.view.View;import android.view.View.OnClickListener;import android.widget.AdapterView;import android.widget.AdapterView.OnItemClickListener;import android.widget.Button;import android.widget.DatePicker;import android.widget.TextView;/** * http://android.roof-balcony.com/view/listview/custom/ *  * @author kentarokira *  */public class YearListViewActivity extends ListActivity implements		OnItemClickListener, OnClickListener {	private YearListMemberAdapter adapter;	private List<RowItem> items;	private TextView yearPicker;	private TextView yearSum;	private Button NEXT_YEAR;	private Button PREV_YEAR;	private Button PREFERENCE;	private DbQuery q;	private String unit;	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.year_listview);		unit = getString(R.string.unit).toString();		q = new DbQuery();		items = new ArrayList<RowItem>();		// year picker		yearPicker = (TextView) findViewById(R.id.yearPicker);		yearPicker.setText("" + TargetDate.getYear());		// year sum		yearSum = (TextView) findViewById(R.id.yearSum);		// next and prev Buttom		NEXT_YEAR = (Button) findViewById(R.id.nextYear);		NEXT_YEAR.setOnClickListener(this);		PREV_YEAR = (Button) findViewById(R.id.previousYear);		PREV_YEAR.setOnClickListener(this);		PREFERENCE = (Button) findViewById(R.id.preference);		PREFERENCE.setOnClickListener(this);				// calc this year		redraw();	}	private void setOnItemClickListener(			YearListViewActivity mainListViewActivity) {		// TODO 自動生成されたメソッド・スタブ	}	@Override	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		// TODO 自動生成されたメソッド・スタブ	}	@Override	public void onClick(View v) {		int year;		switch (v.getId()) {		case R.id.nextYear:			TargetDate.nextYear();			year = TargetDate.getYear();			yearPicker.setText("" + year);			redraw();			break;		case R.id.previousYear:			TargetDate.backYear();			year = TargetDate.getYear();			yearPicker.setText("" + year);			redraw();			break;					case R.id.preference:			Intent intent = new Intent(this, PreferenceActivity.class);			intent.setAction(Intent.ACTION_VIEW);			startActivity(intent);			break;		}	}	private void redraw() {		items.clear();		q = new DbQuery();		int sum = 0;		int tmp = 0;		for (int i = 1; i <= 12; i++) {			tmp = q.calMonth("kira", "" + yearPicker.getText(), "" + i);			sum = sum + tmp;			items.add(new RowItem(i + getString(R.string.month),					getString(R.string.unit) + " " + tmp, ""));		}		yearSum.setText(unit + " " + sum);		adapter = new YearListMemberAdapter(this, items);		setListAdapter(adapter);		setOnItemClickListener(this);	}}