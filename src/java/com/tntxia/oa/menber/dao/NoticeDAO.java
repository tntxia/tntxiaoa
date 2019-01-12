package com.tntxia.oa.menber.dao;

import java.text.SimpleDateFormat;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.menber.Notice;
import com.tntxia.web.util.DatasourceStore;

public class NoticeDAO {

	private DBManager dbManager = new DBManager(
			DatasourceStore.getDatasource("default"));

	public void add(Notice notice) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		notice.setPro_time(DateUtil.getCurrentDate());
		String sql = "insert into notice(pro_man,pro_time,content) values('"
				+ notice.getPro_man() + "','"
				+ sdf.format(notice.getPro_time()) + "','"
				+ notice.getContent() + "')";

		dbManager.update(sql);

	}

}
