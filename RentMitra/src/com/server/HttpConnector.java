package com.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.server.mime.HttpMultipartMode;
import com.server.mime.MultipartEntity;
import com.server.mime.content.StringBody;


@SuppressLint("DefaultLocale")
public class HttpConnector {
	public static final String TAG = HttpConnector.class.getSimpleName();

	private static final String ACCEPT = "application/json, api_version=1";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String USER_AGENT = "Postaplan-android";

//	private CategoryColors mCategoryColors;
	private HttpResponseListener mHttpResponseListener;
	private INetConnListener mINetConnListener;
	private Dialog mDialog = null;
	private View mLoaderView = null;
	private static Handler mHandler = null;
	private HttpClient mHttpClient = null;
	private String mAccessToken = null;
	private AtomicBoolean mActivityLive;
	private String mError = null;
	private boolean mCacheEnabled = false;
	private boolean mAutoRefreshEnabled = false;
	private static Set<Runnable> mAutoRefreshRunnables = null;
	private Context mContext;

	public enum UrlType {
		SERVICE, IMAGE, UPLOAD, EXTERNAL;
	}

	public HttpConnector(Context context) {
		if (null == mHandler) {
			mHandler = new Handler();
		} else {
			mHandler.removeCallbacksAndMessages(null);
		}
//		this.mCategoryColors = categoryColors;
		this.mContext = context;
		this.mHttpClient = new DefaultHttpClient();
		this.mActivityLive = new AtomicBoolean(true);
		
		/* RequestConfig requestConfig = RequestConfig.custom()
			       .setSocketTimeout(5000)
			       .setConnectTimeout(5000)
			       .setConnectionRequestTimeout(5000)
			       .build();
		*/

		showLoader(false, false);
	}

	public void setHttpResponseListener(HttpResponseListener listener) {
		this.mHttpResponseListener = listener;

	}

/*	public void enableCaching(boolean enable) {
		this.mCacheEnabled = enable;
		if (this.mCacheEnabled) {
			mFileCache = new FileCache();
		}
	}*/

	public void setAutoRefreshEnabled(boolean enable) {
		this.mAutoRefreshEnabled = enable;
	}

	public void setDialog(Dialog dialog) {
		this.mDialog = dialog;
	}

	public void setLoader(View view) {
		this.mLoaderView = view;
	}

	public void setActivityStatus(boolean live) {
		this.mActivityLive.set(live);
	}

	public interface HttpResponseListener {
		void onResponse(int reqCode, int statusCode, String json);

		void onCancel(boolean canceled);

		void onProgressChange(int progress);
	}

	public void setINetConnListener(INetConnListener listener) {
		this.mINetConnListener = listener;

	}

	public interface INetConnListener {
		void onNotConnected();
	}

	public void executeAsync(final String url, final int reqCode, final boolean isGet, boolean runInBg, int urlType) {
		String method;

		if (isGet) {
			method = "get";
		} else {
			method = "post";
		}
		if (urlType == 1)
			executeAsync(url, reqCode, method, runInBg, null, null, UrlType.SERVICE);
		else if (urlType == 4)
			executeAsync(url, reqCode, method, runInBg, null, null, UrlType.EXTERNAL);
	}

	public void executeAsync(final String remainUrl, final int reqCode, final String method, final boolean runInBg, final String jsonData,
			final ArrayList<BasicNameValuePair> extraFormData, final UrlType urlType) {
		if (Config.DEBUG) {
			logFullResponse("executeAsync - url=" + remainUrl + ", reqCode=" + reqCode + ", method=" + method + ", runInBg=" + runInBg
					+ ", jsonData=" + jsonData + ", extraFormData=" + extraFormData);
		}
		// check if activity dead, if yes return
		if (!mActivityLive.get()) {
			if (Config.DEBUG) {
				Log.w(TAG, Config.REQUEST_STOPPED_MSG);
			}
			if (null != mHttpResponseListener) {
				mHttpResponseListener.onCancel(true);
			}
			return;
		}
/*		final File cacheFile = mCacheEnabled ? mFileCache.getFile((remainUrl + reqCode + jsonData).hashCode() + ".req") : null;
		if (TYPE_NOT_CONNECTED == getConnectivityStatus(mContext)) {
			if (mCacheEnabled) {
				mHttpResponseListener.onResponse(reqCode, 200, FileCache.readFile(cacheFile));
			} else {
				if (null != mINetConnListener) {
					mINetConnListener.onNotConnected();
				} else {
					
					DialogUtil.showOkCancelDialog(mContext,
							Utility.FromResouarceToString(mContext, R.string.Network_erro),
							Utility.FromResouarceToString(mContext, R.string.No_internet_connection_Please_retry), new OnClickListener() {

								@Override
								public void onClick(View v) {
									executeAsync(remainUrl, reqCode, method, false, jsonData, extraFormData, urlType);
								}
							}, new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

								}
							});
					
//					DialogUtil.showOkDialogWithTittle(mCategoryColors,
//							Utility.FromResouarceToString(mContext, R.string.Network_erro),
//							Utility.FromResouarceToString(mContext, R.string.No_internet_connection));
				}
			}
			if (mAutoRefreshEnabled) {
				addToAutoRefresh(new Runnable() {
					@Override
					public void run() {
						executeAsync(remainUrl, reqCode, method, runInBg, jsonData, extraFormData, urlType);
					}
				});
			}
			return;
		}*/
		UrlType type = ((null == urlType) ? UrlType.SERVICE : urlType);
		final String finalUrl;
		switch (type) {
		case SERVICE:
			finalUrl = Config.SERVICE_URL + remainUrl;
			break;
		case IMAGE:
			finalUrl = Config.IMAGE_URL + remainUrl;
			break;
		case UPLOAD:
			finalUrl = Config.UPLOAD_URL + remainUrl;
			break;
		case EXTERNAL:
			finalUrl = remainUrl;
			break;
		default:
			finalUrl = Config.SERVICE_URL + remainUrl;
			break;
		}

		if (Config.DEBUG) {
			Log.v("finalUrl : ", " " + finalUrl);
		}
		showLoader(true, runInBg);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String response = null;
				int statusCode = 0;
				mError = null;
				try {
					HttpUriRequest httpRequest = null;
					if (method.toLowerCase().contains("get")) {
						HttpGet httpGet = new HttpGet(finalUrl);
						if (null != mAccessToken) {
							httpGet.addHeader("Authorization", "OAuth " + mAccessToken);
						}

						httpGet.addHeader("Accept", ACCEPT);
						httpGet.addHeader("Content-Type", CONTENT_TYPE);
						httpGet.addHeader("User-Agent", USER_AGENT);

						httpRequest = httpGet;
					} else if (method.toLowerCase().contains("post")) {
						HttpPost httpPost = new HttpPost(finalUrl);

						if (mAccessToken != null && extraFormData == null) {
							httpPost.addHeader("Authorization", "OAuth " + mAccessToken);
						}
						// Used for posting an image to AWS
						if (extraFormData != null) {
							MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
							for (int i = 0; i < extraFormData.size(); i++) {
								BasicNameValuePair pair = extraFormData.get(i);
								if (pair.getName().equalsIgnoreCase("mediaString")) {/*
									String path = pair.getValue();
									int lastSlash = path.lastIndexOf("/");
									String filename = path.substring(lastSlash + 1);

									String mimeType = "video/mp4";

									File file = new File(pair.getValue());

									int dotIndex = file.getName().lastIndexOf('.');
									if (dotIndex > 0 && dotIndex < file.getName().length()) {
										mimeType = file.getName().substring(dotIndex + 1);
										if (mimeType.equalsIgnoreCase("mp4") || mimeType.equalsIgnoreCase("mp3")
												|| mimeType.equalsIgnoreCase("3gp")) {
											mimeType = "video/" + mimeType;
										} else if (mimeType.equalsIgnoreCase("jpg") || mimeType.equalsIgnoreCase("jpeg")
												|| mimeType.equalsIgnoreCase("png")) {
											mimeType = "image/" + mimeType;
										} else {
											mimeType = "file/" + mimeType;
										}
									}
									FileBody fb = new FileBody(file, mimeType, filename);
									entity.addPart(pair.getName(), fb);

									File root_Directory = new File(Environment.getExternalStorageDirectory(), "FK");

									if (!root_Directory.exists()) {
										root_Directory.mkdir();
									}
									String DestinationPath = root_Directory.getAbsoluteFile() + "/" + "Parcy" + ".jpg";

									File destination = new File(DestinationPath);
									try {
										FileUtils.copyFile(file, destination);
									} catch (IOException e) {
										e.printStackTrace();
									}
*/
								} else {
									entity.addPart(pair.getName(), new StringBody(pair.getValue()));
								}
							}
							httpPost.setEntity(entity);
						} else {
							// Don't add headers for multi-form post or else it
							// breaks!
							httpPost.addHeader("Accept", ACCEPT);
							httpPost.addHeader("Content-Type", CONTENT_TYPE);
							httpPost.addHeader("User-Agent", USER_AGENT);
						}

						if (jsonData != null) {
							httpPost.setEntity(new StringEntity(jsonData));
						}

						httpRequest = httpPost;
					} else if (method.equalsIgnoreCase("multipart-post")) {
						MultipartConn multipartConn = new MultipartConn(finalUrl);

						// multipartConn.addHeaderField("Accept", ACCEPT);
						// multipartConn.addHeaderField("Content-Type",
						// CONTENT_TYPE);
						multipartConn.addHeaderField("User-Agent", USER_AGENT);

						if (null != extraFormData) {
							for (NameValuePair nameValuePair : extraFormData) {
								if (nameValuePair.getName().endsWith("file")
										|| (nameValuePair.getName().endsWith("_pic") || nameValuePair.getName().endsWith("_img"))
										&& null != nameValuePair.getValue() && nameValuePair.getValue().length() > 5) {
									String path = nameValuePair.getValue();
									final String fileSuffix = "file://";
									if (path.startsWith(fileSuffix)) {
										path = path.substring(fileSuffix.length());
									}
									multipartConn.addFilePart(nameValuePair.getName(), new File(path));
								} else {
									multipartConn.addFormField(nameValuePair.getName(), nameValuePair.getValue());
								}
							}
						}

						mHttpURLConnection = multipartConn.prepareConnection();
					} /*else if (method.toLowerCase().contains("put")) {
						HttpPut httpPut = new HttpPut(finalUrl);

						if (null != mAccessToken) {
							httpPut.addHeader("Authorization", "OAuth " + mAccessToken);
						}

						if (jsonData != null) {
							httpPut.setEntity(new StringEntity(jsonData));
						}

						httpPut.addHeader("Accept", ACCEPT);
						httpPut.addHeader("Content-Type", CONTENT_TYPE);
						httpPut.addHeader("User-Agent", USER_AGENT);

						httpRequest = httpPut;
					} else if (method.contains("delete")) {
						HttpDelete httpDelete = new HttpDelete(finalUrl);

						if (null != mAccessToken) {
							httpDelete.addHeader("Authorization", "OAuth " + mAccessToken);
						}

						httpDelete.addHeader("Accept", ACCEPT);
						httpDelete.addHeader("Content-Type", CONTENT_TYPE);
						httpDelete.addHeader("User-Agent", USER_AGENT);

						httpRequest = httpDelete;
					}*/
					// check if activity dead, if yes return
					if (!mActivityLive.get()) {
						if (Config.DEBUG) {
							Log.w(TAG, Config.REQUEST_STOPPED_MSG);
						}

						if (null != mHttpResponseListener) {
							mHttpResponseListener.onCancel(true);
						}
						showLoader(false, runInBg);
						return;
					}

					if (method.equalsIgnoreCase("multipart-post")) {
						statusCode = mHttpURLConnection.getResponseCode();
						response = readStreamFully(mHttpURLConnection.getContentLength(), mHttpURLConnection.getInputStream());
					} else {
						HttpResponse httpResponse = mHttpClient.execute(httpRequest);
						HttpEntity entity = httpResponse.getEntity();
						StatusLine sl = httpResponse.getStatusLine();
						statusCode = sl.getStatusCode();
						response = readStreamFully(entity.getContentLength(), entity.getContent());
					}
					if (Config.DEBUG) {
						logFullResponse(response);
					}
				/*	if (mCacheEnabled && !TextUtils.isEmpty(response)) {
						FileCache.writeFile(cacheFile, response.getBytes());
					}*/
					// check if activity dead, if yes return
					if (!mActivityLive.get()) {
						if (Config.DEBUG) {
							Log.w(TAG, Config.REQUEST_STOPPED_MSG);
						}

						if (null != mHttpResponseListener) {
							mHttpResponseListener.onCancel(true);
						}
						showLoader(false, runInBg);
						return;
					}
				} catch (UnknownHostException e) {
					mError = e.getLocalizedMessage();
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					mError = e.getLocalizedMessage();
					e.printStackTrace();
				} catch (IOException e) {
					mError = e.getLocalizedMessage();
					e.printStackTrace();
				} catch (Exception e) {
					mError = e.getLocalizedMessage();
					e.printStackTrace();
				}
				showLoader(false, runInBg);
				final int sc = statusCode;
				final String resp = !TextUtils.isEmpty(mError) ? mError : response;
				mHandler.post(new Runnable() {
					@Override
					public void run() {
//						if (!LogoutHandler.isLoggingOut(mCategoryColors, resp)) {
							mHttpResponseListener.onResponse(reqCode, sc, resp);
//						}
					}
				});
			}
		};

		new Thread(runnable).start();
	}

	private String readStreamFully(long len, InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();

		long readCount = 0;
		int progress = 0, prevProgress = 0;
		final HttpResponseListener listener = len > 0 ? mHttpResponseListener : null;
		String currLine = null;
		try {
			if (null != listener) {
				listener.onProgressChange(0);
			}

			/* Read until all response is read */
			while ((currLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(currLine + "\n");
				readCount += currLine.length();
				if (!mActivityLive.get()) {
					return stringBuilder.toString();
				}
				if (null != listener) {
					progress = (int) (len / readCount);
					if (progress != prevProgress) {
						prevProgress = progress;
						listener.onProgressChange(progress);
					}
				}
			}

			if (null != listener) {
				listener.onProgressChange(100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

	private void showLoader(final boolean show, final boolean runInBg) {
		if (runInBg || null == mContext || !mActivityLive.get()) {
			return;
		}

		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (null == mContext || !mActivityLive.get()) {
					return;
				}
				if (show) {
					if (null != mLoaderView) {
						mLoaderView.setVisibility(View.VISIBLE);
					} else if (null != mDialog) {
						mDialog.setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								mActivityLive.set(false);
							}
						});
						mDialog.show();
					}
				} else {
					if (null != mLoaderView) {
						mLoaderView.setVisibility(View.GONE);
					} else if (null != mDialog) {
						if (mDialog.isShowing()) {
							mDialog.dismiss();
						}
					}
				}
			}
		});
	}

	public void logFullResponse(String response) {
		final int chunkSize = 4000;
		if (null != response && response.length() > chunkSize) {
			int chunks = (int) Math.ceil((double) response.length() / (double) chunkSize);
			for (int i = 1; i <= chunks; i++) {
				if (i != chunks) {
					Log.i(TAG, "logFullResponse=> " + response.substring((i - 1) * chunkSize, i * chunkSize));
				} else {
					Log.i(TAG, "logFullResponse=> " + response.substring((i - 1) * chunkSize, response.length()));
				}
			}
		} else {
			Log.i(TAG, "logFullResponse=> " + response);
		}
	}

	private void addToAutoRefresh(Runnable runnable) {
		if (null == mAutoRefreshRunnables) {
			mAutoRefreshRunnables = new HashSet<Runnable>();
		}
		mAutoRefreshRunnables.add(runnable);
	}

	public static void executeAutoRefreshIfAny() {
		if (null != mAutoRefreshRunnables && mAutoRefreshRunnables.size() > 0) {
			for (Runnable runnable : mAutoRefreshRunnables) {
				mHandler.post(runnable);
			}
		}
	}

	public static void clearAutoRefreshIfAny() {
		if (null != mAutoRefreshRunnables) {
			mAutoRefreshRunnables.clear();
		}
	}

	public static final int TYPE_NOT_CONNECTED = 0;
	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;

	public static int getConnectivityStatus(Context context) {
		if (null == context) {
			return TYPE_NOT_CONNECTED;
		}
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		if (Config.DEBUG) {
			Log.d(TAG, "getConnectivityStatus=> statu=" + (null == activeNetwork ? "Unknown or not connected" : activeNetwork.getState()));
		}
		if (null != activeNetwork && activeNetwork.isConnectedOrConnecting()) {
			if (Config.DEBUG) {
				Log.d(TAG, "getConnectivityStatus=> type=" + activeNetwork.getType());
			}
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
				return TYPE_WIFI;
			}

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
				return TYPE_MOBILE;
			}
		}
		return TYPE_NOT_CONNECTED;
	}

	// creates a unique boundary based on time stamp
	private static final String BOUNDARY = "===" + System.currentTimeMillis() + "===";
	private static final String LINE_FEED = "\r\n";
	private static String CHARSET = "UTF-8";
	private HttpURLConnection mHttpURLConnection;

	public class MultipartConn {
		private HttpURLConnection httpURLConnection;
		private OutputStream outputStream;
		private PrintWriter printWriter;

		/**
		 * This constructor initializes a new HTTP POST request with content type is set to multipart/form-data
		 * 
		 * @param requestURL
		 * @throws IOException
		 */
		public MultipartConn(String requestURL) throws IOException {
			URL url = new URL(requestURL);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true); // indicates POST method
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			httpURLConnection.setRequestProperty("User-Agent", HttpConnector.USER_AGENT);
			outputStream = httpURLConnection.getOutputStream();
			printWriter = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET), true);
		}

		/**
		 * Adds a form field to the request
		 * 
		 * @param name
		 *            field name
		 * @param value
		 *            field value
		 */
		public void addFormField(String name, String value) {
			printWriter.append("--" + BOUNDARY).append(LINE_FEED);
			printWriter.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
			printWriter.append("Content-Type: text/plain; charset=" + CHARSET).append(LINE_FEED);
			printWriter.append(LINE_FEED);
			printWriter.append(value).append(LINE_FEED);
			printWriter.flush();
		}

		/**
		 * Adds a upload file section to the request
		 * 
		 * @param fieldName
		 *            name attribute in <input type="file" name="..." />
		 * @param uploadFile
		 *            a File to be uploaded
		 * @throws IOException
		 */
		public void addFilePart(String fieldName, File uploadFile) throws IOException {
			String fileName = uploadFile.getName();
			printWriter.append("--" + BOUNDARY).append(LINE_FEED);
			printWriter.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(
					LINE_FEED);
			printWriter.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
			printWriter.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
			printWriter.append(LINE_FEED);
			printWriter.flush();

			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();

			printWriter.append(LINE_FEED);
			printWriter.flush();
		}

		/**
		 * Adds a header field to the request.
		 * 
		 * @param name
		 *            - name of the header field
		 * @param value
		 *            - value of the header field
		 */
		public void addHeaderField(String name, String value) {
			printWriter.append(name + ": " + value).append(LINE_FEED);
			printWriter.flush();
		}

		/**
		 * Completes the request and receives response from the server.
		 * 
		 * @return a list of Strings as response in case the server returned status OK, otherwise an exception is thrown.
		 * @throws IOException
		 */
		public HttpURLConnection prepareConnection() {
			printWriter.append(LINE_FEED).flush();
			printWriter.append("--" + BOUNDARY + "--").append(LINE_FEED);
			printWriter.close();

			return httpURLConnection;
		}
	}
}