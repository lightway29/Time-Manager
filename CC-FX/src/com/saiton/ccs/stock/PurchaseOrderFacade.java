package com.saiton.ccs.stock;

import com.saiton.ccs.stockdao.PurchaseOrderDAO;
import com.saiton.ccs.stockdao.SupplierDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Purchase Order
 *
 * @author Saitonya
 */
public class PurchaseOrderFacade {

    private final PurchaseOrderDAO purchaseDAO = new PurchaseOrderDAO();
    private final SupplierFacade supplier = new SupplierFacade();
    private final SupplierDAO supplierDao = new SupplierDAO();

    public String generateId() {
        return purchaseDAO.generateId();
    }

    public List<Supplier> selectAllSuppliers() {
        return supplier.selectAllSuppliers();
    }
//    public ArrayList<String> getSelectedItems(String itemId) {
//        ArrayList<String> data = supplier.getSelectedItems(itemId);
//        
//        return data;
//    }

    public List<Item> searchItemsBySupplier(String supplierId,String keyword) {
        List<String[]> data = supplierDao.searchItemsBySupplier(supplierId,keyword);
        List<Item> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Item item = new Item();
            item.setFromStringArrayQty(atom);
            items.add(item);
        });

        return items;
    }
    
    public List<Purchase> searchPurchases(String keyword) {
        List<String[]> data = purchaseDAO.searchPurchases(keyword);
        List<Purchase> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Purchase item = new Purchase();
            item.setFromStringArray(atom);
            items.add(item);
        });

        return items;
    }
    
    public List<Item> selectSupplierItems(String supplierId) {
        List<String[]> data = supplierDao.selectItems(supplierId);
        List<Item> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Item item = new Item();
            item.setFromStringArrayQty(atom);
            items.add(item);
        });

        return items;
    }

    private List<String[]> purchaseItemConvert(List<PurchaseItem> purchaseItems) {
        List<String[]> data = new ArrayList<>();
        purchaseItems.forEach((PurchaseItem p) -> {
            data.add(p.toStringArray());
        });
        return data;
    }

    public Boolean insertPurchaseOrder(String supplierId, String userId,
            List<PurchaseItem> purchaseItems) {

        return purchaseDAO.insertPurchaseOrder(supplierId, userId,
                purchaseItemConvert(purchaseItems));
    }

    public Boolean updatePurchaseOrder(String purchaseId, String supplierId,
            String userId, List<PurchaseItem> purchaseItems) {

        return purchaseDAO.updatePurchaseOrder(purchaseId, supplierId, userId,
                purchaseItemConvert(purchaseItems));
    }

    public List<Batch> selectAllBatch(String itemId) {
        List<String[]> data = purchaseDAO.selectAllBatch(itemId);
        List<Batch> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Batch item = new Batch();
            item.setFromStringArray(atom);
            items.add(item);
        });

        return items;
    }

    public List<Purchase> selectAllPurchases() {
        List<String[]> data = purchaseDAO.selectAllPurchases();
        List<Purchase> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            Purchase item = new Purchase();
            item.setFromStringArray(atom);
            items.add(item);
        });

        return items;
    }

    public List<PurchaseItem> selectPurchaseItems(String purchaseId) {
        List<String[]> data = purchaseDAO.selectPurchaseItems(purchaseId);
        List<PurchaseItem> items = new ArrayList<>();
        if (data == null) {
            return null;
        }
        data.forEach((String[] atom) -> {
            PurchaseItem item = new PurchaseItem();
            item.setFromStringArray(atom);
            items.add(item);
        });

        return items;
    }

    public Item selectItemDetails(String itemId) {
        String[] data = purchaseDAO.selectItemDetails(itemId);
        if (data == null) {
            return null;
        }
        Item item = new Item();
        item.setFromStringArrayQty(data);
        return item;
    }

    public Boolean deletePurchaseOrder(String purchaseId) {
        return purchaseDAO.deletePurchaseOrder(purchaseId);
    }
}
