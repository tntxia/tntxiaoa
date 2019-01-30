// 点击了某个供应商后，会把供应商所有的信息都放在父页面里面，因为这个JSP可能会给多个父页面使用
// 所有在导入之前，要先判断父页面的输入框是否存在
function _chooseSupplier(name,number1,lxr,receiver,receiver_tel,receiver_addr,
	freight,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,pf){

	opener.form1.coname.value=name;
	opener.form1.co_number1.value=number1;
	if(opener.form1.lxr)
		opener.form1.lxr.value=lxr;
	
		
	if(opener.form1.express_company)
		opener.form1.express_company.value=express_company;
	if(opener.form1.acct)
		opener.form1.acct.value=acct;
	if(opener.form1.service_type)
		opener.form1.service_type.value=service_type;
	if(opener.form1.pay_type)
		opener.form1.pay_type.value=pay_type;
	if(opener.form1.supplier_addr)
		opener.form1.supplier_addr.value=coaddr;
	if(opener.form1.tel)
		opener.form1.tel.value=cotel;
	if(opener.form1.fax)
		opener.form1.fax.value=cofax;
	if(opener.setPf)
		opener.setPf(pf);
	
	window.close();

}

function chooseSupplier(name,number1,lxr,receiver,receiver_tel,receiver_addr,
		freight,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,pf){
	_chooseSupplier(name,number1,lxr,receiver,receiver_tel,receiver_addr,
			freight,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,pf);
	window.close();
}

function viewContact(coname,co_number,lxr,receiver,receiver_tel,receiver_addr,freight
,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,pf){
	_chooseSupplier(coname,co_number,lxr,receiver,receiver_tel,receiver_addr,freight
		   ,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,pf);
   opener.contactSearch(co_number);
   window.close();
}

require(['domReady!',"jquery"],function(doc,$){
	
});