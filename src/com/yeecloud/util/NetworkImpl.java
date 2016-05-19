package com.yeecloud.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkImpl {
	public static HttpClient getHttpClient(Context ctx) {
		String networkTypeName = getNetworkTypeName(ctx);
		if (networkTypeName == null) {
			return null;
		}
		HttpClient client = null;
		if (isCmwapType(ctx)) {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);
			HttpConnectionParams.setSocketBufferSize(httpParams, 100 * 1024);
			HttpClientParams.setRedirecting(httpParams, true);
			HttpHost host = new HttpHost("10.0.0.172", 80);
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
			client = new DefaultHttpClient(httpParams);
		} else {
			client = new DefaultHttpClient();
			HttpParams httpParams = client.getParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					30 * 1000);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000);
		}
		return client;
	}


	private static boolean isCmwapType(Context ctx) {
		ConnectivityManager mgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
		String extraInfo = activeNetworkInfo.getExtraInfo();
		if (extraInfo == null) {
			return false;
		}
		return "cmwap".equalsIgnoreCase(extraInfo)
				|| "3gwap".equalsIgnoreCase(extraInfo)
				|| "uniwap".equalsIgnoreCase(extraInfo);
	}

	public static String getNetworkTypeName(Context ctx) {
		ConnectivityManager mgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return null;
		}
//		String extraInfo = activeNetworkInfo.getExtraInfo();
//		if (extraInfo != null && extraInfo.length() > 0) {
//			return extraInfo;
//		}
		return activeNetworkInfo.getTypeName();
	}
	
	class SSLSocketFactoryEx extends SSLSocketFactory 
	{

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			//super(truststore);

			TrustManager tm = new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {

				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}

		@Override
		public String[] getDefaultCipherSuites() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getSupportedCipherSuites() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String s, int i) throws IOException,
				UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(String s, int i, InetAddress inetaddress,
				int j) throws IOException, UnknownHostException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(InetAddress inetaddress, int i)
				throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Socket createSocket(InetAddress inetaddress, int i,
				InetAddress inetaddress1, int j) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public  String requestHTTPSPage(String mUrl,Context ctx)
	{
		InputStream ins = null;
		String result = "";
		try {
			ins = ctx.getAssets().open("app_pay.cer");
			CertificateFactory cerFactory = CertificateFactory
					.getInstance("X.509");
			Certificate cer = cerFactory.generateCertificate(ins);
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(null, null);
			keyStore.setCertificateEntry("trust", cer);

			SSLSocketFactory socketFactory = new SSLSocketFactoryEx(keyStore);
			Scheme sch = new Scheme("https", (SocketFactory) socketFactory, 443);
			HttpClient mHttpClient = new DefaultHttpClient();
			mHttpClient.getConnectionManager().getSchemeRegistry()
					.register(sch);

			BufferedReader reader = null;
			try {
				Logger.d("requestHTTPSPage", "executeGet is in,murl:" + mUrl);
				HttpGet request = new HttpGet();
				request.setURI(new URI(mUrl));
				HttpResponse response = mHttpClient.execute(request);
				if (response.getStatusLine().getStatusCode() != 200) {
					request.abort();
					return result;
				}

				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuffer buffer = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				result = buffer.toString();
				Logger.d("requestHTTPSPage", "mUrl=" + mUrl + "\nresult = " + result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		} 
		finally 
		{
			try 
			{
				if (ins != null)
					ins.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
}
