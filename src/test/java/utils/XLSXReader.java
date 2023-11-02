package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class XLSXReader {
    private static final int ID_COLUMN_INDEX = 0;
    private static final int DEVICE_NAME_COLUMN_INDEX = 1;

    public static boolean compareDataWithXLSX(Map<String, String> expected, String failePath) {
        try (FileInputStream fis = new FileInputStream(failePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet firstSheet = workbook.getSheetAt(0);
            for (Row nextRow : firstSheet) {
                Cell idCell = nextRow.getCell(ID_COLUMN_INDEX);
                Cell nameCell = nextRow.getCell(DEVICE_NAME_COLUMN_INDEX);

                String id = idCell.getStringCellValue();
                String name = nameCell.getStringCellValue();

                if (expected.containsKey(id) && expected.get(id).equals(name)) {
                    expected.remove(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return expected.isEmpty();
    }

}
