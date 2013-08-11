package com.mikankui.hanasite.kakeibo;import java.util.List;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.View.OnClickListener;import android.view.ViewGroup;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.TextView;import android.widget.Toast;public class ListMemberAdapter extends ArrayAdapter<RowItem>{	private LayoutInflater mInflater;	private TextView rId;	private TextView rValue;	private Button mButton;		public ListMemberAdapter(Context context,  List<RowItem> item) {		super(context, 0, item);		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	}		public View getView(final int position, View convertView, ViewGroup parent) {		if (convertView == null) {			convertView = mInflater.inflate(R.layout.main_listitem, null);			}				final RowItem item = this.getItem(position);				if(item != null){			rId = (TextView)convertView.findViewById(R.id.rowId);			rId.setText(item.getRowid());			rValue = (TextView)convertView.findViewById(R.id.rowValue);			rValue.setText(item.getRowValue());			mButton = (Button)convertView.findViewById(R.id.rowAction);			mButton.setOnClickListener(new OnClickListener() {				public void onClick(View v) {					Toast.makeText(getContext(), "pushed", Toast.LENGTH_LONG).show();				}			});		}		return convertView;		}}