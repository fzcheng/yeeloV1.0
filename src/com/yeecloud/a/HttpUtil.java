package com.yeecloud.a;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.io.OutputStream;import java.io.UnsupportedEncodingException;import java.net.HttpURLConnection;import java.net.MalformedURLException;import java.net.URL;import java.net.URLEncoder;import java.security.KeyManagementException;import java.security.NoSuchAlgorithmException;import java.security.SecureRandom;import java.util.Iterator;import java.util.Map;import java.util.logging.Level;import java.util.logging.Logger;import javax.net.ssl.HostnameVerifier;import javax.net.ssl.HttpsURLConnection;import javax.net.ssl.SSLContext;import javax.net.ssl.TrustManager;public class HttpUtil {	public static int a = 6000;	  public static int b = 6000;	  public static String c = "UTF-8";	  public static int d = -1;	  private static HostnameVerifier e = new D();	  public static CallResult a(String paramString1, Map paramMap, String paramString2)	  {	    if (a(paramString1))	      a();	    HttpURLConnection localHttpURLConnection = null;	    OutputStream localOutputStream = null;	    InputStreamReader localInputStreamReader = null;	    BufferedReader localBufferedReader = null;	    URL localURL = null;	    try	    {	      localURL = new URL(paramString1);	      localHttpURLConnection = (HttpURLConnection)localURL.openConnection();	      Object localObject1;	      if (paramMap != null)	      {	        localObject1 = paramMap.keySet().iterator();	        while (((Iterator)localObject1).hasNext())	        {	          String localObject2 = (String)((Iterator)localObject1).next();	          String str = (String)paramMap.get(localObject2);	          localHttpURLConnection.setRequestProperty((String)localObject2, str);	        }	      }	      else	      {	        localHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");	      }	      localHttpURLConnection.setRequestMethod("POST");	      localHttpURLConnection.setDoInput(true);	      localHttpURLConnection.setDoOutput(true);	      localHttpURLConnection.setUseCaches(false);	      localHttpURLConnection.setConnectTimeout(a);	      localHttpURLConnection.setReadTimeout(b);	      localOutputStream = localHttpURLConnection.getOutputStream();	      localOutputStream.write(paramString2.getBytes(c));	      localOutputStream.flush();	      Object localObject2 = new StringBuilder();	      try	      {	        localInputStreamReader = new InputStreamReader(localHttpURLConnection.getInputStream(), c);	      }	      catch (IOException localIOException2)	      {	        localInputStreamReader = new InputStreamReader(localHttpURLConnection.getErrorStream(), c);	        localIOException2.printStackTrace();	      }	      finally	      {	        if (localInputStreamReader != null)	        {	          localBufferedReader = new BufferedReader(localInputStreamReader);	          localObject2 = new StringBuilder();	          while ((localObject1 = localBufferedReader.readLine()) != null)	            ((StringBuilder)localObject2).append((String)localObject1);	        }	      }	      CallResult localb = new CallResult(localHttpURLConnection.getResponseCode(), localHttpURLConnection.getResponseMessage(), ((StringBuilder)localObject2).toString());	      return localb;	    }	    catch (MalformedURLException localMalformedURLException)	    {	      localMalformedURLException.printStackTrace();	      CallResult localb = new CallResult(d, localMalformedURLException.getMessage());	      return localb;	    }	    catch (IOException localIOException1)	    {	      localIOException1.printStackTrace();	      CallResult localb = new CallResult(d, localIOException1.getMessage());	      return localb;	    }	    catch (Exception localException)	    {	      localException.printStackTrace();	      CallResult localb = new CallResult(d, localException.getMessage());	      return localb;	    }	    finally	    {	      if (localBufferedReader != null)	        try	        {	          localBufferedReader.close();	        }	        catch (IOException localIOException15)	        {	        }	      if (localInputStreamReader != null)	        try	        {	          localInputStreamReader.close();	        }	        catch (IOException localIOException16)	        {	        }	      if (localOutputStream != null)	        try	        {	          localOutputStream.close();	        }	        catch (IOException localIOException17)	        {	        }	      if (localHttpURLConnection != null)	        localHttpURLConnection.disconnect();	    }	    //throw localObject4;	  }	  public static CallResult a(String paramString, Map paramMap1, Map paramMap2)	  {	    return a(paramString, paramMap1, a(paramMap2));	  }	  public static boolean a(String paramString)	  {	    return paramString.startsWith("https");	  }	  private static void a()	  {	    try	    {	      TrustManager[] arrayOfTrustManager = { new E() };	      SSLContext localSSLContext = SSLContext.getInstance("TLS");	      localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());	      HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());	      HttpsURLConnection.setDefaultHostnameVerifier(e);	    }	    catch (KeyManagementException localKeyManagementException)	    {	      Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, localKeyManagementException);	    }	    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)	    {	      Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, localNoSuchAlgorithmException);	    }	  }	  public static String a(Map paramMap)	  {	    if ((paramMap == null) || (paramMap.isEmpty()))	      return "";	    String str1 = "";	    Iterator localIterator = paramMap.keySet().iterator();	    while (localIterator.hasNext())	    {	      String str2 = (String)localIterator.next();	      String str3 = (String)paramMap.get(str2);	      try	      {	        str1 = str1 + URLEncoder.encode(str2, c) + "=" + URLEncoder.encode(String.valueOf(str3), c);	      }	      catch (UnsupportedEncodingException localUnsupportedEncodingException)	      {	      }	      str1 = str1 + "&";	    }	    return str1.substring(0, str1.length() - 1);	  }}