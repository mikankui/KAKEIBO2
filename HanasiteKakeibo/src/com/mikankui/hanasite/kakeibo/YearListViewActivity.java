package com.mikankui.hanasite.kakeibo;import java.util.ArrayList;import java.util.List;import com.mikankui.hanasite.kakeibo.db.DbQuery;import com.mikankui.hanasite.kakeibo.preference.PreferenceData;import com.mikankui.hanasite.kakeibo.preference.SettingActivity;import android.app.ListActivity;import android.content.Intent;import android.content.SharedPreferences;import android.os.Bundle;import android.preference.PreferenceManager;import android.view.GestureDetector;import android.view.GestureDetector.SimpleOnGestureListener;import android.view.MotionEvent;import android.view.View;import android.view.View.OnClickListener;import android.view.View.OnTouchListener;import android.widget.AdapterView;import android.widget.Button;import android.widget.ListView;import android.widget.TextView;/** * http://android.roof-balcony.com/view/listview/custom/ *  * @author kentarokira *  */public class YearListViewActivity extends ListActivity implements		OnClickListener {	private YearListMemberAdapter adapter;	private List<RowItem> items;	private TextView yearPicker;	private TextView yearSum;	private Button PREFERENCE;	private DbQuery q;	private String unit;	private OnTouchListener pageChenger;	private GestureDetector gestureDetector;    private static final int SWIPE_MIN_DISTANCE = 120;    private static final int SWIPE_MAX_OFF_PATH = 250;    private static final int SWIPE_THRESHOLD_VELOCITY = 200;	@Override	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		setContentView(R.layout.year_listview);		unit = getString(R.string.unit);		q = new DbQuery();		items = new ArrayList<RowItem>();		// year picker		yearPicker = (TextView) findViewById(R.id.yearPicker);		yearPicker.setText("" + TargetDate.getYear());		// year sum		yearSum = (TextView) findViewById(R.id.yearSum);		// next and prev Buttom		PREFERENCE = (Button) findViewById(R.id.setting);		PREFERENCE.setOnClickListener(this);		// ListActivity内部にあるListViewオブジェクトを取得する場合		ListView listView = getListView();				final SimpleOnGestureListener pageChangeListener = new SimpleOnGestureListener() {						int year;						@Override			public boolean onFling(MotionEvent event1, MotionEvent event2,					float velocityX, float velocityY) {				try {					if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH) {						// 縦の移動距離が大きすぎる場合は無視						return false;					}					if (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						// 開始位置から終了位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						// 終了位置から開始位置の移動距離が指定値より大きい						// X軸の移動速度が指定値より大きい						TargetDate.backYear();						year = TargetDate.getYear();						yearPicker.setText("" + year);						redraw();											} else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE							&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {						TargetDate.nextYear();						year = TargetDate.getYear();						yearPicker.setText("" + year);						redraw();					}				} catch (Exception e) {					// nothing				}				return false;			}		};		gestureDetector = new GestureDetector(pageChangeListener);		pageChenger = new OnTouchListener() {			   public boolean onTouch(View v, MotionEvent event) {			    return gestureDetector.onTouchEvent(event);			   }			  };			  		listView.setOnTouchListener(pageChenger);				// リスト項目をクリック時に呼び出されるコールバックを登録		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {			// リスト項目クリック時の処理			@Override			public void onItemClick(AdapterView<?> parent, View view,					int position, long id) {				String monthStr = getString(R.string.month);				ListView listView = (ListView) parent;				RowItem item = (RowItem) listView.getItemAtPosition(position);				TargetDate.setMonth(Integer.parseInt(item.getRowid()						.replaceAll(monthStr, "")) - 1);				Intent intent = new Intent();				intent.setClass(YearListViewActivity.this,						MonthListViewActivity.class);				intent.setAction(Intent.ACTION_VIEW);				startActivity(intent);				overridePendingTransition(R.anim.push_left_in,						R.anim.push_left_out);			}		});		/*		 * //リスト項目の選択時に呼び出されるコールバックを登録 listView.setOnItemSelectedListener(new		 * AdapterView.OnItemSelectedListener() {		 * 		 * //リスト項目が選択された場合(端末の方向キー等でリストの項目がフォーカスされた時)の処理		 * 		 * @Override public void onItemSelected(AdapterView<?> parent, View		 * view,int position, long id) { // ここに処理を記述します。		 * 		 * //今回は、トースト表示 ListView listView =(ListView)parent; String		 * item=(String)listView.getItemAtPosition(position);		 * Toast.makeText(YearListViewActivity.this, "Selected: "+item,		 * Toast.LENGTH_SHORT).show();		 * 		 * }		 * 		 * //リスト項目が何も選択されないない場合の処理 //（初期状態では、呼び出されず。選択がなくなった時）		 * 		 * @Override public void onNothingSelected(AdapterView<?> adapterview) {		 * 		 * // ここに処理を記述します。		 * 		 * //今回は、トースト表示 Toast.makeText(YearListViewActivity.this, "no item",		 * Toast.LENGTH_SHORT).show();		 * 		 * }		 * 		 * });		 * 		 * //リストの項目が長押しされた場合に呼び出されるコールバックを登録		 * listView.setOnItemLongClickListener(new		 * AdapterView.OnItemLongClickListener() {		 * 		 * //リストの項目が長押しされた場合の処理		 * 		 * @Override public boolean onItemLongClick(AdapterView<?> parent,View		 * view, int position, long id) {		 * 		 * // ここに処理を記述します。		 * 		 * //今回は、トースト表示 ListView listView =(ListView)parent; String		 * item=(String)listView.getItemAtPosition(position);		 * Toast.makeText(YearListViewActivity.this, "Long: "+item,		 * Toast.LENGTH_SHORT).show();		 * 		 * return false; } });		 */		// calc this year		redraw();	}	@Override	public void onClick(View v) {		switch (v.getId()) {		case R.id.setting:			Intent intent = new Intent(this, SettingActivity.class);			intent.setAction(Intent.ACTION_VIEW);			startActivity(intent);			break;		}	}	private void redraw() {		//set user name		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);		String user =pref.getString("userName", "");				items.clear();		q = new DbQuery();		int sum = 0;		int tmp = 0;		for (int i = 1; i <= 12; i++) {			tmp = q.calMonth(user, "" + yearPicker.getText(), "" + i);			sum = sum + tmp;			items.add(new RowItem(i + getString(R.string.month),					getString(R.string.unit) + " " + tmp, ""));		}		yearSum.setText(unit + " " + sum);		adapter = new YearListMemberAdapter(this, items);		setListAdapter(adapter);	}}