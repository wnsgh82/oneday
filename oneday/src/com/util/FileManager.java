package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

public class FileManager {
	/**
	 * ���� �ٿ�ε� �޼ҵ�
	 * @param saveFilename ��������������ϸ�
	 * @param originalFilename Ŭ���̾�Ʈ�����ε������ϸ�
	 * @param pathname ����������Ȱ��
	 * @param resp HttpServletResponse ��ü
	 * @return �ٿ�ε强������
	 */
	public static boolean doFiledownload(String saveFilename, String originalFilename, String pathname, HttpServletResponse resp) {
		boolean flag=false;
		
		BufferedInputStream bis = null;
		OutputStream os = null;
		
		try {
			originalFilename=new String(	originalFilename.getBytes("euc-kr"), "8859_1");
			pathname=pathname+File.separator+saveFilename;
			File f=new File(pathname);
			if(! f.exists()) {
				return flag;
			}
			
			// Ŭ���̾�Ʈ���� ������ ����Ÿ���� ��Ʈ���̶�� ����
			resp.setContentType("application/octet-stream");
			
			// ���ϸ��� ����� �Ǿ ����
			resp.setHeader("Content-disposition", "attachment;filename="+originalFilename);
			
			// Ŭ���̾�Ʈ���� ������ ������ ����
			byte[] b = new byte[1024];
			bis = new BufferedInputStream(new FileInputStream(f));
			
			// Ŭ���̾�Ʈ���� ������ ��� ��Ʈ��
			os=resp.getOutputStream();
			
			int n;
			while((n = bis.read(b)) != -1) {
				os.write(b, 0, n);
			}
			os.flush();
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (Exception e2) {
				}
			}
			
			if(os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * ���� �̸� ����(����Ͻú��ʳ�����)
	 * @param pathname ����������� ���
	 * @param filename ������ ���ϸ�
	 * @return ���ο����ϸ�
	 */
	public static String doFilerename(String pathname, String filename) {
		String newname="";
		
    	String fileExt = filename.substring(filename.lastIndexOf("."));
    	String s = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", 
				 Calendar.getInstance());
    	s += System.nanoTime();
    	s += fileExt;
    	
    	try {
	    	File f1=new File(pathname+File.separator+filename);
	    	File f2=new File(pathname+File.separator+s);
	    	f1.renameTo(f2);
	    	
	    	newname = s;
    	}catch(Exception e) {
    	}
		
		return newname;
	}
	
	/**
	 * ���� ����
	 * @param pathname ������ ����� ���
	 * @param filename ������ ���ϸ�
	 * @return ���� ���� ���� ����
	 */
	public static boolean doFiledelete(String pathname, String filename) {
		String path=pathname+File.separator+filename;
		
		return doFiledelete(path);
	}

	/**
	 * ���� ����
	 * @param pathname ������ ����� ��� �� ������ ���ϸ�
	 * @return ���� ���� ���� ����
	 */
	public static boolean doFiledelete(String pathname) {
		try {
			File f=new File(pathname);
			
			if(! f.exists()) // ������ ������
				return false;
			
			f.delete();
		} catch (Exception e) {
		}
		
		return true;
	}
	
}
