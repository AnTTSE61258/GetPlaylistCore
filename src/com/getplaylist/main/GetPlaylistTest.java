package com.getplaylist.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetPlaylistTest {

	@Test
	public void testGetNhaccuatui() {
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc"
					+ "-hay-cua-cac-nhom-nhac-vol-3-va.zFriPcrlzd0C.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(20, size);
	}
	@Test
	public void testGetZingMp3() {
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://mp3.zing.vn/album/Top-100-Bai-Hat-Nhac-Tre"
					+ "-Hay-Nhat-Various-Artists/ZWZB969E.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(100, size);
	}
	@Test
	public void testGetZingMp3WithSpaceInURL() {
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://mp3.zing.vn/album/Top-100- Bai-Hat-Nhac-Tre"
					+ "-Hay-Nhat-Various-Artists/ZWZB969E.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(100, size);
	}
	@Test
	public void testGetNhaccuatuiWithSpaceInURL() {
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://www. nhaccuatui.com/ playlist/tuyen-tap-nhung-ca-khuc"
					+ "-hay-cua-cac -nhom-nhac -vol-3-va.zFriPc rlzd0C.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(20, size);
	}
	@Test
	public void testGetNhaccuatuiWithMissHttp(){
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc"
					+ "-hay-cua-cac-nhom-nhac-vol-3-va.zFriPcrlzd0C.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(20, size);
	}
	@Test
	public void testGetZingMp3WithMissHttp(){
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("mp3.zing.vn/album/Top-100-Bai-Hat-Nhac-Tre"
					+ "-Hay-Nhat-Various-Artists/ZWZB969E.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(100, size);
	}
	@Test
	public void testGetNhaccuatuiWrongURL(){
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc"
					+ "-hay-cua-cac-nhom-nhac-vol-3-va.zFriPwecrlzd0C.html").size();
			System.out.println(size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, size);
	}
	//some thing still wrong
	@Test
	public void testGetZingMp3WrongURL(){
		int size = 0;
		GetPlaylist getPlaylist = new GetPlaylist();
		try {
			size = getPlaylist.getMp3List("http://mp3.zing.vn/album/Top-100-Nhac"
					+ "-Hoa-Tau-Co-Dien-Hay-Nhat-Various-ewerfuck youArtists/ZWZB96EW.html").size();
			System.out.println(size);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(99, size);
	}
	
	
}
