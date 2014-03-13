package proft.me.testsqlite.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Item> items;

    public ItemAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        ItemDBHelper db = new ItemDBHelper(context);
        items = db.getItems();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public Item getItem(int i) {
        return items.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        final ViewHolder holder;
        View v = arg1;

        if ((v == null) || (v.getTag() == null)) {
            v = inflater.inflate(R.layout.item_row, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.item = getItem(arg0);
        holder.tvTitle.setText(holder.item.getTitle());
        v.setTag(holder);
        return v;
    }

    public class ViewHolder {
        Item item;
        TextView tvTitle;
    }

}
