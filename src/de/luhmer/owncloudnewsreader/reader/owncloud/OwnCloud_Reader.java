package de.luhmer.owncloudnewsreader.reader.owncloud;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import de.luhmer.owncloudnewsreader.reader.AsyncTask_Reader;
import de.luhmer.owncloudnewsreader.reader.FeedItemTags;
import de.luhmer.owncloudnewsreader.reader.IReader;
import de.luhmer.owncloudnewsreader.reader.OnAsyncTaskCompletedListener;

public class OwnCloud_Reader implements IReader {
	boolean isSyncRunning = false;

	SparseArray<AsyncTask_Reader> AsyncTasksRunning;
	
	public OwnCloud_Reader() {
		AsyncTasksRunning = new SparseArray<AsyncTask_Reader>();
	}
	
	@Override
	public void Start_AsyncTask_GetItems(int task_id,
			Activity context, OnAsyncTaskCompletedListener listener, FeedItemTags.TAGS tag) {
		setSyncRunning(true);
		AsyncTasksRunning.append(task_id, (AsyncTask_Reader) new AsyncTask_GetItems(task_id, context, new OnAsyncTaskCompletedListener[] { AsyncTask_finished, listener } ).execute(tag));
	}

	@Override
	public void Start_AsyncTask_GetOldItems(int task_id,
			Activity context, OnAsyncTaskCompletedListener listener, String feed_id, String folder_id) {
		setSyncRunning(true);
		AsyncTasksRunning.append(task_id, (AsyncTask_Reader) new AsyncTask_GetOldItems(task_id, context, new OnAsyncTaskCompletedListener[] { AsyncTask_finished, listener }, feed_id, folder_id).execute());
	}
	
	@Override
	public void Start_AsyncTask_GetFolder(int task_id,
			Activity context, OnAsyncTaskCompletedListener listener) {
		setSyncRunning(true);
		AsyncTasksRunning.append(task_id, (AsyncTask_Reader) new AsyncTask_GetFolderTags(task_id, context, new OnAsyncTaskCompletedListener[] { AsyncTask_finished, listener } ).execute());
	}
	
	@Override
	public void Start_AsyncTask_GetFeeds(int task_id,
			Activity context, OnAsyncTaskCompletedListener listener) {
		setSyncRunning(true);
		AsyncTasksRunning.append(task_id, (AsyncTask_Reader) new AsyncTask_GetFeeds(task_id, context, new OnAsyncTaskCompletedListener[] { AsyncTask_finished, listener } ).execute());
	}

	@Override
	public void Start_AsyncTask_PerformTagAction(int task_id,
			Context context, OnAsyncTaskCompletedListener listener,
			List<String> itemIds, FeedItemTags.TAGS tag) {
		setSyncRunning(true);
		AsyncTasksRunning.append(task_id, (AsyncTask_Reader) new AsyncTask_PerformTagAction(task_id, context, new OnAsyncTaskCompletedListener[] { AsyncTask_finished, listener } ).execute(itemIds, tag));
	}
	
	@Override
	public void Start_AsyncTask_Authenticate(int task_id, Activity context,
			OnAsyncTaskCompletedListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAsyncTaskCompleted(int task_id, Object task_result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSyncRunning() {
		return isSyncRunning;
	}
	
	@Override
	public void setSyncRunning(boolean isSyncRunning) {
		this.isSyncRunning = isSyncRunning;
	}

	OnAsyncTaskCompletedListener AsyncTask_finished = new OnAsyncTaskCompletedListener() {
		
		@Override
		public void onAsyncTaskCompleted(int task_id, Object task_result) {
			setSyncRunning(false);
			AsyncTasksRunning.remove(task_id);
		}
	};

	@Override
	public SparseArray<AsyncTask_Reader> getRunningAsyncTasks() {
		return AsyncTasksRunning;
	}

	@Override
	public void attachToRunningTask(int task_id, Activity activity, OnAsyncTaskCompletedListener listener) {
		if(AsyncTasksRunning.get(task_id) != null)
			AsyncTasksRunning.get(task_id).attach(activity, new OnAsyncTaskCompletedListener[] { listener, AsyncTask_finished });
	}

	
}
