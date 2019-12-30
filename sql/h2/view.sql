CREATE VIEW subview
AS
SELECT subscribe.number, subscribe.coname, subscribe.yj, 
      subscribe.datetime, ddpro.selljg, ddpro.epro, ddpro.num, 
      ddpro.unit, ddpro.salejg, ddpro.pricehb, ddpro.rale_types, 
      ddpro.rale, ddpro.supplier, ddpro.fypronum, subscribe.man, 
      subscribe.habitus, subscribe.state, subscribe.deptjb, 
      subscribe.dept, subscribe.p_states, ddpro.wid, ddpro.fy_states, 
      ddpro.s_num, ddpro.s_c_num, ddpro.s_tr_date, subscribe.id, 
      subscribe.s_check, subscribe.senddate, ddpro.cpro, 
      subscribe.cg_man, subscribe.send_date, subscribe.yf_money, 
      subscribe.yf_types, subscribe.spman, subscribe.spdate, 
      subscribe.item, ddpro.money, subscribe.sub, subscribe.fy_number, 
      subscribe.other_fy, ddpro.x_no, ddpro.p_check, ddpro.money2, 
      subscribe.custno, subscribe.mode, ddpro.remark,ddpro.id proid
FROM subscribe INNER JOIN
      ddpro ON subscribe.id = ddpro.ddid
      
      
CREATE VIEW subview2
AS
SELECT  ddpro.ddid,subscribe.number, subscribe.coname, subscribe.yj, subscribe.datetime, ddpro.selljg, ddpro.epro, ddpro.num, ddpro.unit, 
                      ddpro.salejg, ddpro.pricehb, ddpro.rale_types, ddpro.rale, ddpro.supplier, ddpro.fypronum, subscribe.man, subscribe.habitus, 
                      subscribe.state, subscribe.deptjb, subscribe.dept, subscribe.p_states, ddpro.wid, ddpro.fy_states, ddpro.s_num, ddpro.s_c_num, 
                      ddpro.s_tr_date, subscribe.id, subscribe.s_check, subscribe.senddate, ddpro.cpro, subscribe.cg_man, subscribe.send_date, 
                      subscribe.yf_money, subscribe.yf_types, subscribe.spman, subscribe.spdate, subscribe.item, ddpro.money, subscribe.sub, 
                      subscribe.fy_number, subscribe.other_fy, ddpro.x_no, ddpro.p_check, ddpro.money2, subscribe.custno, subscribe.mode, 
                      ddpro.remark
FROM         subscribe LEFT OUTER JOIN
                      ddpro ON subscribe.id = ddpro.ddid
      
CREATE VIEW cgview
AS
SELECT procure.id, procure.number, procure.coname, procure.datetime, 
      cgpro.selljg, cgpro.money, cgpro.epro, cgpro.num, 
      cgpro.cgpro_num, cgpro.unit, procure.man, cgpro.cgpro_ydatetime, 
      procure.l_spqk, cgpro.wid, cgpro.cgpro_sdatetime, cgpro.cpro, 
      procure.l_dept, procure.l_deptjb, procure.sub, procure.senddate, 
      procure.pay_je, procure.pay_if, cgpro.rate, cgpro.remark, 
      cgpro.supplier
FROM procure INNER JOIN
      cgpro ON procure.id = cgpro.ddid
      
CREATE VIEW payview
AS
SELECT payment.*, procure.ponum AS ponum, procure.sub AS sub
FROM payment INNER JOIN
      procure ON payment.contract = procure.number
      
CREATE VIEW gatherview
AS
SELECT gathering.*, subscribe.man as saleman,subscribe.sub AS sub,subscribe.custno AS custno
FROM gathering INNER JOIN
      subscribe ON gathering.orderform = subscribe.number      
      
      
CREATE VIEW ckview
AS
SELECT     subscribe.id, outhouse.pro_fynum, outhouse.pro_coname, outhouse.pro_model, outhouse.pro_name, outhouse.pro_num, 
                      outhouse.pro_unit, outhouse.pro_supplier, outhouse.pro_datetime, outhouse.pro_number, outhouse.slinkman, 
                      outhouse.slinktel, 
                      outhouse.states, outhouse.ddid, outhouse.remark, outhouse.wid, outhouse.pro_coname_num, 
                      outhouse.pro_sales_price, 
                      outhouse.pro_price_hb, outhouse.pro_rate_types, outhouse.pro_rate, outhouse.pro_out_num, outhouse.in_no, 
                      subscribe.sub, 
                      subscribe.item, subscribe.man, outhouse.salejg
FROM         outhouse INNER JOIN
                      subscribe ON outhouse.pro_fynum = subscribe.number
                      
                      
CREATE VIEW quoteview
AS                     
SELECT quote.id, quote.number, quote.in_number, quote.quotedatetime, 
      quote.coname, quote.man, quote.spman, quote.states, 
      quote.dept, quote.deptjb, quoteproduct.product, 
      quoteproduct.quantity, quoteproduct.unit, quoteproduct.price, 
      quoteproduct.pricehb
FROM quote INNER JOIN
      quoteproduct ON quote.id = quoteproduct.quoteid
  
CREATE VIEW rkview
AS
SELECT in_warehouse.id, in_warehouse.number, in_warehouse.supplier, 
      in_warehouse.int_date, in_warehouse.man, in_warehouse.states, 
      rkhouse.pro_model, rkhouse.pro_name, rkhouse.pro_num, 
      rkhouse.pro_unit, rkhouse.pro_price, rkhouse.pro_hb, 
      in_warehouse.g_man, in_warehouse.deptjb, in_warehouse.dept, 
      in_warehouse.int_types, rkhouse.pro_number, rkhouse.pro_addr, 
      rkhouse.pro_datetime, rkhouse.pro_id, rkhouse.pro_supplier,rkhouse.remark,rkhouse.pro_types
FROM in_warehouse INNER JOIN
      rkhouse ON in_warehouse.id = rkhouse.pro_rk_num
      
CREATE VIEW view_subscribe_search
AS
SELECT subscribe.number, subscribe.coname, subscribe.yj, 
      subscribe.datetime, subscribe.item, ddpro.selljg, ddpro.epro, 
      ddpro.num, ddpro.unit, ddpro.salejg, ddpro.pricehb, 
      ddpro.rale_types, ddpro.rale, ddpro.supplier, ddpro.fypronum, 
      subscribe.man, subscribe.habitus, subscribe.state, 
      subscribe.deptjb, subscribe.dept, subscribe.p_states, ddpro.wid, 
      ddpro.fy_states, ddpro.s_num, ddpro.s_c_num, ddpro.s_tr_date, 
      subscribe.id, subscribe.s_check, subscribe.senddate, ddpro.cpro, 
      subscribe.cg_man, subscribe.send_date, subscribe.yf_money, 
      subscribe.yf_types, subscribe.spman, subscribe.spdate, 
      subscribe.item AS Expr1, ddpro.money, subscribe.sub, 
      subscribe.fy_number, subscribe.other_fy, ddpro.x_no, 
      ddpro.p_check, ddpro.money2, ddpro.remark
FROM subscribe left outer  JOIN
      ddpro ON subscribe.id = ddpro.ddid
      

CREATE VIEW rkview
AS
SELECT in_warehouse.id, in_warehouse.number, in_warehouse.supplier, 
      in_warehouse.int_date, in_warehouse.man, in_warehouse.states, 
      rkhouse.pro_model, rkhouse.pro_name, rkhouse.pro_num, 
      rkhouse.pro_unit, rkhouse.pro_price, rkhouse.pro_hb, 
      in_warehouse.g_man, in_warehouse.deptjb, in_warehouse.dept, 
      in_warehouse.int_types, rkhouse.pro_number, rkhouse.pro_addr, 
      rkhouse.pro_datetime, rkhouse.pro_id, rkhouse.pro_supplier,rkhouse.remark,rkhouse.pro_types
FROM in_warehouse INNER JOIN
      rkhouse ON in_warehouse.id = rkhouse.pro_rk_num
      
      
CREATE VIEW ixjview
AS
SELECT Inquiry.id, Inquiry.number, Inquiry.coname, 
      Inquiry.quotedatetime, Inquiry_product.price, Inquiry_product.price_hb, 
      Inquiry_product.product, Inquiry_product.quantity, 
      Inquiry_product.p_quantity, Inquiry_product.punit, 
      Inquiry_product.supplier, Inquiry.states, Inquiry.man, Inquiry.cgman, 
      Inquiry_product.wid, Inquiry_product.pro_tr, Inquiry_product.pro_p_year, 
      Inquiry_product.pro_pro_no, Inquiry.dept, Inquiry.deptjb, 
      Inquiry.linktel, Inquiry.linkman, Inquiry.cofax, 
      Inquiry_product.p_price, Inquiry_product.p_supplier, 
      Inquiry_product.p_pro_tr, Inquiry.xj_date, Inquiry_product.p_product, 
      Inquiry_product.p_pro_p_year, Inquiry_product.q_price, 
      Inquiry_product.p_hb, Inquiry.co_number, Inquiry_product.cpro, 
      Inquiry_product.cpro2, Inquiry.country, Inquiry.province, 
      Inquiry.hf_date, Inquiry.city, Inquiry_product.pro_report, 
      Inquiry_product.pro_remark, Inquiry_product.pro_states, Inquiry.c_types, 
      Inquiry.copost, Inquiry.in_man, Inquiry.in_f_datetime, 
      Inquiry_product.pro_sup_no, Inquiry.cotel
FROM Inquiry INNER JOIN
      Inquiry_product ON Inquiry.id = Inquiry_product.quoteid
      
CREATE VIEW samview
AS

SELECT sample.id, sample.number, sample.coname, sample.datetime, 
      sam_pro.selljg, sam_pro.money, sam_pro.epro, sam_pro.num, 
      sam_pro.unit, sam_pro.salejg, sam_pro.pricehb, 
      sam_pro.rale_types, sam_pro.rale, sam_pro.supplier, 
      sam_pro.fypronum, sample.man, sample.habitus, sample.state, 
      sample.deptjb, sample.dept, sample.senddate, sam_pro.wid, 
      sam_pro.cpro, sample.sub, sample.spman, sample.fveight, 
      sample.insurance, sample.linkman, sam_pro.pro_snum, 
      sam_pro.pro_sc_num, sam_pro.cpro2, sam_pro.pro_r_date, 
      sam_pro.pro_sr_date
FROM sample INNER JOIN
      sam_pro ON sample.id = sam_pro.ddid
      
CREATE VIEW cg_th_cw as SELECT th_table_supplier.id, th_table_supplier.number, 
      th_table_supplier.coname, th_table_supplier.sub, th_table_supplier.man, 
      th_table_supplier.spman, th_table_supplier.state, 
      th_table_supplier.spdate, th_table_supplier.remarks, 
      th_table_supplier.deptjb, payment.states
FROM th_table_supplier INNER JOIN
      payment ON th_table_supplier.number = payment.contract
      
CREATE VIEW cg_th_cw as SELECT th_table_supplier.id, th_table_supplier.number, 
      th_table_supplier.coname, th_table_supplier.sub, th_table_supplier.man, 
      th_table_supplier.spman, th_table_supplier.state, 
      th_table_supplier.spdate, th_table_supplier.remarks, 
      th_table_supplier.deptjb, payment.states
FROM th_table_supplier INNER JOIN
      payment ON th_table_supplier.number = payment.contract

      