package com.mikankui.hanasite.kakeibo;import java.util.ArrayList;import java.util.List;import com.mikankui.hanasite.kakeibo.db.DbQuery;import android.app.ListActivity;import android.content.Intent;import android.content.SharedPreferences;import android.os.Bundle;import android.preference.PreferenceManager;import android.view.GestureDetector;import android.view.MotionEvent;import android.view.View;import android.view.GestureDetector.SimpleOnGestureListener;import android.view.View.OnClickListener;import android.view.View.OnTouchListener;import android.widget.AdapterView;import android.widget.Button;import android.widget.ListView;import android.widget.TextView;import android.widget.Toast;import android.widget.AdapterView.OnItemClickListener;public class MonthListViewActivity extends ListActivity implements OnClickListener {	private MonthListMemberAdapter adapter;	private List<RowItem> items;	private TextView monthPicker;	private TextView monthSum;	private Button move_yearListView;	private DbQuery q;	private String unit;	private OnTouchListener pageChenger;	private GestureDetector gestureDetector;    private static final int SWIPE_MIN_DISTANCE = 120;    private static final int SWIPE_MAX_OFF_PATH = 250;    private static final int SWIPE_THRESHOLD_VELOCITY = 200;    	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.month_listview);		unit = getString(R.string.unit).toString();		q = new DbQuery();		items = new ArrayList<RowItem>();		// year picker		monthPicker = (TextView) findViewById(R.id.monthPicker);		monthPicker.setText("" + TargetDate.getYear() + "/"				+ TargetDate.getMonth());		// year sum		monthSum = (TextView) findViewById(R.id.monthSum);		// next and prev Buttom		move_yearListView = (Button) findViewById(R.id.move_yearList);		move_yearListView.setOnClickListener(this);	    // ListActivity内部にあるListViewオブジェクトを取得する場合	    ListView listView=getListView();				final SimpleOnGestureListener pageChangeListener = new SimpleOnGestureListener() {						int year;						@Override			public boolean onFling(MotionEvent event1, MotionEvent event2,					float velocityX, float velocityY) {				try {					if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {						// 縦の移動距離が大きすぎる場合は無視						return false;					}					if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						// 開始位置から終了位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						// 終了位置から開始位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						TargetDate.nextMonth();						monthPicker.setText("" + TargetDate.getYear() + "/"								+ TargetDate.getMonth());						redraw();											} else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						TargetDate.backMonth();						monthPicker.setText("" + TargetDate.getYear() + "/"								+ TargetDate.getMonth());						redraw();					}				} catch (Exception e) {					// nothing				}				return false;			}		};		gestureDetector = new GestureDetector(pageChangeListener);		pageChenger = new OnTouchListener() {			   public boolean onTouch(View v, MotionEvent event) {			    return gestureDetector.onTouchEvent(event);			   }			  };			  		listView.setOnTouchListener(pageChenger);	    //リスト項目をクリック時に呼び出されるコールバックを登録	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {	       //リスト項目クリック時の処理	      @Override	      public void onItemClick(AdapterView<?> parent, View view, int position,long id) {	    	String dayStr = getString(R.string.day);	        ListView listView =(ListView)parent;	        RowItem item=(RowItem)listView.getItemAtPosition(position);	        	        TargetDate.setDay(Integer.parseInt(item.getRowid().replaceAll(dayStr, "")));			Intent intent = new Intent();			intent.setClass(MonthListViewActivity.this, DayListViewActivity.class);			intent.setAction(Intent.ACTION_VIEW);			startActivity(intent);			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);	      }	    });	        				// calc this year		redraw();	}	@Override	public void onClick(View v) {		switch (v.getId()) {		case R.id.move_yearList:			Intent intent = new Intent(this, YearListViewActivity.class);			intent.setAction(Intent.ACTION_VIEW);			startActivity(intent);			overridePendingTransition(R.anim.push_right_in,					R.anim.push_right_out);		}	}	private void redraw() {		items.clear();		q = new DbQuery();		int sum = 0;		int tmp = 0;		//set user name		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);		String user =pref.getString("userName", "");		for (int i = 1; i <= TargetDate.getActualMaximumOfMonth(); i++) {			tmp = q.calDay(user, "" + TargetDate.getYear(),					"" + TargetDate.getMonth(), "" + i);			sum = sum + tmp;			items.add(new RowItem(i + getString(R.string.day),					getString(R.string.unit) + " " + tmp, ""));		}		monthSum.setText(unit + " " + sum);		adapter = new MonthListMemberAdapter(this, items);		setListAdapter(adapter);	}}