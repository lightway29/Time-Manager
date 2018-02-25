package com.saiton.ccs.stock;

import com.saiton.ccs.stockdao.SupplierDAO;
import java.util.ArrayList;
import java.util.List;


public class SupplierFacade {

    private final SupplierDAO supplierDao = new SupplierDAO();

    public String generateSupplierId() {
        return supplierDao.generateSupplierId();
    }

    public List<String> selectTelephoneNumbers(String sid) {
        return supplierDao.selectTelephoneNumbers(sid);
    }

    public Boolean setTelephoneNumbers(String sid, List<String> telephones) {
        return supplierDao.setTelephoneNumbers(sid, telephones);
    }

    private List<Item> strArrayListToItemList(List<String[]> data) {
        List<Item> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Item item = new Item();
            item.setFromStringArray(atom);
            items.add(item);
        });

        return items;
    }
    
    public List<Supplier> searchAllSuppliers(String keyword) {
        List<Supplier> suppliers = new ArrayList<>();
        List<String[]> data = supplierDao.searchAllSuppliers(keyword);
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Supplier supplier = new Supplier();
            supplier.setFromStringArray(atom);
            suppliers.add(supplier);
        });
        return suppliers;
    }

    public List<Item> selectItems(String sid) {
        List<String[]> data = supplierDao.selectItems(sid);
        return strArrayListToItemList(data);
    }
    
//    public ArrayList<String> getSelectedItems(String itemId) {
//        ArrayList<String> data = supplierDao.getSelectedItemDetails(itemId);
//        
//        return data;
//    }

    public List<Item> selectAllItems() {
        List<String[]> data = supplierDao.selectAllItems();
        return strArrayListToItemList(data);
    }

    public List<Supplier> selectAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        List<String[]> data = supplierDao.selectAllSuppliers();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Supplier supplier = new Supplier();
            supplier.setFromStringArray(atom);
            suppliers.add(supplier);
        });
        return suppliers;
    }

    public Boolean setItems(String sid, List<Item> items) {
        List<String> strItems = new ArrayList<>();
        items.forEach(item->{
            strItems.add(item.getItemId());
        });
        return supplierDao.setItems(sid, strItems);
    }

    public String insertSupplier(String name, String regNo, String address) {

        return supplierDao.insertSupplier(name, regNo, address);
    }

    public Boolean updateSupplier(String sid, String name, String regNo,
            String address) {
        return supplierDao.updateSupplier(sid, name, regNo, address);
    }

    public Boolean deleteSupplier(String sid) {
        return supplierDao.deleteSupplier(sid);
    }
}
