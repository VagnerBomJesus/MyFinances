package pt.vagner.myfinances;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FinanceContentProvider extends ContentProvider {

    public static final String AUTHORITY = "pt.vagner.myfinances.FinanceContentProvider";
    public static final String CATEGORIAS_FINANCE = "categorias_receitas";
    //public static final String CATEGORIAS_FINANCE_R = "categorias_receitas";

    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY);

    public static final Uri CONTAB_URI = Uri.withAppendedPath(BASE_URI,BdTableRegistoMovimentos.NOME_TABELA);
    public static final Uri CATEGORIAS_RECEITAS_URI = Uri.withAppendedPath(BASE_URI, BdTableTipoReceita.NOME_TABELA);



    private static final int URI_CATEGORIAS_RECEITAS = 200;
    private static final int URI_CATEGORIAS_RECEITAS_ID = 201;

    public static final String SINGLE_ITEM = "vnd.android.cursor.item/";
    public static final String MULTIPLOS_ITEMS = "vnd.android.cursor.dir/";

    private BdFinaceOpenHelper bdFinaceOpenHelper;

    private static UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY,CATEGORIAS_FINANCE,URI_CATEGORIAS_RECEITAS);

        uriMatcher.addURI(AUTHORITY,CATEGORIAS_FINANCE + "/#", URI_CATEGORIAS_RECEITAS_ID);

        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        bdFinaceOpenHelper = new BdFinaceOpenHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = bdFinaceOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        //UriMatcher matcher = getUriMatcher();

        switch (getUriMatcher().match(uri)){

            case URI_CATEGORIAS_RECEITAS:
                return new BdTableTipoReceita(bd).query(projection,selection,selectionArgs,null,null,sortOrder);

            case URI_CATEGORIAS_RECEITAS_ID:
                return new BdTableTipoReceita(bd).query(projection, BdTableTipoReceita._ID+"=?",new String[]{id},null,null,sortOrder);

            default:
                throw new UnsupportedOperationException(" URI Invalida (QUERY) : " + uri.toString());
        }

        //return null;
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * Implement this to handle requests to insert a new row.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after inserting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     * @return The URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = bdFinaceOpenHelper.getWritableDatabase();

        UriMatcher matcher = getUriMatcher();

        long id = -1;

        switch (matcher.match(uri)){
            case URI_CATEGORIAS_RECEITAS:
                id = new BdTableTipoReceita(db).insert(values);
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI: "+uri);
        }

        if (id > 0){ //foram inseridos registos
            notifyChanges(uri);
            return Uri.withAppendedPath(uri,Long.toString(id));
        }
        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo");
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));

    }
    private void notifyChanges(@NonNull Uri uri) {
        getContext().getContentResolver().notifyChange(uri,null);
    }

    /**
     * Implement this to handle requests to delete one or more rows.
     * The implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after deleting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>The implementation is responsible for parsing out a row ID at the end
     * of the URI, if a specific row is being deleted. That is, the client would
     * pass in <code>content://contacts/people/22</code> and the implementation is
     * responsible for parsing the record number (22) when creating a SQL statement.
     *
     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs
     * @return The number of rows affected.
     * @throws SQLException
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = bdFinaceOpenHelper.getWritableDatabase();

        // UriMatcher matcher = getUriMatcher();

        String id = uri.getLastPathSegment();

        int rows = 0;

        switch (getUriMatcher().match(uri)){
            case URI_CATEGORIAS_RECEITAS_ID:
                rows = new BdTableTipoReceita(db).delete(BdTableTipoReceita._ID+"=?",new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());
        }

        if (rows > 0) notifyChanges(uri);

        return rows;
    }

    /**
     * Implement this to handle requests to update one or more rows.
     * The implementation should update all rows matching the selection
     * to set the columns according to the provided values map.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after updating.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri           The URI to query. This can potentially have a record ID if this
     *                      is an update request for a specific record.
     * @param values        A set of column_name/value pairs to update in the database.
     *                      This must not be {@code null}.
     * @param selection     An optional filter to match rows to update.
     * @param selectionArgs
     * @return the number of rows affected.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = bdFinaceOpenHelper.getWritableDatabase();

        //UriMatcher matcher = getUriMatcher();

        String id = uri.getLastPathSegment();

        int rows = 0;

        switch (getUriMatcher().match(uri)){
            case URI_CATEGORIAS_RECEITAS_ID:
                rows = new BdTableTipoReceita(db).update(values,BdTableTipoReceita._ID+"=?",new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("URI inválida (UPDATE): " + uri.toString());

        }

        if (rows > 0) notifyChanges(uri);

        return rows;
    }
}
