package com.rentmitra.dashboard;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rentmitra.R;

public class OptionAdapter extends BaseAdapter{
	
	List<String> optionList;
	private LayoutInflater minflater;
	private Context mContext;

	
	public OptionAdapter(Context context) {
		mContext = context;
		minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return null == optionList ? 0 : optionList.size();
	}

	@Override
	public Object getItem(int position) {
		return optionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		OptionHolder viewHolder;

		if (convertView == null) {
			viewHolder = new OptionHolder();
			convertView = minflater.inflate(R.layout.option_inner, null, false);
		
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (OptionHolder) convertView.getTag();
		}
		
		viewHolder.optionTv		= (TextView)convertView.findViewById(R.id.optionTv);
		viewHolder.optionTv.setText(optionList.get(position));
		return convertView;
	}
	
	public void setList(List<String> list){
		this.optionList = list;
		notifyDataSetChanged();
	}
	
	class OptionHolder{
		private TextView optionTv;
		
	}

}
