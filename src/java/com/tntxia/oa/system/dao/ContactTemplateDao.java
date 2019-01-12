package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.ContactTemplate;

public class ContactTemplateDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Ä£°åÁÐ±í
	 * 
	 * @return
	 */
	public List<ContactTemplate> list() {

		DBConnection einfodb = new DBConnection();

		ResultSet rs=einfodb.executeQuery("select * from ht_mb");
		
		List<ContactTemplate> res = new ArrayList<ContactTemplate>();
		
		try {
			while(rs.next()){
				ContactTemplate template = new ContactTemplate();
				template.setId(rs.getInt("id"));
				template.setName(rs.getString("q_name"));
				template.setTopic(rs.getString("q_topic"));
				template.setCompany(rs.getString("q_company"));
				template.setCreateDate(rs.getTimestamp("q_date"));
				res.add(template);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}
	
	public void add(Map<String,Object> params){
		
		String sql = "insert into ht_mb(q_name,q_topic,q_company,q_addr,q_man,q_tel,q_fax,"
				+ "q_email,q_net,q_post,q_number,q_tk,remark,q_date,dept) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		jdbcTemplate.update(sql, new Object[]{params.get("q_name"),params.get("q_topic"),
		 params.get("q_company"),
		 params.get("q_addr"),
		 params.get("q_man"),
		 params.get("q_tel"),
		 params.get("q_fax"),
		 params.get("q_email"),
		 params.get("q_net"),
		 
		 params.get("q_post"),
		 params.get("q_number"),
		 params.get("q_tk"),
		 
		 params.get("remark"),
		 
		 params.get("q_date"),
		
		 params.get("dept")
		 
		});
		
	}
	
	public void update(Map<String,Object> params){
		
		String sql = "update ht_mb set q_name=?,q_topic=?,q_company=?,q_man=?,"
				+ "q_addr=?,q_tel=?,q_fax=?,q_email=?,q_net=?,q_tk=?,q_post=?,q_number=?"
				+ ",remark=? where id=?";
		
		jdbcTemplate.update(sql, new Object[]{params.get("q_name"),params.get("q_topic"),
		 params.get("q_company"),
		 params.get("q_man"),
		 params.get("q_addr"),
		 
		 params.get("q_tel"),
		 params.get("q_fax"),
		 params.get("q_email"),
		 params.get("q_net"),
		 
		 params.get("q_tk"),
		 
		 params.get("q_post"),
		 params.get("q_number"),
		
		 params.get("remark"),
		
		 params.get("id")
		 
		});
		
	}
	
	public void del(Integer id){
		String sql="delete from ht_mb where id=?";
		jdbcTemplate.update(sql, new Object[]{id});
	}

}
