package com.dragonflow;

public class FileTransfer {

	private String receiveFolder;
	private String response;
	
	public FileTransfer(){
	}
	
	public void OnDebug(String strOutput)
	{
		System.out.println("------OnDebug:" + strOutput);
	}
	
	//���� ���� �ļ���������ʱ���ص�
    public void onAccept(String host, String DeviceName, String SendType, String filename, int count){
    	System.out.println("------onAccept:" + host + ":" + filename);
    }
    
    //���� ��� �ļ���������ʱ���ص�
    public void onAcceptFolder(String host, String DeviceName, String SendType, String filename, int count){
    	
    }
    
    // �ļ��������ʱ�ص��ļ����� 
    public void onAcceptonFinish(String host, Object[] fileNameList){
    	System.out.println("------onAccept:" + host + ":");
    	
    }
    
    //���ͽ���ʱ�ص�
    // msg: ʧ��-"FAIL", �ɹ�-"SUCCESS", ֹͣ-"STOP"
    public void onFinished(String msg){
    	System.out.println("------onFinished:" + msg);
    }
    
    //�����ļ��лص���  1��ֹͣ����  2��ֹͣ����
    public void onTransfer(long sum,long current,String filename){
    	System.out.println("------onTransfer:" + sum + ":" + current);
    }
    
    //������Ϣʱ�ص�
    public void onRecvMessage(String msg,String host,String hostname){
    	System.out.println("------onRecvMessage:" + host + ":" + msg);
    	response = msg;
    }
    
    public String getText(){
    	return response;
    }
    
    //��ʼ�����ļ�����
    public native int listenFileSend(int port);
    //ֹͣ�����ļ�����
    public native int stopListen();
    //������Ϣ
    public native int sendMessage(String host,int port,String message, String hostname, String sendtype);
    //�����ļ�
    public native int sendFile(String host,int port,String filename, String hostname, String sendtype);
    //���Ͷ���ļ� 
    public native int sendMultiFiles(String host, int port, Object[] filelist, String hostname, String sendtype);
    //�����ļ���
    public native int sendfolder(String host, int port, String folderName, String hostname, String sendtype);
    //ֹͣ�ļ����͡�����
    public native int stopTransfer(int type);
    //�Ƿ�����ļ�  "REJECT", "REJECTBUSY", "/mnt/sdcard/";
    public native int replyAccept(String strReply);

    static {
        System.loadLibrary("udt");
    }
}
