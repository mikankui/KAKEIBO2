package com.mikankui.hanasite.kakeibo;import java.util.ArrayList;import java.util.List;import android.app.ListActivity;import android.content.Intent;import android.os.Bundle;import android.view.GestureDetector;import android.view.MotionEvent;import android.view.View;import android.view.GestureDetector.SimpleOnGestureListener;import android.view.View.OnClickListener;import android.view.View.OnTouchListener;import android.widget.AdapterView;import android.widget.Button;import android.widget.ListView;import android.widget.TextView;import android.widget.AdapterView.OnItemClickListener;import com.mikankui.hanasite.kakeibo.db.DbQuery;public class DayListViewActivity extends ListActivity implements OnClickListener {	private DayListMemberAdapter adapter;	private List<RowItem> items;	private TextView dayPicker;	private TextView daySum;	private Button next_day;	private Button back_day;	private Button move_monthListView;	private DbQuery q;	private String unit;	private OnTouchListener pageChenger;	private GestureDetector gestureDetector;    private static final int SWIPE_MIN_DISTANCE = 120;    private static final int SWIPE_MAX_OFF_PATH = 250;    private static final int SWIPE_THRESHOLD_VELOCITY = 200;    	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.day_listview);		unit = getString(R.string.unit).toString();		q = new DbQuery();		items = new ArrayList<RowItem>();		// year picker		dayPicker = (TextView) findViewById(R.id.dayPicker);		dayPicker.setText("" + TargetDate.getYear() + "/"				+ TargetDate.getMonth() + "/"				+ TargetDate.getDay());		// year sum		daySum = (TextView) findViewById(R.id.daySum);		// next and prev Buttom		move_monthListView = (Button) findViewById(R.id.move_monthList);		move_monthListView.setOnClickListener(this);	    // ListActivity内部にあるListViewオブジェクトを取得する場合	    ListView listView=getListView();				final SimpleOnGestureListener pageChangeListener = new SimpleOnGestureListener() {						int year;						@Override			public boolean onFling(MotionEvent event1, MotionEvent event2,					float velocityX, float velocityY) {				try {					if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {						// 縦の移動距離が大きすぎる場合は無視						return false;					}					if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						// 開始位置から終了位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						// 終了位置から開始位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						TargetDate.nextDay();						dayPicker.setText("" + TargetDate.getYear() + "/"								+ TargetDate.getMonth() + "/"								+ TargetDate.getDay());						redraw();											} else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						TargetDate.backDay();						dayPicker.setText("" + TargetDate.getYear() + "/"								+ TargetDate.getMonth()+ "/"								+ TargetDate.getDay());						redraw();					}				} catch (Exception e) {					// nothing				}				return false;			}		};		gestureDetector = new GestureDetector(pageChangeListener);		pageChenger = new OnTouchListener() {			   public boolean onTouch(View v, MotionEvent event) {			    return gestureDetector.onTouchEvent(event);			   }			  };			  		listView.setOnTouchListener(pageChenger);	    //リスト項目をクリック時に呼び出されるコールバックを登録	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {	       //リスト項目クリック時の処理	      @Override	      public void onItemClick(AdapterView<?> parent, View view, int position,long id) {	      }	    });		// calc this year		redraw();	}	@Override	public void onClick(View v) {		switch (v.getId()) {		case R.id.move_monthList:			Intent intent = new Intent(this, MonthListViewActivity.class);			intent.setAction(Intent.ACTION_VIEW);			startActivity(intent);			overridePendingTransition(R.anim.push_right_in,					R.anim.push_right_out);		}	}	private void redraw() {		items.clear();		q = new DbQuery();		int sum = 0;				items = q.getDayList("kira", ""+TargetDate.getYear(), ""+TargetDate.getMonth(), ""+TargetDate.getDay());		for(RowItem i :items){			sum = sum + Integer.parseInt(i.getRowValue());		}				daySum.setText(unit + " " + sum);		adapter = new DayListMemberAdapter(this, items);		setListAdapter(adapter);	}}