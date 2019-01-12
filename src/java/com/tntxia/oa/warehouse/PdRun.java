package com.tntxia.oa.warehouse;

import com.tntxia.oa.warehouse.WarehouseStokingManager;

public class PdRun implements Runnable {
	
	
	public PdRun() {
		
	}

	@Override
	public void run() {
		
		WarehouseStokingManager warehouseManager = new WarehouseStokingManager();
		warehouseManager.pd();
		
	}

}
