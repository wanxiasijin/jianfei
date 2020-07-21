/*
    ShengDao Android Client, DBManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.chenganrt.smartSupervision.utils.db;

import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.common.Constants;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;
import com.chenganrt.smartSupervision.utils.db.gen.DaoMaster;
import com.chenganrt.smartSupervision.utils.db.gen.DaoSession;
import com.chenganrt.smartSupervision.utils.db.gen.SearchRecordDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBManager {

	private volatile static DBManager instance;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private SearchRecordDao searchRecordDao;

	/**
	 * [获取DBManager实例，单例模式实现]
	 *
	 * @return
	 */
	public static DBManager getInstance() {
		if (instance == null) {
			synchronized (DBManager.class) {
				if (instance == null) {
					instance = new DBManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 构造方法
	 */
	private DBManager() {
		if(daoSession == null){
			if(daoMaster == null){
				DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(BaseApplication.getApp(), Constants.DB_NAME, null);
				daoMaster = new DaoMaster(helper.getWritableDatabase());
			}
			daoSession = daoMaster.newSession();
			searchRecordDao = daoSession.getSearchRecordDao();
		}
	}

	public DaoMaster getDaoMaster() {
		return daoMaster;
	}

	public void setDaoMaster(DaoMaster daoMaster) {
		this.daoMaster = daoMaster;
	}

	public DaoSession getDaoSession() {
		return daoSession;
	}

	public void setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
	}

	public void insertSearchRecord(SearchRecord searchRecord){
		searchRecordDao.insert(searchRecord);
	}

	public List<SearchRecord> getSearchRecords() {
		QueryBuilder<SearchRecord> qb = searchRecordDao.queryBuilder();
		return qb.list();
	}

	public void clearSearchRecords() {
		searchRecordDao.deleteAll();
	}
}
