package com.app.utils;

import java.io.IOException;
import java.util.List;

import com.app.tables.Ingredient;
import com.app.tables.Menu;
import com.app.tables.Menu_item;
import com.app.tables.TableMain;

public class MenuDML {
	TableMain tm;

	public MenuDML() {
		tm = TableMain.getInstance();
	}

	public List<Menu_item> showMenu_item(String name, String category, String soldout)
		throws IOException {
		List<Menu_item> rs = tm.menu_item.SELECT(name, category, soldout);
		// ResultSet rs = tm.menu_item.SELECT("piece", "Regular Menu", "T"); // name, category, soldout

		return rs;
	}

	public List<Menu> showMenu(String start_date, String end_date) throws IOException {
		List<Menu> rs = tm.menu.SELECT(start_date, end_date);
		// ResultSet rs = tm.menu.SELECT("2023-01-01", "2023-01-31"); // start_date, end_date

		return rs;
	}

	public List<Menu> showMenu_v2() throws IOException {
		List<Menu> rs = tm.menu.SELECT2();
		// ResultSet rs = tm.menu.SELECT("2023-01-01", "2023-01-31"); // start_date, end_date

		return rs;
	}

	public List<Menu_item> showMenu_item_v2(String menu_id) throws IOException {
		List<Menu_item> result = tm.menu_item.SELECT2(menu_id);
		return result;
	}

	public List<Ingredient> showIngredient(String id) throws IOException {
		List<Ingredient> rs = tm.ingredient.SELECT(id);
		// ResultSet rs = tm.ingredient.SELECT("IN000001"); // id

		return rs;
	}

	public String insertMenu_item(String name, String category, int unit_price, String manager_id)
		throws IOException {
		String id = tm.menu_item.INSERT(name, category, unit_price);
		// String id = tm.menu_item.INSERT("4 piece pie", "Regular Menu"); // name, category
		if (id != "" && id != null) {
			tm.managed_item.INSERT(id, manager_id);
		}
		return id;
	}

	public int insertNeed(String menu_item_id, String ingredient_id) throws IOException {
		int res = tm.need.INSERT(menu_item_id, ingredient_id);

		return res;
	}

	public String insertMenu(String start_date, String end_date, String manager_id)
		throws IOException {
		String id = tm.menu.INSERT(start_date, end_date);
		// String id = tm.menu.INSERT("2023-01-01", "2023-01-31"); // start_date, end_date
		if (id != "" && id != null) {
			tm.edited_menu.INSERT(id, manager_id);
		}
		return id;
	}

	public int insertContains(String menu_id, String menu_item_id) throws IOException {
		int res = tm.contains.INSERT(menu_id, menu_item_id);

		return res;
	}

	public int modifyMenu(String id, String start_date, String end_date) throws IOException {
		int res = tm.menu.UPDATE(id, start_date, end_date);
		// int res = tm.menu.UPDATE("ME000101", "2023-01-01", "2023-01-02"); // id, start_date, end_date

		return res;
	}

	public int modifyIngredient(String id, String attribute, String update_value) throws IOException {
		int res = tm.ingredient.UPDATE(id, attribute, update_value);
		// int res = tm.ingredient.UPDATE("IN000101", "Quantity", "55"); // id, attribute, update_value

		return res;
	}

	public int checkManager_id(String id) { // TO DO
		int res = 0;
		/*
		 * if (id != "" && id != null && id.length() >= 2 && id.charAt(0) == 'M' && id.charAt(1) == 'A')
		 * { res = 1; }
		 */

		return res;
	}
}
