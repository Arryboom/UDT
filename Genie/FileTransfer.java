package com.dragonflow;

import android.os.Handler;

public class FileTransfer {
	
	public interface Callback
	{		
		public abstract void onAccept(String szIpAddr, String szHostName, String szSendType, String szFileName, int nFileCount);
		public abstract void onSendFinished(String szMsg);
		public abstract void onRecvFinished(String sztrMsg);
		public abstract void onSendTransfer(long nFileTotalSize, long nCurrent, String szFileName);
		public abstract void onRecvTransfer(long nFileTotalSize, long nCurrent, String szFileName);
		public abstract void onRecvMessage(String szIpAddr, String szHostName, String szMsg);
	}
	
	
	public FileTransfer(Callback callback)
	{
		mCallback = callback;
		mCore = init(this);
	}
	public void ListenFileSend(int port)
	{
		listenFileSend(mCore, port);
	}
	public void SendMessage(String host,int port,String message, String hostname, String sendtype){
		sendMessage(mCore, host, port, message, hostname, sendtype);
	}
	public void SendUniFile(String host, int port, String folderName, String hostname, String sendtype){
		sendFile(mCore, host, port, folderName, hostname, sendtype);
	}
	public void SendMultiFiles(String host, int port, Object[] filelist, String hostname, String sendtype){
		sendMultiFiles(mCore, host, port, filelist, hostname, sendtype);
	}
	public void SendFolder(String host, int port, String folderName, String hostname, String sendtype){
		sendfolder(mCore, host, port, folderName, hostname, sendtype);
	}
	public void ReplyAccept(String strReply)
	{
		replyAccept(mCore, strReply);
	}
	public void StopTransfer(int nType)
	{
		stopTransfer(mCore, nType);
	}
	public void StopListen()
	{
		stopListen(mCore);
	}
	
	
	// ������Ϣ���ص�
	public void onDebug(String strOutput)
	{
		System.out.println("------OnDebug:" + strOutput);
	}
	
	//���� ���� �ļ���������ʱ���ص�
	public void onAccept(String host, String DeviceName, String SendType, String filename, int count)
	{
		System.out.println("------onAccept:" + host + ":" + filename);
		FileTransfer.this.hook_onAccept(host, DeviceName, SendType, filename, count);
	}
	void hook_onAccept(String host, String DeviceName, String SendType, String filename, int count)
	{
		if (mCallback != null) {
			mCallback.onAccept(host, DeviceName, SendType, filename, count);
		}
	}
	
    //���� ��� �ļ���������ʱ���ص�
	public void onAcceptFolder(String host, String DeviceName, String SendType, String filename, int count)
	{
		System.out.println("------onAccept:" + host + ":" + filename);
		FileTransfer.this.hook_onAcceptFolder(host, DeviceName, SendType, filename, count);
    }
	void hook_onAcceptFolder(String host, String DeviceName, String SendType, String filename, int count)
	{
		if (mCallback != null) {
			mCallback.onAccept(host, DeviceName, SendType, filename, count);
		}
	}
	
    // �ļ��������ʱ�ص��ļ����� 
    public void onAcceptonFinish(String host, Object[] fileNameList)
    {
    	System.out.println("------onAccept:" + host + ":");
    	FileTransfer.this.hook_onAcceptonFinish(host, fileNameList);
    }
    void hook_onAcceptonFinish(String host, Object[] fileNameList)
	{

	}
    //���ͽ���ʱ�ص�
    // msg: ʧ��-"FAIL", �ɹ�-"SUCCESS", ֹͣ-"STOP"
    public void onFinished(String msg)
    {
    	System.out.println("------onFinished:" + msg);
    	FileTransfer.this.hook_onFinished(msg);
    }
    void hook_onFinished(String msg)
    {
    	if (mCallback != null)
    	{
    		mCallback.onRecvFinished(msg);
    	}
    }
    
    //�����ļ��лص���  1��ֹͣ����  2��ֹͣ����
    public void onTransfer(long sum,long current,String filename)
    {
    	System.out.println("------onTransfer:" + sum + ":" + current);
    	FileTransfer.this.hook_onTransfer(sum, current, filename);
    }
    void hook_onTransfer(long sum,long current,String filename)
    {
    	if (mCallback != null)
    	{
    		mCallback.onRecvTransfer(sum, current, filename);
    	}
    }
    
    //������Ϣʱ�ص�
    public void onRecvMessage(String msg, String host,String hostname)
    {
    	FileTransfer.this.hook_onRecvMessage(host, hostname, msg);
    }
    void hook_onRecvMessage(String szIpAddr, String szHostName, String szMsg)
    {
    	System.out.println("------onRecvMessage:" + szIpAddr + ":" + szMsg);
		if (mCallback != null) {
			mCallback.onRecvMessage(szIpAddr, szHostName, szMsg);
		}
    }
    
    
    // ��ʼ��
	private static native long init(Object delegate);
	// ����
	private static native void destroy(long core);
    //��ʼ�����ļ�����
	private static native int listenFileSend(long core, int port);
    //������Ϣ
	private static native int sendMessage(long core, String host,int port,String message, String hostname, String sendtype);
    //�����ļ�
	private static native int sendFile(long core, String host,int port,String filename, String hostname, String sendtype);
    //���Ͷ���ļ� 
	private static native int sendMultiFiles(long core, String host, int port, Object[] filelist, String hostname, String sendtype);
    //�����ļ���
	private static native int sendfolder(long core, String host, int port, String folderName, String hostname, String sendtype);
	// �Ƿ�����ļ�  "REJECT", "REJECTBUSY", "/mnt/sdcard/";
	private static native int replyAccept(long core, String strReply);
    //ֹͣ�ļ����͡�����
	private static native int stopTransfer(long core, int type);
	// ֹͣ�����ļ�����
	private static native int stopListen(long core);
    
	Callback mCallback = null;
	boolean mDisposed = false;
	boolean mStarted = false;
	Handler mDisp = new Handler();
	long mCore = 0;
	private String receiveFolder;
	private String response;

    static {
        System.loadLibrary("udt");
    }
}
