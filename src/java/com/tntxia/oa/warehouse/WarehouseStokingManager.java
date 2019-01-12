package com.tntxia.oa.warehouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.dao.PdDao;
import com.tntxia.oa.warehouse.PdConsole;

import infocrmdb.infocrmdb;

public class WarehouseStokingManager {

	private DBConnection einfodb = new DBConnection();

	/**
	 * 获取仓库产品列表
	 * 
	 * @param filter
	 * @return
	 */
	public List<String[]> getStokingModelList() {

		String sql = "select  distinct pro_model,pro_addr from warehouse where pro_num !=0 ";

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);

		List<String[]> res = new ArrayList<String[]>();

		try {
			while (rs.next()) {
				String[] arr = new String[2];
				arr[0] = rs.getString("pro_model");
				arr[1] = rs.getString("pro_addr");
				res.add(arr);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return res;

	}

	/**
	 * 采购总数量 A数量
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getCgTotalNum(String pro_model) {
		String sql = "select sum(num) from cgpro where epro='" + pro_model
				+ "' and ddid in (select id from procure where l_spqk='已入库' or  l_spqk='全部退货' or  l_spqk='部分退货')";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return res;

	}

	/**
	 * 销售退货总数量 B数量
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getThProTotalNum(String pro_model) {
		String sql = "select sum(num) from th_pro where epro='" + pro_model
				+ "' and ddid in (select id from th_table where state='已入库')";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return res;

	}

	/**
	 * 销售单总数量 C数量
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getDdProTotalNum(String pro_model) {
		String sql = "select sum(num) from ddpro where epro='"
				+ pro_model
				+ "' and ddid in (select id from subscribe where state='已发运' or state='已出库' or state='全部退货' or state='部分退货')";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return res;

	}

	/**
	 * 采购退货单总量 D数量
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getCgReturnNum(String pro_model) {
		String sql = "select sum(num) from th_pro_supplier where epro='"
				+ pro_model
				+ "' and ddid in (select id from th_table_supplier where state='已出库')";
		ResultSet rs = null;
		int res = 0;
		try {
			rs = einfodb.executeQuery(sql);
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(sql);
			e.printStackTrace();
		} finally {
		}

		return res;
	}

	/**
	 * 采购入库单总量 数量E
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getRkTotalNum(String pro_model) {
		String sql = "select sum(pro_num) from rkhouse where pro_model='"
				+ pro_model
				+ "' and pro_rk_num in (select id from in_warehouse where states='已入库')";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return res;
	}

	/**
	 * 销售退货入库单总量 数量F
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getSaleReturnOuthouseTotalNum(String pro_model) {
		String sql = "select sum(abs(pro_num)) from outhouse where pro_model='"
				+ pro_model
				+ "' and pro_fynum like 'R%' and pro_fynum not like 'RS%'";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return res;
	}

	/**
	 * 销售出库单总量 数量G
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getOuthouseTotalNum(String pro_model) {
		String sql = "select sum(pro_num) from outhouse where pro_model='"
				+ pro_model + "' and pro_fynum like 'K%'";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			einfodb.closeRs(rs);
		}
		return res;
	}

	/**
	 * 销售出库单总量 数量H
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getOuthouseCgReturnTotalNum(String pro_model) {
		String sql = "select sum(pro_num) from outhouse where pro_model='"
				+ pro_model + "' and pro_fynum like 'RS%'";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error_sql:" + sql);
			e.printStackTrace();
		} finally {
		}

		return res;
	}

	/**
	 * 当前实际库存 数量I
	 * 
	 * @param pro_model
	 * @return
	 */
	public int getWarehouseTotalNum(String pro_model, String pro_addr) {
		String sql = "select sum(pro_num) from warehouse where pro_model='"
				+ pro_model + "' and pro_addr='" + pro_addr + "'";
		ResultSet rs = einfodb.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}

		return res;
	}

	private void exportPdExcel(List<PdItem> items, String path) {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		String[] header = new String[] { "型号", "采购单数量总和", "销售退货数量总和",
				"销售单数量总和", "采购退货数量总和", "采购入库单数量总和", "销售退货入库单数量总和", "销售出库单数量总和",
				"采购退货出库单数量总和", "盘点结果", "实际库存总和" };

		HSSFRow rowHeader = sheet.createRow(0);

		for (int i = 0; i < header.length; i++) {
			String h = header[i];
			HSSFCell cell = rowHeader.createCell(i);
			sheet.setColumnWidth(i, 25 * 256);
			cell.setCellValue(h);
		}

		for (int i = 0; i < items.size(); i++) {
			PdItem item = items.get(i);
			int a = item.getA();
			int b = item.getB();
			int c = item.getC();
			int d = item.getD();

			int calResult = (a + b) - (c + d);

			HSSFRow row = sheet.createRow(i + 1);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue(item.getPro_model());
			cell = row.createCell((short) 1);
			cell.setCellValue(a);
			cell = row.createCell((short) 2);
			cell.setCellValue(b);

			cell = row.createCell((short) 3);
			cell.setCellValue(c);

			cell = row.createCell((short) 4);
			cell.setCellValue(d);

			cell = row.createCell((short) 5);
			cell.setCellValue(item.getE());

			cell = row.createCell((short) 6);
			cell.setCellValue(item.getF());

			cell = row.createCell((short) 7);
			cell.setCellValue(item.getG());
			cell = row.createCell((short) 8);
			cell.setCellValue(item.getH());

			cell = row.createCell((short) 9);
			cell.setCellValue(calResult);

			cell = row.createCell((short) 10);
			cell.setCellValue(item.getI());

		}

		try {
			FileOutputStream output = new FileOutputStream(new File(path));
			wb.write(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addPdItemToDB(PdItem item) {
		
		String sql;
		sql = "insert into pd(pro_model,pro_addr,a,b,c,d,e,f,g,h,i,status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(item.getPro_model());
		params.add(item.getPro_addr());
		params.add(item.getA());
		params.add(item.getB());
		params.add(item.getC());
		params.add(item.getD());
		params.add(item.getE());
		params.add(item.getF());
		params.add(item.getG());
		params.add(item.getH());
		params.add(item.getI());
		params.add(item.getStatus());
		einfodb.executeUpdate(sql, params);

	}

	/**
	 * 获取盘点的列表
	 * 
	 * @param pro_addr
	 * @param status
	 * @return
	 */
	public List<PdItem> getPdItemList(int status,
			String pro_model) {
		String sql = "select * from pd where status = " + status;

		if (pro_model != null && pro_model.trim().length() > 0) {
			sql += " and pro_model like '%" + pro_model + "%'";
		}
		ResultSet rs = einfodb.executeQuery(sql);
		List<PdItem> res = new ArrayList<PdItem>();
		try {
			while (rs.next()) {
				PdItem pdItem = new PdItem();
				pdItem.setPro_model(rs.getString("pro_model"));
				pdItem.setPro_addr(rs.getString("pro_addr"));
				pdItem.setA(rs.getInt("a"));
				pdItem.setB(rs.getInt("b"));
				pdItem.setC(rs.getInt("c"));
				pdItem.setD(rs.getInt("d"));
				pdItem.setE(rs.getInt("e"));
				pdItem.setF(rs.getInt("f"));
				pdItem.setG(rs.getInt("g"));
				pdItem.setH(rs.getInt("h"));
				pdItem.setI(rs.getInt("i"));
				pdItem.setStatus(rs.getInt("status"));
				res.add(pdItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			einfodb.closeRs(rs);
		}
		return res;
	}

	/**
	 * 库存盘点
	 * 
	 * @param pro_addr
	 *            仓库地址
	 * @param filePath
	 *            导出Excel文件的路径
	 */
	public void pd() {

		if (PdConsole.status == 1)
			return;

		PdConsole.status = 1;

		PdDao pdDao = new PdDao();
		pdDao.deleteAll();

		List<String[]> models = getStokingModelList();
		for (int i = 0; i < models.size(); i++) {

			// 修改进度
			PdConsole.process = i * 100.0 / models.size();

			PdItem pdItem = new PdItem();
			String pro_model = models.get(i)[0];
			String pro_addr = models.get(i)[1];
			System.out.println("正在盘点型号：" + pro_model);
			pdItem.setPro_model(pro_model);
			pdItem.setPro_addr(pro_addr);
			int a = getCgTotalNum(pro_model);
			pdItem.setA(a);
			int b = getThProTotalNum(pro_model);
			pdItem.setB(b);
			int c = getDdProTotalNum(pro_model);
			pdItem.setC(c);
			int d = getCgReturnNum(pro_model);
			pdItem.setD(d);

			int e = getRkTotalNum(pro_model);
			pdItem.setE(e);
			int f = getSaleReturnOuthouseTotalNum(pro_model);
			pdItem.setF(f);
			int g = getOuthouseTotalNum(pro_model);
			pdItem.setG(g);
			int h = getOuthouseCgReturnTotalNum(pro_model);
			pdItem.setH(h);

			int I = getWarehouseTotalNum(pro_model, pro_addr);
			pdItem.setI(I);

			int i1 = (a + b) - (g + h);
			int i2 = (e + f) - (c + d);

			int i3 = (a + b) - (c + d);

			// 如果i1=i2，而且库存>=0为正常的盘点
			if (i1 == i2 && I >= 0 && i3 == I) {
				pdItem.setStatus(1);
			} else {
				pdItem.setStatus(2);
			}

			addPdItemToDB(pdItem);

		}

		PdConsole.process = 100;
		PdConsole.status = 2;

		einfodb.close();

	}

	public void exportPd(String pro_addr, int startIndex, int endIndex,
			String path, int status) {
		List<PdItem> pdItemList = getPdItemList(status, null);

		List<PdItem> exportPdItemList;
		if (startIndex == 0 && endIndex == 0) {
			exportPdItemList = pdItemList;
		} else {
			if (endIndex <= pdItemList.size()) {
				exportPdItemList = pdItemList.subList(startIndex, endIndex);
			} else {
				exportPdItemList = pdItemList.subList(startIndex,
						pdItemList.size());
			}
		}

		String exportPath;

		if (status == 1) {
			exportPath = path + "\\pd_normal.xls";
		} else {
			exportPath = path + "\\pd_exception.xls";
		}

		exportPdExcel(exportPdItemList, exportPath);
	}

	public static void main(String args[]) {
		WarehouseStokingManager manager = new WarehouseStokingManager();
		manager.pd();

	}

}
