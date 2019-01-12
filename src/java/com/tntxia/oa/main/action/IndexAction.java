package com.tntxia.oa.main.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.tntxia.jdbc.SQLExecutor;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.DatasourceStore;

public class IndexAction extends HandlerWithHeader{
	
	private int getTotalTodo(String username) throws SQLException{
		
		SQLExecutorSingleConn sqlExec = this.getSQLExecutorSingleConn();

		SimpleDateFormat simple = new SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		
		String strSQL4 = "select count(*) from sample inner join sam_pro on sample.id = sam_pro.ddid where state='�ѷ���' and  man=?  and num!=pro_snum and pro_r_date<='"
				+ currentDate + "'";
		int num4 = sqlExec.queryForInt(strSQL4, new Object[]{username});
		
		String strSQL5 = "select count(*) from subscribe where state='������' and spman=? or state='������' and cwman=? and fif='��'";
		int num5 = sqlExec.queryForInt(strSQL5, new Object[]{username,username});
		
		String strSQL6 = "select count(*) from workreport where w_states='������' and w_spman=?";
		int num6 = sqlExec.queryForInt(strSQL6, new Object[]{username});
		
		
		String strSQL7 = "select count(*) from work_report_rd where w_states='������' and w_spman=?";
		int num7 = sqlExec.queryForInt(strSQL7, new Object[]{username});
		
		String strSQL8 = "select count(*) from work_report_r where w_states='������' and w_spman=?";
		int num8 = sqlExec.queryForInt(strSQL8, new Object[]{username});
		
		String strSQL9 = "select count(*) from workreport_r where w_states='������' and w_spman=?";
		int num9 = sqlExec.queryForInt(strSQL9, new Object[]{username});
		
		
		String strSQL10 = "select count(*) from procure  where   l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num10 = sqlExec.queryForInt(strSQL10, new Object[]{username,username,username});
		
		String strSQL11 = "select count(*) from lending  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		int num11 = sqlExec.queryForInt(strSQL11, new Object[]{username,username,username});
		
		String strSQL12 = "select count(*) from lending_m  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num12 = sqlExec.queryForInt(strSQL12, new Object[]{username,username,username});
		String strSQL13 = "select count(*) from payment_sp  where  p_states='������' and   p_spman=? or   p_states='������' and fif='��' and   fspman=? or  firspif='��'  and   p_states='������' and   firspman=?  or  fourspif='��'  and   p_states='������' and   fourspman=?";
		
		int num13 = sqlExec.queryForInt(strSQL13, new Object[]{username,username,username,username});
		
		String strSQL14 = "select count(*) from workm where  rwzman=?  and (states='���������' or states='���������') ";
		
		int num14 = sqlExec.queryForInt(strSQL14, new Object[]{username});
		
		String strSQL15 = "select count(*) from work_report where w_states='������' and w_spman=?";
		int num15 = sqlExec.queryForInt(strSQL15, new Object[]{username});
		String strSQL16 = "select count(*) from work_report_rdm where w_states='������' and w_spman=?";
		
		int num16 =  sqlExec.queryForInt(strSQL16, new Object[]{username});
		
		String strSQL17 = "select count(*) from work_report_rm where w_states='������' and w_spman=?";
		int num17 = sqlExec.queryForInt(strSQL17, new Object[]{username});
		
		String strSQL18 = "select count(*) from month_report_rdm where w_states='������' and w_spman=?";
		
		int num18 = sqlExec.queryForInt(strSQL18, new Object[]{username});
		
		String strSQL19 = "select count(*) from m_zzgl  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num19 = sqlExec.queryForInt(strSQL19, new Object[]{username,username,username});
		
		String strSQL20 = "select count(*) from m_jcgl  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num20 = sqlExec.queryForInt(strSQL20, new Object[]{username,username,username});
		
		String strSQL21 = "select count(*) from m_stipend  where l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num21 = sqlExec.queryForInt(strSQL21, new Object[]{username,username,username});
		
		String strSQL22 = "select count(*) from m_evection  where l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		int num22 = sqlExec.queryForInt(strSQL22, new Object[]{username,username,username});
		
		String strSQL23 = "select count(*) from procure_of  where l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num23 = sqlExec.queryForInt(strSQL23, new Object[]{username,username,username});
		
		String strSQL25 = "select count(*) from m_document  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		int num25 = sqlExec.queryForInt(strSQL25, new Object[]{username,username,username});
		
		String strSQL26 = "select count(*) from m_commision  where  l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num26 =  sqlExec.queryForInt(strSQL26, new Object[]{username,username,username});
		String strSQL27 = "select count(*) from m_spbm  where l_spqk='������' and   l_spman=? or   l_spqk='������' and l_fif='��' and   l_fspman=? or  l_firspif='��'  and   l_spqk='������' and   l_firspman=?";
		
		int num27 = sqlExec.queryForInt(strSQL27, new Object[]{username,username,username});
		
		String strSQL28 = "select count(*) from th_table where  state='������' and spman=? or state='������' and cwman=? and fif='��'   or state='������' and  firspman=? and  firspif='��'";
		int num28 = sqlExec.queryForInt(strSQL28, new Object[]{username,username,username});
		
		String strSQL29 = "select count(*) from hh_goods  where  l_spqk='������' and   l_spman=? or   l_spqk='������'  and   l_fspman=? or    l_spqk='������' and   l_firspman=?  or    l_spqk='������' and   hh_m_man=?";
		int num29 = sqlExec.queryForInt(strSQL29, new Object[]{username,username,username,username});
		
		String strSQL30 = "select count(*) from appeal where state='������' and  clfaman=?";
		int num30 =  sqlExec.queryForInt(strSQL30, new Object[]{username});
		
		
		sqlExec.close();
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public List getCustomerChat(String username,String currentDate) throws Exception{
		
		SQLExecutor sqlExec = SQLExecutor.getInstance(DatasourceStore.getDatasource("default"));
		
		String sql = "select top 10 id,c_nr,co_number,c_name,coid,txtime,c_date,man from customer_gj  where man='"
				+ username
				+ "' and iftx='��' and txtime<='"
				+ currentDate
				+ "'  order by id desc";
		List res = sqlExec.queryForList(sql, true);
		sqlExec.close();
		return res;
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		SimpleDateFormat simple = new SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		
		Map<String,Object> root = this.getRoot();
		
		String username = this.getUsername(runtime);
		int znum = getTotalTodo(username);
		root.put("znum", znum);
		root.put("customerChatList", getCustomerChat(username, currentDate));
		
	}
	
	
	
}
