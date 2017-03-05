package tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.bean.Book;
import tianhao.agoto.Common.Widget.RecyclerView.LoadMoreRecyclerView.db.BookDBHelper;
import tianhao.agoto.R;


/**
 * Created by zoom on 2016/3/30.
 */
public class BookAdapter extends RecyclerViewCursorAdapter<BookAdapter.BookViewHolder> {

    private LayoutInflater inflater;

    public BookAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater=LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, Cursor cursor) {

    /*    Book book=new Book();
        book.setId(cursor.getString(cursor.getColumnIndex(BookDBHelper.ID)));
        book.setName(cursor.getString(cursor.getColumnIndex(BookDBHelper.NAME)));
        book.setPrice(cursor.getInt(cursor.getColumnIndex(BookDBHelper.PRICE)));*/

/*        holder.name.setText(book.getName());
        holder.price.setText(Integer.toString(book.getPrice()));*/

    }

    @Override
    protected void onContentChanged() {}

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);

        return new BookViewHolder(v);
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
/*
        public TextView name;
        public TextView price;*/

        public BookViewHolder(View itemView) {
            super(itemView);/*
            name= (TextView) itemView.findViewById(R.id.book_name);
            price= (TextView) itemView.findViewById(R.id.book_price);*/

        }
    }
}
