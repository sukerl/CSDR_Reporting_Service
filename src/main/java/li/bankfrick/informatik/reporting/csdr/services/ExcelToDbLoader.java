package li.bankfrick.informatik.reporting.csdr.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExcelToDbLoader {
	
	private static String SOURCE_FOLDER;
	@Value("${excel.files.source.folder}")
	public void setSourceFolder(String sourceFolder) {
		SOURCE_FOLDER = sourceFolder;
    }
	
	public static void readExcelFiles() {		
		
		String[] pathnames;
		System.out.println(SOURCE_FOLDER);
		File sourceFolderFile = new File(SOURCE_FOLDER);
		
		pathnames = sourceFolderFile.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }
	}
	
	private void load_Details_1_1() {
		
	}

}
