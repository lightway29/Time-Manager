package com.saiton.ccs.commondao;



public class ColumnsAndTables {

    public static final String TABLE_USER = "user";

    public static final String TABLE_BNQ_HALLPRICE = "bnq_hall_price";
    public static final String TABLE_ITEM = "item";
    public static final String TABLE_HALL = "hall";

    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_FLAG = "flag";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOODMENU_ID = "foodmenu_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_SERVICE = "service";
    public static final String COLUMN_VAT = "vat";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_PARENT_ID = "parent_id";

    public static final String COLUMN_FOOD_ID = "food_id";
    public static final String COLUMN_TYPE = "type";

    public static final String COLUMN_FOODMENU_ITEM_ID = "foodmenu_item_id";
    public static final String COLUMN_FOODMENU_FOOD_ID = "foodmenu_food_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_ITEM_ID = "item_id";

    public static final String COLUMN_FOODMENU_CAT_ID
            = "foodmenu_parent_item_id";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_FOODMENUSELECTION_ID
            = "foodmenuselection_id";

    public static final String COLUMN_CUS_ID = "cus_id";
    public static final String COLUMN_EVNT_NAME = "event_name";

    public static final String COLUMN_HALL_ID = "hall_id";
    public static final String COLUMN_HALL_NAME = "hall_name";

    //psuedo columns
    public static final String FCOLUMN_MENU = "menuname";
    public static final String FCOLUMN_FOOD = "foodname";
    public static final String FCOLUMN_CAT = "catname";
    public static final String FCOLUMN_CMNT = "user_comment";
    public static final String COLUMN_FPI_ID = "foodmenu_parent_item_id";

    public static final String QUERY_FOODLIST_PRICE
            = "SELECT f.food_id,f.name,f.price,f.service,f.type FROM "
            + "%1$s_foodmenu_itemfood i INNER JOIN %1$s_foodmenu_food "
            + "f ON i.foodmenu_food_id=f.food_id WHERE i.foodmenu_item_id=?;";

    public static final String QUERY_FOODLIST_PRICE_WITHTYPE
            = "SELECT f.food_id,f.name,f.price,f.service,f.type FROM "
            + "%1$s_foodmenu_itemfood i INNER JOIN %1$s_foodmenu_food "
            + "f ON i.foodmenu_food_id=f.food_id WHERE i.foodmenu_item_id=? "
            + "AND f.type=?;";

    public static final String QUERY_FOODLIST_NOPRICE
            = "SELECT f.food_id,f.name,f.type FROM "
            + "%1$s_foodmenu_itemfood i INNER JOIN %1$s_foodmenu_food "
            + "f ON i.foodmenu_food_id=f.food_id WHERE i.foodmenu_item_id=?;";

    public static final String QUERY_INGR_IN_FOOD
            = "select i.item_id,i.name,i.unit,f.amount "
            + "from %s_food_ingredients f,item i where "
            + "f.ingredient_id=i.item_id AND f.food_id=? AND "
            + "i.item_category='Ingredient';";

    public static final String QUERY_INSERT_SELECTION
            = "INSERT INTO %s_foodmenuselection (cus_id,event_name,date_added) "
            + " VALUES(?,?,now());";

    public static final String QUERY_HALL_PRICE
            = "SELECT m.foodmenu_id,h.hall_id,h.hall_name,hp.price,hp.service FROM "
            + "bnq_hall_price hp,bnq_foodmenu m,hall h WHERE "
            + "hp.foodmenu_id = m.foodmenu_id AND h.hall_id = hp.hall_id AND m.foodmenu_id=?;";

    public static final String QUERY_SELECTION_DETAILS_TYPE = "select s.id, "
            + "se.foodmenu_id,m.name as 'menuname', "
            + "se.foodmenu_food_id,f.name as 'foodname', "
            + "se.foodmenu_parent_item_id,c.name as 'catname', "
            + "se.comment as 'user_comment'  " + "from  "
            + "%1$s_foodmenuselection s, "
            + "%1$s_foodmenuselection_selected se, " + "%1$s_foodmenu m,  "
            + "%1$s_foodmenu_food f, " + "%1$s_foodmenu_item c " + "where  "
            + "s.id=se.foodmenuselection_id  "
            + "and se.foodmenu_food_id=f.food_id "
            + "and se.foodmenu_parent_item_id=c.id  "
            + "and se.foodmenu_id=m.foodmenu_id " + "and s.cus_id=?  "
            + "and s.event_name=?  " + "and f.type=?;";

    public static final String QUERY_SELECTION_DETAILS = "select s.id, "
            + "se.foodmenu_id,m.name as 'menuname', "
            + "se.foodmenu_food_id,f.name as 'foodname', "
            + "se.foodmenu_parent_item_id,c.name as 'catname', "
            + "se.comment as 'user_comment'  " + "from  "
            + "%1$s_foodmenuselection s, "
            + "%1$s_foodmenuselection_selected se, " + "%1$s_foodmenu m,  "
            + "%1$s_foodmenu_food f, " + "%1$s_foodmenu_item c " + "where  "
            + "s.id=se.foodmenuselection_id  "
            + "and se.foodmenu_food_id=f.food_id "
            + "and se.foodmenu_parent_item_id=c.id  "
            + "and se.foodmenu_id=m.foodmenu_id " + "and s.cus_id=?  "
            + "and s.event_name=?;";

    public static final String QUERY_SELECT_INGREDIENTS
            = "SELECT i.item_id,i.item_name,i.unit,f.amount "
            + "FROM %s_food_ingredients f " + "INNER JOIN item i ON "
            + "f.ingredient_id=i.item_id " + "WHERE f.food_id = ?;";

//       public static final String QUERY_HALL_PRICE_JUST_ONE
    //            = "SELECT m.foodmenu_id,h.hall_id,h.hall_name,hp.price FROM "
    //            + "bnq_hall_price hp,bnq_foodmenu m,hall h WHERE "
    //            + "hp.foodmenu_id = m.foodmenu_id AND h.hall_id = hp.hall_id AND h.hall_id=?;"; 
   
}
