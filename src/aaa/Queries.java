/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaa;

/**
 *
 * @author muslumoncel
 */
public class Queries {

	protected static final String getAllDepartments = "select " 
		+ DBTablesAndColumns.COLUMN_DEPARTMENT_CODE 
		+ " from " + DBTablesAndColumns.DB_TABLE_NAME;

	protected static final String getInfos = "select "
		+ DBTablesAndColumns.COLUMN_DEPARTMENT_NAME
		+ "," + DBTablesAndColumns.COLUMN_DEPARTMENT_LONGITUDE
		+ "," + DBTablesAndColumns.COLUM_DEPARTMENT_LATITUDE
		+ " from " + DBTablesAndColumns.DB_TABLE_NAME
		+ " where " + DBTablesAndColumns.COLUMN_DEPARTMENT_CODE + "=?";
}
