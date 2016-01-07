package com.vaiuu.androidtrello.trello.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vaiuu.androidtrello.R;
import com.vaiuu.androidtrello.trello.model.BoardModel;

import java.util.Vector;

public class BoardAdapter extends ArrayAdapter<BoardModel> {
	Context context;
	Vector<BoardModel> boardModels;
	public BoardAdapter(Context context, Vector<BoardModel> boardModels) {
		super(context, R.layout.row_trello_board, boardModels);
		this.context = context;
		this.boardModels = boardModels;
	}

	static class ViewHolder {

		TextView board_name;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View v = convertView;
		System.out.println("getview:" + position + " " + convertView);
		if (v == null) {
			final LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_trello_board, null);
			holder = new ViewHolder();
			holder.board_name = (TextView) v.findViewById(R.id.board_name);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		if (position < boardModels.size()) {
			final BoardModel query = boardModels.get(position);
			holder.board_name.setText("" + query.getName());

		}

		return v;
	}

}