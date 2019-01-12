package com.tntxia.oa.purchasing.entity;

public class Purchasing {
	
	private int id;
	
	// 采购订单号
	private String number;
	
	// 采购人
	private String man;
	
	// 货币类型
	private String money;
	
	// 销售合同编号
	private String saleNumber;
	
	// 仓库
	private String warehouse;
	
	// 供应商
	private String supplier;
	
	// 供应商编号
	private String supplierNumber;
	
	// 采购方向
	private String purchasePlace;
	
	// 交货日期
	private String deliverDate;
	
	// 运费
	private String transportationExpense;
	
	// 运费货币
	private String transportationExpenseMoneyType;
	
	// 签订地
	private String signPlace;
	
	// 采购日期
	private String purchaseDate;
	
	// 采购货币
	private String purchaseMoneyType;
	
	// 合同条款
	private String contractItem;
	
	// 备注
	private String remark;
	
	// 状态
	private int status;
	
	private String statusOrign;
	
	// 第一审批人
	private String firstApprover;
	
	// 第二审批人
	private String secondApprover;
	
	// 是否需要二审
	private boolean toSencondApprove;
	
	// 是否已经一审
	private boolean isFirstApproved;
	
	// 审批意见
	private String approveOpinion;
	
	// 联系人
	private String contactMan;
	
	// 收件人
	private String receiver;
	
	// 收件 人电话 
	private String receiverTel;
	
	// 收件人地址
	private String receiverAddress;
	
	// 重量
	private String freight;
	
	// 快递公司
	private String expressCompany;
	
	// 帐号
	private String accountNo;
	
	// 服务类型
	private String serviceType;
	
	// 支付方式
	private String payway;
	
	// 税率
	private String rate;
	
	// 签订地
	private String dealPlace;
	
	// 交易地点
	private String exchangePlace;
	
	// 是否自提
	private boolean selfPickup;
	
	private String firspif;
	
	private String firspman;
	
	private String l_spyj;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}
	
	

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getPurchasePlace() {
		return purchasePlace;
	}

	public void setPurchasePlace(String purchasePlace) {
		this.purchasePlace = purchasePlace;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getTransportationExpense() {
		return transportationExpense;
	}

	public void setTransportationExpense(String transportationExpense) {
		this.transportationExpense = transportationExpense;
	}

	public String getTransportationExpenseMoneyType() {
		return transportationExpenseMoneyType;
	}

	public void setTransportationExpenseMoneyType(
			String transportationExpenseMoneyType) {
		this.transportationExpenseMoneyType = transportationExpenseMoneyType;
	}

	public String getSignPlace() {
		return signPlace;
	}

	public void setSignPlace(String signPlace) {
		this.signPlace = signPlace;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseMoneyType() {
		return purchaseMoneyType;
	}

	public void setPurchaseMoneyType(String purchaseMoneyType) {
		this.purchaseMoneyType = purchaseMoneyType;
	}

	public String getContractItem() {
		return contractItem;
	}

	public void setContractItem(String contractItem) {
		this.contractItem = contractItem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFirstApprover() {
		return firstApprover;
	}

	public void setFirstApprover(String firstApprover) {
		this.firstApprover = firstApprover;
	}

	public String getSecondApprover() {
		return secondApprover;
	}

	public void setSecondApprover(String secondApprover) {
		this.secondApprover = secondApprover;
	}

	public boolean isToSencondApprove() {
		return toSencondApprove;
	}

	public void setToSencondApprove(boolean toSencondApprove) {
		this.toSencondApprove = toSencondApprove;
	}

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getContactMan() {
		return contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDealPlace() {
		return dealPlace;
	}

	public void setDealPlace(String dealPlace) {
		this.dealPlace = dealPlace;
	}

	public boolean isFirstApproved() {
		return isFirstApproved;
	}

	public void setFirstApproved(boolean isFirstApproved) {
		this.isFirstApproved = isFirstApproved;
	}

	public String getExchangePlace() {
		return exchangePlace;
	}

	public void setExchangePlace(String exchangePlace) {
		this.exchangePlace = exchangePlace;
	}

	public boolean isSelfPickup() {
		return selfPickup;
	}

	public void setSelfPickup(boolean selfPickup) {
		this.selfPickup = selfPickup;
	}

	public String getFirspif() {
		return firspif;
	}

	public void setFirspif(String firspif) {
		this.firspif = firspif;
	}

	public String getFirspman() {
		return firspman;
	}

	public void setFirspman(String firspman) {
		this.firspman = firspman;
	}

	public String getL_spyj() {
		return l_spyj;
	}

	public void setL_spyj(String l_spyj) {
		this.l_spyj = l_spyj;
	}

	public String getStatusShow(){
		String show = "";
		switch(status){
		case 0 : show = "草拟";break;
		case 2 : show = "已入库";break;
		default : show = "未知";
		}
		return show;
	}

	public String getStatusOrign() {
		return statusOrign;
	}

	public void setStatusOrign(String statusOrign) {
		this.statusOrign = statusOrign;
	}
	
	

}
