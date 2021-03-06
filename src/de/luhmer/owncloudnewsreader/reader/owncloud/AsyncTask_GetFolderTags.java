package de.luhmer.owncloudnewsreader.reader.owncloud;

import android.app.Activity;
import android.os.AsyncTask;
import de.luhmer.owncloudnewsreader.reader.AsyncTask_Reader;
import de.luhmer.owncloudnewsreader.reader.InsertIntoDatabase;
import de.luhmer.owncloudnewsreader.reader.OnAsyncTaskCompletedListener;

public class AsyncTask_GetFolderTags extends AsyncTask<Object, Void, Exception> implements AsyncTask_Reader {

    private Activity context;
    private int task_id;
    private OnAsyncTaskCompletedListener[] listener;

    public AsyncTask_GetFolderTags(final int task_id, final Activity context, final OnAsyncTaskCompletedListener[] listener) {
        super();

        this.context = context;
        this.task_id = task_id;
        this.listener = listener;
    }

    //Activity meldet sich zurueck nach OrientationChange
		public void attach(final Activity context, final OnAsyncTaskCompletedListener[] listener) {
			this.context = context;
			this.listener = listener;	
		}
			  
		//Activity meldet sich ab
		public void detach() {		
			if (context != null) {
				context = null;
			}
			 
			if (listener != null) {
				listener = null;
			}
		}
		
		
		@Override
		protected Exception doInBackground(Object... params) {
			/*
			String username = (String) params[0];
			String password = (String) params[1];
			*/

            try {
			    InsertIntoDatabase.InsertFoldersIntoDatabase(OwnCloudReaderMethods.GetFolderTags(context), context);
            } catch(Exception ex) {
                return ex;
            }
            return null;
		}
		
	    @Override
	    protected void onPostExecute(Exception ex) {
	    	for (OnAsyncTaskCompletedListener listenerInstance : listener) {
	    		if(listenerInstance != null)
	    			listenerInstance.onAsyncTaskCompleted(task_id, ex);
			}
	    	
			detach();
	    }
}
